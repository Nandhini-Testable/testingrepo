# orderlab -- PY_V39_UV_PIP_MONC

Order-pricing domain used as a white-box tool-evaluation fixture. This git branch
is the **full-primary** Python 3.9.23 variant: every White Box metric primary
named for Python can run here. Sibling `PY_V39_UV_PIP_MONO` keeps corpus-wide
latest pins with intentional dark tools. Domain code is byte-identical across
branches.

## Branch variables

| Variable | This branch |
|---|---|
| Branch | `PY_V39_UV_PIP_MONC` |
| Python | 3.9.23 |
| Build backend | uv_build 0.12.9 |
| Backend metadata | pyproject [project] |
| Package manager | pip 26.0.1 |
| Architecture | Monolith |
| Scenario | 1 - Monolithic |
| Source root | `src` |
| Branch usable | yes |

> **Build backend.** uv_build 0.12.9 -- MANDATES PEP 621 [project] and requires Python >=3.8, comfortably below this family. Runs at the current latest release, not a rolled-back one.

> **Package manager.** pip 26.0.1 -- the newest release admitting Python 3.9.

## Supported tools

29 tools are wired on this branch. **24 run on Python 3.9.23**; **5** remain dark
because only releases requiring >=3.10 exist (`mutmut`, `diff-cover`, standalone
`astroid`, `pyan3 + astroid`, `dulwich`). Primaries that the metrics sheet names
for Python use the **newest pin that still supports 3.9** on this branch only.

Coverage runs pytest under Coverage.py, then writes `reports/coverage-beniget.json`
(Beniget + coverage XML) for All-Uses metrics. Pymcdc is installed with
`pip install --ignore-requires-python` and started via `tools/pymcdc/run_pymcdc.py`
(the PyPI `-m pymcdc` entry uses `match`, which is 3.10+).

See `dataset.json` for the full active/dark breakdown.

### Dark here (>=3.10 only)

| Tool | Role | Pin | Why |
|---|---|---|---|
| `mutmut` | alternative | `mutmut==3.7.0` | mutmut 3.7.0 declares Requires-Python >=3.10. |
| `diff-cover` | alternative | `diff-cover==10.5.1` | diff-cover 10.5.1 declares Requires-Python >=3.10. |
| `astroid` | alternative | `astroid==4.3.1` | astroid 4.3.1 declares Requires-Python >=3.10.0. |
| `pyan3 + astroid` | alternative | `pyan3==2.8.1` | pyan3 2.8.1 declares Requires-Python >=3.10,<3.16. |
| `dulwich` | alternative | `dulwich==1.2.14` | dulwich 1.2.14 declares Requires-Python >=3.10. |


## Build

```
make setup        # python -m pip install --upgrade 'pip==26.0.1'
make install      # dev pins via scripts/install_dev_deps.sh (pymcdc vendor + tomli reconcile)
```

> Both the backend and the package manager on this branch run on Python 3.9.23 for real; the branch installs, locks and tests cleanly.

## Run

```
python -m orderlab
```



## Test

```
make test         # pytest 8.4.2
make check        # Tool Triggering (Synthetic Data)/full_check.py -- cross-file consistency audit
```

pytest is pinned at 8.4.2, the last release admitting this
interpreter. It is infrastructure, not one of the 29 roster tools, and so is
exempt from the latest-only policy: a branch whose tests cannot run is not a
branch.

## Workspace layout

```
python-corpus/  (PY_V39_UV_PIP_MONC)
|-- .github/  (1 files)
|-- src/  (20 files)
|-- tests/  (7 files)
|-- Tool Triggering (Synthetic Data)/  (74 files)
|-- .editorconfig
|-- .gitignore
|-- .python-version
|-- Makefile
|-- dataset.json
|-- pip.conf
|-- pyproject.toml
|-- pytest.ini
|-- requirements-dev.txt
|-- requirements-runtime.txt
|-- requirements.lock
|-- setup.cfg
```

### Monolith

One deployable unit. `src/orderlab` holds the whole domain, and the analysis
fixtures sit alongside it. Untrusted input reaches the code through argv, the
process environment, file contents and public function parameters -- there is
no request object anywhere in this branch, which is the point: a taint engine
that only recognises a web request finds nothing here.



## Tool test-data folders

Three sibling folders sit at the repo root, alongside this branch's own
`Tool Triggering (Synthetic Data)/` (above).

### `Tool Triggering (Tool Github Test data)/`
Each of the 28 tool subfolders is that tool's own real upstream test suite,
pulled as-is from its actual GitHub project -- not generated. `Radon/` is
radon's own pytest suite; `pydriller/` is pydriller's own test suite;
`Opengrep/` is the real opengrep/semgrep test corpus (1,000+ files of rules,
parsing fixtures and snapshots). A correct run finds whatever that upstream
project's own tests genuinely contain.

### `Tool Clean (Synthetic Data)/`
25 of the 28 tools each carry 5 generated fixture packages, one per Python
interpreter (3.6, 3.7, 3.11, 3.13, 3.14), each a self-contained package
(`pyproject.toml`, `src/<module>/`, `tests/`) under its own invented domain
name -- engineered to be clean, so the tool should report zero findings:
the **Tool Clean (100% pass)** condition. The remaining 3 tools
(`diff-cover`, `dulwich`, `pydriller`) operate on git history rather than
interpreter syntax, so each carries one real git repository's worth of
history instead of 5 per-version copies -- restored from `_git-bundles/`
via `restore-invalid-git.ps1` rather than kept as a live `.git` folder, so a
plain file copy never silently drops their content. `_generator/` holds the
scripts that built every fixture.

### `Tool Invalid (Synthetic Data)/`
Same shape as Clean -- 25 tools x 5 Python-version fixtures, plus the same
3 git-history tools -- but every fixture is engineered to make the tool
flag or fail rather than pass: the **Tool Invalid** condition. `_git-bundles/`
backs up each git-history tool's real history, with `restore-invalid-git.ps1`
to rebuild it; `live_results.json` and this folder's own README/archive
travel with it.

## Tool entry points

Every tool directory carries a `trigger.yaml` recording its pin, its declared
floor, its measured status on this interpreter and what a working run should
find. Run one tool directly, or all of them:

```
bash "Tool Triggering (Synthetic Data)/radon/run_radon.sh"
python "Tool Triggering (Synthetic Data)/tool_integration.py" --run
python "Tool Triggering (Synthetic Data)/tool_integration.py" --verify
```

`--run` distinguishes three outcomes: a tool that ran, a tool that skipped for
a reason `dataset.json` already records, and a tool that skipped for a reason
it does not. Only the third is a finding.

## Planted fixtures

Every tool is pointed at something it should find. Without these, a tool that
ran and reported nothing is indistinguishable from a tool that silently
no-opped.

| Fixture | File | Planted for |
|---|---|---|
| Duplication | [`src/orderlab/services/retail_order_processor.py`](src/orderlab/services/retail_order_processor.py) + [`wholesale_order_processor.py`](src/orderlab/services/wholesale_order_processor.py) | jscpd, symilar |
| Complexity | [`src/orderlab/analysis/complexity_sample.py`](src/orderlab/analysis/complexity_sample.py) | Radon, Lizard, complexipy, cognitive-ast |
| Lint | [`src/orderlab/analysis/lint_violations.py`](src/orderlab/analysis/lint_violations.py) | pylint, Ruff |
| SAST | [`src/orderlab/analysis/sast_fixture.py`](src/orderlab/analysis/sast_fixture.py) | Semgrep + Bandit, Opengrep |
| Taint | [`src/orderlab/analysis/taint_fixture.py`](src/orderlab/analysis/taint_fixture.py) | Opengrep taint mode |
| Dead code | [`src/orderlab/analysis/dead_code.py`](src/orderlab/analysis/dead_code.py) | vulture, pylint |
| Call graph | [`src/orderlab/analysis/call_graph_sample.py`](src/orderlab/analysis/call_graph_sample.py) | pyan3 + astroid, Beniget |
| Vulnerable pins | [`requirements-runtime.txt`](requirements-runtime.txt) | pip-audit, Trivy |

The duplicate pair also co-changes three times in the git history, so a
change-coupling tool and a duplication tool should agree on it.

The five planted pins carry 47 live advisories between them,
confirmed against the PyPI JSON API on 3 September 2026. The set is identical to the three families below it, so the
SCA metrics are directly comparable across all four.

`six` sits beside them in a clearly separated support section — paramiko
2.4.1 imports it without declaring it, and cryptography 42 no longer supplies
it by accident. Every one is genuinely imported by
[`src/orderlab/platform/integrations.py`](src/orderlab/platform/integrations.py) --
a pin nothing imports produces a manifest-versus-source disagreement that looks
like a tool defect and is not.

## History

Roughly 45 synthetic commits, authored as Prajith Kumaravel with three
co-authors carried in `Co-authored-by:` trailers. Measured on this family:
14-20% of commits each, three distinct names. A history tool
that reads only the author field reports one contributor at 100% and is wrong.
The processor pair co-changes three times; the tool runners co-change as a
cluster.

## Machine-readable

[`dataset.json`](dataset.json) carries every branch variable, the full tool
status breakdown with the verbatim reason for each dark tool, and the planted
fixture inventory. It is the answer key: a run is correct when what the tool
platform reports matches what `dataset.json` says should happen, **including
the tools that are supposed to be dark**.
