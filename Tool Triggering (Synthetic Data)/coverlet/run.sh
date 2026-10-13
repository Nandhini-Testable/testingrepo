#!/usr/bin/env bash
# coverlet
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
# STATUS CHANGED AT THIS FAMILY. net45 and net46 sat below netstandard2.0, whose .NET Framework floor is net461, so Coverlet was excluded by the TARGET FRAMEWORK and exited 3. This family is above that floor: nothing about net462 excludes Coverlet. It is now held out only by NuGet being refused at this host's egress proxy, which is a setup gap -- exit 4. The 18 metrics it is primary for (statement 5, branch 7, delta 6) are dark for a completely different reason than they were one family ago, and collapsing 3 and 4 would hide that.
missing "STATUS CHANGED AT THIS FAMILY"

exit 0
