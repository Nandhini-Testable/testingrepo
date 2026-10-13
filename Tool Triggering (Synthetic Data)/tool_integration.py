#!/usr/bin/env python3
"""Banner / --verify / --run for the tool roster.

  python "Tool Triggering (Synthetic Data)/tool_integration.py" --run-primary
      run only dataset.json tools with status active (primary evidence)

--run distinguishes a skip that dataset.json ALREADY RECORDS (the measurement,
exit 0) from a skip it does not (a new finding, exit 2).
"""
import argparse
import json
import os
import shutil
import subprocess
import sys

# Derived from this file's own location, so the containing folder can be
# renamed without editing 312 branches. It is now
# "Tool Triggering (Synthetic Data)".
TOOLS_DIR = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(TOOLS_DIR)
NAMES = {0: "ran", 1: "FAILED", 3: "skipped", 4: "not installed"}


def dataset():
    with open(os.path.join(ROOT, "dataset.json")) as fh:
        return json.load(fh)


def _bash_executable():
    """Prefer Git Bash on Windows; WSL /bin/bash cannot run paths with spaces."""
    env_bash = os.environ.get("BASH")
    if env_bash and os.path.isfile(env_bash):
        return env_bash
    if os.name == "nt":
        for cand in (
            r"C:\Program Files\Git\bin\bash.exe",
            r"C:\Program Files (x86)\Git\bin\bash.exe",
        ):
            if os.path.isfile(cand):
                return cand
    found = shutil.which("bash")
    return found if found else "bash"


def _git_bash_path(win_path):
    p = os.path.normpath(win_path)
    drive, rest = os.path.splitdrive(p)
    if not drive:
        return p.replace("\\", "/")
    return "/" + drive[0].lower() + rest.replace("\\", "/")


def _runner_env():
    env = dict(os.environ, REPO_ROOT=ROOT)
    tools_path = _git_bash_path(TOOLS_DIR)
    py_root = os.path.dirname(sys.executable)
    scripts = os.path.join(py_root, "Scripts")
    prefix = tools_path + ":" + _git_bash_path(scripts) + ":" + _git_bash_path(py_root)
    env["PATH"] = prefix + ":" + env.get("PATH", "")
    if os.name == "nt":
        env["PYTHON3"] = _git_bash_path(sys.executable)
    return env


def banner(ds):
    print("OrderKit - %s (%s), C# %s" % (ds["netLabel"], ds["targetFramework"],
                                         ds["languageVersion"]))
    print("branch %s  cell %s/%s/%s" % (ds["branch"], ds["cell"]["bundler"],
                                        ds["cell"]["packageManager"],
                                        ds["cell"]["architecture"]))
    print("%d tools declared, %d expected to fire"
          % (ds["expectations"]["toolsTotal"], ds["expectations"]["toolsActive"]))


def verify(ds):
    problems = []
    for t in ds["tools"]:
        run = os.path.join(TOOLS_DIR, t["slug"], "run.sh")
        if not os.path.exists(run):
            problems.append("%s: no runner" % t["slug"])
        elif not os.access(run, os.X_OK):
            problems.append("%s: runner not executable" % t["slug"])
    if problems:
        print("VERIFY FAILED")
        for p in problems:
            print("  -", p)
        return 1
    print("OK - %d runners present and executable" % len(ds["tools"]))
    return 0


def run(ds, *, primary_only=False):
    tools = [t for t in ds["tools"] if t["status"] == "active"] if primary_only else ds["tools"]
    expected = {t["slug"]: t["expectedExitCode"] for t in tools}
    surprises, tally, status_rows = [], {}, []
    bash = _bash_executable()
    env = _runner_env()
    for t in tools:
        run_sh = os.path.join(TOOLS_DIR, t["slug"], "run.sh")
        proc = subprocess.run(
            [bash, run_sh],
            capture_output=True,
            text=True,
            encoding="utf-8",
            errors="replace",
            env=env,
        )
        rc = proc.returncode
        tally[rc] = tally.get(rc, 0) + 1
        mark = "ok " if rc == expected[t["slug"]] else "NEW"
        if rc != expected[t["slug"]]:
            surprises.append("%s: expected %d (%s), got %d (%s)"
                             % (t["slug"], expected[t["slug"]],
                                NAMES.get(expected[t["slug"]], "?"), rc,
                                NAMES.get(rc, "?")))
        print("  %s %-18s %d %s" % (mark, t["slug"], rc, NAMES.get(rc, "?")))
        if primary_only:
            label = "RAN" if rc == 0 else NAMES.get(rc, str(rc))
            status_rows.append("%s\t%s\texit %d" % (t["slug"], label, rc))
    print("\n" + ", ".join("%d %s" % (n, NAMES.get(rc, str(rc)))
                           for rc, n in sorted(tally.items())))
    if primary_only and status_rows:
        reports = os.path.join(ROOT, "reports")
        os.makedirs(reports, exist_ok=True)
        with open(os.path.join(reports, "_tool-status.tsv"), "w") as fh:
            fh.write("\n".join(status_rows) + "\n")
    if surprises:
        print("\nUNRECORDED RESULTS - these are new findings, not measurements:")
        for s in surprises:
            print("  -", s)
        return 2
    print("every result matches dataset.json")
    return 0


if __name__ == "__main__":
    ap = argparse.ArgumentParser()
    ap.add_argument("--verify", action="store_true")
    ap.add_argument("--run", action="store_true")
    ap.add_argument(
        "--run-primary",
        action="store_true",
        help="run only active (primary) tools and refresh reports/_tool-status.tsv",
    )
    a = ap.parse_args()
    d = dataset()
    if a.verify:
        sys.exit(verify(d))
    if a.run_primary:
        banner(d)
        print()
        sys.exit(run(d, primary_only=True))
    if a.run:
        banner(d)
        print()
        sys.exit(run(d))
    banner(d)
