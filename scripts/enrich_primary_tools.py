"""Merge ``primary_tool_supplement.json`` into each primary-tool bundle."""

from __future__ import annotations

import json
import shutil
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[1]
SUPPLEMENT = REPO_ROOT / "data" / "primary_tool_supplement.json"
OUT_DIR = REPO_ROOT / "data" / "primary_tools"
PKG_DIR = REPO_ROOT / "src" / "strategy_metrics" / "primary_tools"
MANIFEST = OUT_DIR / "manifest.json"


def enrich() -> int:
    if not SUPPLEMENT.is_file():
        raise SystemExit(f"Missing {SUPPLEMENT}")
    if not MANIFEST.is_file():
        raise SystemExit(f"Missing {MANIFEST}; run build_primary_tools_data.py first.")

    extra = json.loads(SUPPLEMENT.read_text(encoding="utf-8"))
    tool_meta: dict = extra.get("tools", {})
    manifest = json.loads(MANIFEST.read_text(encoding="utf-8"))
    updated = 0

    PKG_DIR.mkdir(parents=True, exist_ok=True)
    for entry in manifest.get("tools", []):
        tid = entry["primary_tool_id"]
        path = OUT_DIR / entry["file"]
        if not path.is_file():
            continue
        bundle = json.loads(path.read_text(encoding="utf-8"))
        meta = dict(tool_meta.get(tid, {}))
        if meta:
            ex_id = meta.pop("example_metric_id", None)
            if ex_id and "example_variables" in meta:
                meta["example_apply"] = {
                    "metric_id": ex_id,
                    "variables": meta.pop("example_variables"),
                }
            elif "example_variables" in meta:
                meta["example_variables_hint"] = meta.pop("example_variables")
            bundle["primary_tool_data"] = meta
        elif "primary_tool_data" in bundle:
            del bundle["primary_tool_data"]
        text = json.dumps(bundle, indent=2, ensure_ascii=False)
        path.write_text(text, encoding="utf-8")
        (PKG_DIR / entry["file"]).write_text(text, encoding="utf-8")
        updated += 1

    shutil.copy2(MANIFEST, PKG_DIR / "manifest.json")
    return updated


def main() -> None:
    n = enrich()
    print(f"Enriched {n} primary-tool bundles from {SUPPLEMENT.name}")


if __name__ == "__main__":
    main()
