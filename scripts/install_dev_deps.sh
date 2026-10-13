#!/usr/bin/env bash
# Install dev/tool pins for PY_V39_UV_PIP_MONC on Python 3.9.
#
# semgrep (tomli~=2.0.1) and complexipy 7.x (tomli>=2.2.1) cannot resolve in
# one pip invocation. Install semgrep first, bump tomli, then complexipy.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PY="${PYTHON:-python3}"

"$PY" -m pip install --upgrade 'pip==26.0.1'
"$PY" -m pip install -e "$ROOT"

FILTERED="$(mktemp)"
grep -vE '^(semgrep|complexipy|-e )' "$ROOT/requirements-dev.txt" >"$FILTERED"
"$PY" -m pip install -r "$FILTERED"
rm -f "$FILTERED"

"$PY" -m pip install 'semgrep==1.72.0'
"$PY" -m pip install --upgrade --force-reinstall 'tomli==2.4.1' --no-deps
"$PY" -m pip install 'complexipy==7.0.1'
"$PY" -m pip install -e "$ROOT/Tool Triggering (Synthetic Data)/pymcdc/vendor"
"$PY" -m pip install -r "$ROOT/requirements-runtime.txt"
