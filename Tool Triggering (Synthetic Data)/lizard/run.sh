#!/usr/bin/env bash
# lizard
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
# Python tokeniser. Class B: own parser, never loads a compiler, so the TFM is irrelevant to it.

require_cmd lizard
OUT="$(report_dir)/lizard.json"
FINDINGS_RC="1"
lizard -l csharp --xml "$TOOL_DIR" > "$(report_dir)/lizard.xml" 2>/dev/null
rc=$?
lizard -l csharp "$TOOL_DIR" > "$(report_dir)/lizard.txt" 2>/dev/null
accept_findings $rc || exit 1
python3 - "$OUT" <<'PY'
import json, re, sys, os
txt = open(os.path.join(os.environ.get("REPO_ROOT","."), "reports", "lizard.txt")).read()
m = re.findall(r'^\s*(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s+(\S+)@', txt, re.M)
peak = max(((int(c), f) for _, c, _, _, _, f in m), default=(0, None))
if not m:
    sys.exit("STRUCTURALLY ZERO: lizard counted no functions. '-l csharp' is required; "
             "without it lizard guesses by extension and reports an empty analysis "
             "at exit 0.")
json.dump({"tool": "lizard", "functions": len(m), "max_ccn": peak[0]},
          open(sys.argv[1], "w"), indent=2)
print("lizard: %d functions, peak CCN %d" % (len(m), peak[0]))
PY
exit $?
