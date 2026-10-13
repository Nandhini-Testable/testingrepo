#!/usr/bin/env bash
# Pymcdc -- primary tool, branch PY-081 / PY_V39_UV_PIP_MONC (Python 3.9, uv_build / pip).
set -uo pipefail
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT="$(cd "$HERE/../.." && pwd)"
# shellcheck source=/dev/null
. "$ROOT/tools/_skip.sh"

# Vendored 3.9-safe pymcdc under tools/pymcdc/vendor (upstream uses match/case).

mkdir -p "$ROOT/reports"
cd "$ROOT"

FINDINGS_EXIT=1
"$PYBIN" "$ROOT/tools/pymcdc/run_pymcdc.py" 2>&1 | tee reports/pymcdc.txt
rc=$?
accept_findings "pymcdc" "$rc" "reports/pymcdc.txt"
exit $?
