#!/usr/bin/env bash
# unilyze
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
# REPAIRED. Real tool (github.com/bigdra50/unilyze, MIT, v0.6.1) -- see
# tools/unilyze/README.md and trigger.yaml for the full correction and the
# verification evidence. Class B: own Roslyn-based syntax walk, no restore or
# build required, so the TFM is irrelevant to it, like Lizard/jscpd/Semgrep.
# Installed from its GitHub Release binary, a channel this host's egress
# proxy does not block, unlike the NuGet-gated dotnet-tool channel.

UNILYZE_VERSION="0.6.1"
CACHE_DIR="${UNILYZE_CACHE_DIR:-$HOME/.cache/unilyze-bin}/$UNILYZE_VERSION"
BIN="$CACHE_DIR/unilyze"

if [ ! -x "$BIN" ]; then
  case "$(uname -s)" in
    Linux)  RID="linux-x64" ;;
    Darwin) if [ "$(uname -m)" = "arm64" ]; then RID="osx-arm64"; else RID="osx-x64"; fi ;;
    *) missing "unsupported host OS for unilyze's self-contained binary: $(uname -s)" ;;
  esac
  mkdir -p "$CACHE_DIR"
  URL="https://github.com/bigdra50/unilyze/releases/download/v${UNILYZE_VERSION}/unilyze-${UNILYZE_VERSION}-${RID}.tar.gz"
  if ! curl -fsSL "$URL" -o "$CACHE_DIR/unilyze.tar.gz"; then
    missing "could not download unilyze ${UNILYZE_VERSION} from GitHub Releases ($URL)"
  fi
  tar -xzf "$CACHE_DIR/unilyze.tar.gz" -C "$CACHE_DIR"
  chmod +x "$BIN"
  rm -f "$CACHE_DIR/unilyze.tar.gz"
fi

require_artifact "$BIN" "unilyze binary"

OUT="$(report_dir)/unilyze.json"
HOTSPOT_OUT="$(report_dir)/unilyze-hotspot.json"

"$BIN" -p "$TOOL_DIR" -f json -o "$OUT" --no-open
rc=$?
if [ "$rc" -ne 0 ]; then
  _reason "unilyze exited $rc analyzing $REPO_ROOT/src"
  exit 1
fi

"$BIN" hotspot -p "$TOOL_DIR" --since 100.year -o "$HOTSPOT_OUT" >/dev/null
hrc=$?
if [ "$hrc" -ne 0 ]; then
  _reason "unilyze hotspot exited $hrc"
  exit 1
fi

python3 - "$OUT" "$HOTSPOT_OUT" <<'PY'
import json, sys
result_path, hotspot_path = sys.argv[1], sys.argv[2]
d = json.load(open(result_path))
best_cyc = (-1, None)
best_cog = (-1, None)
for t in d.get("types", []):
    for m in t.get("members", []):
        name = "%s.%s.%s" % (t["namespace"], t["name"], m["name"])
        cyc = m.get("cyclomaticComplexity")
        cog = m.get("cognitiveComplexity")
        if cyc is not None and cyc > best_cyc[0]:
            best_cyc = (cyc, name)
        if cog is not None and cog > best_cog[0]:
            best_cog = (cog, name)
if best_cyc[1] is None:
    sys.exit("STRUCTURALLY ZERO: unilyze found no members with a cyclomaticComplexity "
              "field -- the JSON schema has changed or analysis silently found nothing.")

hs = json.load(open(hotspot_path))
hotspots = hs.get("hotspots", [])
peak_changes = max((h.get("changeCount", 0) for h in hotspots), default=0)

summary = {
    "tool": "unilyze",
    "toolVersion": d.get("toolVersion"),
    "max_cyclomatic_complexity": best_cyc[0],
    "max_cyclomatic_complexity_member": best_cyc[1],
    "max_cognitive_complexity": best_cog[0],
    "max_cognitive_complexity_member": best_cog[1],
    "hotspot_peak_change_count": peak_changes,
    "hotspot_type_count": len(hotspots),
}
summary_path = result_path.rsplit("/", 1)[0] + "/unilyze-summary.json"
json.dump(summary, open(summary_path, "w"), indent=2)
print("unilyze: max CCN %d (%s), max cognitive %d (%s), %d churn hotspots (peak %d changes)"
      % (best_cyc[0], best_cyc[1], best_cog[0], best_cog[1], len(hotspots), peak_changes))
PY
exit $?