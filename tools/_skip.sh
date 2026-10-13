# Shared runner preamble. Sourced by every tools/*/run_*.sh.
#
# Exit codes:
#   0  tool ran and wrote its report
#   1  tool ran and failed
#   3  SKIPPED       -- the tool cannot run on this interpreter (a finding)
#   4  NOT INSTALLED -- the tool is absent from this host (a setup gap)
#
# Do not "simplify" a skip into exit 0. This corpus exists to make silent
# success visible; a skip that looks like a pass is the defect, not the report.
SKIP_EXIT=3
MISSING_EXIT=4
PYBIN="${PYTHON:-python3}"

py_minor() {
  "$PYBIN" -c 'import sys;print("%d.%d" % sys.version_info[:2])' 2>/dev/null || echo "unknown"
}

_status() {
  # $1 tool dir, $2 status, $3 reason
  mkdir -p "$ROOT/reports"
  printf '%s\t%s\t%s\n' "$1" "$2" "$3" >> "$ROOT/reports/_tool-status.tsv"
}

# require_python_floor <tool-dir> <declared-floor-or-empty> <pin-or-empty>
# Compares the DECLARED floor against the running interpreter.
require_python_floor() {
  local dir="$1" floor="$2" pin="$3"
  [ -z "$floor" ] && return 0
  local have want
  have="$(py_minor)"
  want="${floor#>=}"
  if [ "$(printf '%s\n%s\n' "$want" "$have" | sort -t. -k1,1n -k2,2n | head -1)" != "$want" ]; then
    echo "STATUS: SKIPPED"
    echo "  tool        : $dir"
    echo "  pin         : ${pin:-none}"
    echo "  declares    : Requires-Python $floor"
    echo "  interpreter : Python $have"
    echo "  reason      : the pinned release excludes this interpreter, so pip never"
    echo "                installed it. Declared on purpose; see dataset.json."
    _status "$dir" "SKIPPED" "declared floor $floor > interpreter $have"
    exit $SKIP_EXIT
  fi
  return 0
}

# require_import <tool-dir> <module> <pin>
# Actually imports the module. A distribution's declared floor is a claim; this
# is the fact. lizard and pydriller pass require_python_floor and fail here.
require_import() {
  local dir="$1" mod="$2" pin="$3"
  [ -z "$mod" ] && return 0
  local err
  if err="$("$PYBIN" -c "import $mod" 2>&1)"; then
    return 0
  fi
  local last
  last="$(printf '%s' "$err" | tail -1)"
  echo "STATUS: SKIPPED"
  echo "  tool        : $dir"
  echo "  pin         : ${pin:-none}"
  echo "  interpreter : Python $(py_minor)"
  echo "  import error: $last"
  echo "  reason      : the module is not importable on this interpreter. If the"
  echo "                distribution installed anyway, its declared floor was wrong."
  _status "$dir" "SKIPPED" "import failed: $last"
  exit $SKIP_EXIT
}

# require_binary <tool-dir> <executable>
#
# Exits 4, NOT 3, and the difference matters. Exit 3 means "this tool cannot run
# on this interpreter" -- a property of the branch, and part of the measurement.
# Exit 4 means "this tool is not installed on this host" -- a property of the
# machine, and a setup gap to fix. Collapsing them would let a missing binary
# masquerade as a finding about Python 3.6.
require_binary() {
  local dir="$1" exe="$2"
  command -v "$exe" >/dev/null 2>&1 && return 0
  echo "STATUS: NOT INSTALLED"
  echo "  tool   : $dir"
  echo "  reason : '$exe' is not on PATH. It is a standalone binary, not a pin,"
  echo "           so no package manager can supply it -- see tools/$dir/INSTALL.md."
  echo "  note   : this is a host setup gap, not a Python 3.6 finding. This tool"
  echo "           IS able to run on this interpreter."
  _status "$dir" "NOT_INSTALLED" "$exe not on PATH"
  exit $MISSING_EXIT
}

ran_ok() { _status "$1" "RAN" "${2:-ok}"; }

# accept_findings <tool-dir> <exit-code> <expected-report>
#
# Many analysers exit non-zero to mean "I found something". On a corpus whose
# whole purpose is planted defects, that inverts the exit code: a CORRECT run
# reports as a failed run. Observed on this family with lizard (exit 1 because
# classify_shipment exceeds its CCN threshold) and ruff (exit 1 on 94 findings)
# -- both had done exactly what they were wired to do.
#
# A runner declares FINDINGS_EXIT=1 when its tool behaves that way. The run is
# then accepted only if the tool also PRODUCED ITS REPORT: "exited 1 and wrote
# nothing" is still a failure, and that distinction is the whole point.
accept_findings() {
  local dir="$1" rc="$2" out="$3"
  if [ "$rc" -eq 0 ]; then ran_ok "$dir" "exit 0"; return 0; fi
  if [ "${FINDINGS_EXIT:-0}" = "1" ] && [ "$rc" -le 2 ]; then
    if [ -z "$out" ] || [ -e "$ROOT/$out" ]; then
      echo "NOTE: exit $rc means findings were reported, not that the tool failed."
      echo "      $dir is wired against planted fixtures; findings are the expected result."
      ran_ok "$dir" "exit $rc (findings present -- expected)"
      return 0
    fi
    echo "FAIL: $dir exited $rc AND produced no report at $out."
    echo "      A findings exit with no output is a real failure, not a detection."
  fi
  _status "$dir" "FAILED" "exit $rc"
  # Never let a tool's raw exit code escape as 3 or 4: those belong to the
  # corpus (3 = cannot run here, 4 = not installed here), and a collision turns
  # a tool result into a false family finding. vulture, for instance, exits 3
  # when it finds dead code -- dark on this family, live from 3.9.
  if [ "$rc" -eq 3 ] || [ "$rc" -eq 4 ]; then return 1; fi
  return "$rc"
}
