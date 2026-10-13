#!/usr/bin/env bash
# trivy
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
# Standalone Go binary, absent from this host. Class C: reads manifests/lockfiles.
missing "Standalone Go binary, absent from this host"


require_cmd trivy
# Reads this folder's SBOM, not a project manifest: a manifest here would
# make the platform treat the folder as its own project.
trivy sbom --format json --output "$(report_dir)/trivy.json" \
     "$TOOL_DIR/sbom.cdx.json"
accept_findings $? || exit 1
exit 0
