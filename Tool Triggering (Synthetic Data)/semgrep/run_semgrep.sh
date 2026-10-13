#!/usr/bin/env bash
# Semgrep OSS -- primary tool, branch PY-081 / PY_V39_UV_PIP_MONC (Python 3.9, uv_build / pip).
set -uo pipefail
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT="$(cd "$HERE/../.." && pwd)"
# shellcheck source=/dev/null
. "$ROOT/tools/_skip.sh"

SRC="src"
PKG="orderlab"

require_import "semgrep" "semgrep" "semgrep==1.72.0"
PYSEMGREP="$PYBIN_DIR/pysemgrep"
if [ ! -f "$PYSEMGREP" ]; then
  PYSEMGREP="$PYBIN_DIR/semgrep"
fi
require_smoke "semgrep" "semgrep==1.72.0" "$PYBIN" "$PYSEMGREP" --version

mkdir -p "$ROOT/reports"
cd "$ROOT"

FINDINGS_EXIT=1
"$PYBIN" "$PYSEMGREP" scan --config p/python --config p/security-audit --json -o reports/semgrep.json "$SRC" \
  && "$PYBIN" -m bandit -r "$SRC" -f json -o reports/bandit.json
rc=$?
accept_findings "semgrep" "$rc" "reports/semgrep.json"
exit $?
