#!/usr/bin/env python
"""PyDriller churn and ownership.

DARK on this branch. pydriller 2.10 declares Requires-Python >=3.5, so pip
installs it without a word, and it then fails at import:

    File ".../pydriller/utils/mailmap.py", line 72
        if cached_developer := self.check_mailmap_cache.get(developer):
                             ^
    SyntaxError: invalid syntax

The walrus operator is 3.8+. Its GitPython dependency also resolves silently
backwards to 3.1.18. This runner reports that honestly instead of crashing.
"""
from __future__ import print_function

import json
import os
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

try:
    from pydriller import Repository
except SyntaxError as exc:
    print("STATUS: SKIPPED")
    print("  tool        : pydriller")
    print("  pin         : pydriller==2.10")
    print("  declares    : Requires-Python >=3.5")
    print("  interpreter : Python %d.%d" % sys.version_info[:2])
    print("  import error: SyntaxError: %s (line %s)" % (exc.msg, exc.lineno))
    print("  reason      : the declared floor is wrong. This is the finding, not")
    print("                a defect in this branch -- see TRAPS-PY36.md.")
    sys.exit(3)
except ImportError as exc:
    print("STATUS: SKIPPED")
    print("  tool        : pydriller")
    print("  import error: %s" % exc)
    sys.exit(3)


def main():
    authors = {}
    files = {}
    total = 0
    for commit in Repository(ROOT).traverse_commits():
        total += 1
        authors[commit.author.name] = authors.get(commit.author.name, 0) + 1
        for trailer_line in commit.msg.splitlines():
            if trailer_line.lower().startswith("co-authored-by:"):
                who = trailer_line.split(":", 1)[1].split("<")[0].strip()
                authors[who] = authors.get(who, 0) + 1
        for mod in commit.modified_files:
            key = mod.new_path or mod.old_path
            if key:
                files[key] = files.get(key, 0) + 1
    report = {
        "tool": "PyDriller",
        "commits": total,
        "authors": authors,
        "top_churn": sorted(files.items(), key=lambda kv: -kv[1])[:10],
    }
    outdir = os.path.join(ROOT, "reports")
    if not os.path.isdir(outdir):
        os.makedirs(outdir)
    with open(os.path.join(outdir, "pydriller.json"), "w") as handle:
        json.dump(report, handle, indent=2, sort_keys=True)
    print("pydriller: %d commits, %d distinct contributors" % (total, len(authors)))
    if len(authors) < 2:
        print("WARNING: one contributor. Co-authored-by trailers were not counted --")
        print("         this branch plants three of them on purpose.")
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
