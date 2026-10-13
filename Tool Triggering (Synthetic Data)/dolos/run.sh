#!/usr/bin/env bash
# dolos
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
# Node, tree-sitter C# grammar. Class B, and the sheet's declaration ('C# language-version independent - source similarity, not tied to compiler') is correct. MEASURED, not assumed: @dodona/dolos 2.9.3 fails to install on this host because @dodona/dolos-parsers 1.4.1 needs node-gyp native compilation, which fails under node 22.22.2. That is exit 4 (absent from this host), not exit 3 (cannot run on this family) -- collapsing the two would let a missing binary masquerade as a .NET Framework result.
missing "Node, tree-sitter C# grammar"

require_cmd dolos
dolos run -f csv -o "$(report_dir)/dolos" -l c-sharp "$TOOL_DIR"/*.cs
accept_findings $? || exit 1
exit 0
