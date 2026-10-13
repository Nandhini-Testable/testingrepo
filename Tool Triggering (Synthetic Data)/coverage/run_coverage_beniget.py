#!/usr/bin/env python
"""Combined Coverage.py + Beniget report for All-Uses metrics on Python 3.9."""
from __future__ import print_function

import json
import os
import subprocess
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
SRC = os.path.join(ROOT, "src")
PKG = "orderlab"
REPORTS = os.path.join(ROOT, "reports")
COVERAGE_XML = os.path.join(REPORTS, "coverage.xml")
BENIGET_JSON = os.path.join(REPORTS, "beniget.json")
OUT = os.path.join(REPORTS, "coverage-beniget.json")


def _load_beniget_summary():
    if not os.path.isfile(BENIGET_JSON):
        return {"error": "missing beniget.json -- run beniget or coverage first"}
    with open(BENIGET_JSON) as handle:
        return json.load(handle)


def _coverage_summary():
    if not os.path.isfile(COVERAGE_XML):
        return {"error": "missing coverage.xml"}
    try:
        import xml.etree.ElementTree as ET
    except ImportError:
        return {"error": "no ElementTree"}
    root = ET.parse(COVERAGE_XML).getroot()
    packages = []
    for pkg in root.findall(".//package"):
        packages.append({
            "name": pkg.get("name"),
            "line-rate": pkg.get("line-rate"),
            "branch-rate": pkg.get("branch-rate"),
        })
    return {"packages": packages, "line_rate": root.get("line-rate")}


def main():
    os.makedirs(REPORTS, exist_ok=True)
    beniget_runner = os.path.join(ROOT, "tools", "beniget", "run_beniget.py")
    if os.path.isfile(beniget_runner):
        subprocess.check_call([sys.executable, beniget_runner], cwd=ROOT)
    payload = {
        "tool": "coverage.py + beniget",
        "interpreter": "%d.%d.%d" % sys.version_info[:3],
        "coverage": _coverage_summary(),
        "beniget": _load_beniget_summary(),
        "source_root": "%s/%s" % (SRC, PKG),
    }
    with open(OUT, "w") as handle:
        json.dump(payload, handle, indent=2, sort_keys=True)
    print("Wrote %s" % OUT)
    return 0


if __name__ == "__main__":
    sys.exit(main())
