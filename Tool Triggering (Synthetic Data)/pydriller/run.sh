#!/usr/bin/env bash
# pydriller
set -uo pipefail
# Paths are derived from this script's OWN location, so nothing here hardcodes the
# name of the containing folder. The folder was renamed from `tools` to
# `Tool Triggering (Synthetic Data)` and a hardcoded name would have to be edited
# on 312 branches the next time it changes. Quoted throughout: the folder name
# contains spaces and parentheses.
TOOL_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TOOLS_ROOT="$(cd "$TOOL_DIR/.." && pwd)"
REPO_ROOT="$(cd "$TOOLS_ROOT/.." && pwd)"
# Exported so the inline python blocks and any child process see them. Without
# the export, running a runner directly failed with KeyError: 'REPO_ROOT'; it
# only ever worked because tool_integration.py injected REPO_ROOT into the
# environment itself.
export TOOL_DIR TOOLS_ROOT REPO_ROOT
. "$TOOLS_ROOT/_skip.sh"

# --- why this tool has the status it has on this family -------------------
# Python, reads git only. Class C: never touches the source language.

require_python_module pydriller
python3 - <<'PY'
import json, os
from pydriller import Repository
root = os.environ["REPO_ROOT"]
commits, authors = 0, {}
for c in Repository(root).traverse_commits():
    commits += 1
    authors[c.author.name] = authors.get(c.author.name, 0) + 1
    for line in (c.msg or "").splitlines():
        if line.lower().startswith("co-authored-by:"):
            name = line.split(":", 1)[1].split("<")[0].strip()
            authors[name] = authors.get(name, 0) + 1
json.dump({"tool": "pydriller", "commits": commits, "contributors": len(authors),
           "by_author": authors},
          open(os.path.join(root, "reports", "pydriller.json"), "w"), indent=2)
PY
exit 0
