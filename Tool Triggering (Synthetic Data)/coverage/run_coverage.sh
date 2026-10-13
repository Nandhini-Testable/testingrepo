#!/usr/bin/env bash
# Coverage.py -- primary tool, branch PY-081 / PY_V39_UV_PIP_MONC (Python 3.9, uv_build / pip).
set -uo pipefail
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT="$(cd "$HERE/../.." && pwd)"
# shellcheck source=/dev/null
. "$ROOT/tools/_skip.sh"

SRC="src"
PKG="orderlab"

require_import "coverage" "coverage" "coverage==7.10.7"
require_smoke "coverage" "coverage==7.10.7" "$PYBIN" -m coverage --version

mkdir -p "$ROOT/reports"
cd "$ROOT"

"$PYBIN" -m coverage run -m pytest -q && "$PYBIN" -m coverage xml -o reports/coverage.xml && "$PYBIN" -m coverage report
rc=$?
accept_findings "coverage" "$rc" "reports/coverage.xml"
beniget_rc=0
"$PYBIN" "$ROOT/tools/coverage/run_coverage_beniget.py" || beniget_rc=$?
if [ "$beniget_rc" -ne 0 ]; then
  echo "FAIL: coverage-beniget combined report failed with exit $beniget_rc"
  exit 1
fi
exit 0
