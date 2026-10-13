#!/usr/bin/env python
"""Invoke vendored pymcdc (3.9-safe) on the planted complexity fixture."""
from __future__ import print_function

import os
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
VENDOR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "vendor")
SRC = os.path.join(ROOT, "src")
PKG = "orderlab"

sys.path.insert(0, VENDOR)

try:
    from pymcdc.cli import main as pymcdc_main
except ImportError as exc:
    print("STATUS: SKIPPED")
    print("  tool        : pymcdc")
    print("  import error: %s" % exc)
    sys.exit(3)


def run():
    target = os.path.join(SRC, PKG, "analysis", "complexity_sample.py")
    sys.argv = ["pymcdc", target]
    pymcdc_main()


if __name__ == "__main__":
    run()
