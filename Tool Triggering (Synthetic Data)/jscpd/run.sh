#!/usr/bin/env bash
# jscpd
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
# Node, own tokeniser. Class B.

require_cmd jscpd
FINDINGS_RC="1"   # jscpd's threshold is a FAILURE BUDGET, not a detection floor:
                  # at 0 it exits 1 the moment it finds anything, and this corpus
                  # plants a duplicate pair on purpose. A correct run is not a failure.
#
# The format token is "csharp", NOT "c#". Given "c#" jscpd prints a warning to
# stderr, matches ZERO files, writes a valid-looking report saying clones: 0, and
# EXITS 0 -- a platform grading by exit code grades that green. This runner
# therefore asserts it scanned something: an unexplained zero is worse than a
# failure, because nothing in the exit-code contract can see it.
JSCPD_OUT="$(report_dir)/jscpd"
( cd "$TOOL_DIR" && jscpd . --format "csharp" --min-tokens 50 \
      --reporters json,console --output "$JSCPD_OUT" --threshold 100 )
accept_findings $? || exit 1
python3 "$TOOL_DIR/assert_nonzero.py" \
        "$(report_dir)/jscpd/jscpd-report.json"
exit $?
