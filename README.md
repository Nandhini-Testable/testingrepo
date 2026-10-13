# JS_V21_VITE_NPM_MONO

Part of the `javascript-combos` white-box test-repo corpus (GraniteMill /
`granite-mill`, domain: Community garden plots).

## Branch variables

| Variable | This branch |
| --- | --- |
| Branch | `JS_V21_VITE_NPM_MONO` |
| Node.js | 21.7.3 (family V21) |
| Bundler | Vite (built as esbuild) |
| Package manager | npm |
| Bundled npm | 10.9.2 |
| Architecture | Monolith |
| Source root | `src` |

The application code under `src/` (or `packages/*/src/` for Microservices
branches) is byte-identical across all 576 branches of this corpus; only the
build tool, package manager, architecture layout, and each tool's real
status on this Node family vary.

## Supported tools

21 tools are wired on this corpus (one `tools/<dir>/` folder
each, covering the 103-metric white-box framework). **17 of them
run on Node 21; 4 do not.**

That is the measurement, not a defect. A tool that cannot run exits **3**,
not 0 -- a skip that looks like a pass is the failure mode this corpus
exists to expose. Every `tools/<dir>/trigger.yaml` records whether its status
here was actually invoked and observed by Claude Code (`measured: true`) or
is a real npm-registry `engines.node` claim not yet individually
invoke-verified on this exact family (`measured: false`) -- declared support
is a claim, invoking is the fact, and this corpus never blurs the two.

### Running here

| Tool | Role | Pin | Why |
| --- | --- | --- | --- |
| `Lizard` | primary | lizard (pip) | measured directly: pip-installed and run for real against this domain's src/ (lizard 1.24.0). Found policy.js's evaluatePolicy as the highest-CCN function at 18 -- corrects an earlier assumption that dataflow.js's tally loop (CCN 17) was highest; Node-independent by construction, so this holds on every family. _(measured: real invocation confirmed by Claude Code)_ |
| `cyclomatic-complexity` | alternative | cyclomatic-complexity==1.2.5 | cyclomatic-complexity 1.2.5 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `eslint-plugin-sonarjs` | primary | eslint-plugin-sonarjs==4.2.1 | eslint-plugin-sonarjs 4.2.1 declares Node 21 compatibility; runs through eslint itself. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `cognitive-complexity-ts` | alternative | cognitive-complexity-ts==0.8.2 | cognitive-complexity-ts 0.8.2 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `jscpd` | primary | jscpd==5.2.1 | jscpd 5.2.1 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `eslint` | primary | eslint==9.39.5 | eslint 9.39.5 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `eslint-plugin-security` | primary | eslint-plugin-security==4.0.1 | eslint-plugin-security 4.0.1 declares Node 21 compatibility; runs through eslint itself. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `npm audit + npm ls` | primary | n/a | npm's own bundled audit/ls -- ships with npm itself on every Node family this corpus supports. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `nyc + mocha` | primary | nyc==17.1.0 | clean on every family once the architecture-aware test glob (bug #1) and the Microservices require() path (bug #4) were both fixed. _(measured: real invocation confirmed by Claude Code)_ |
| `monocart-coverage-reports` | alternative | monocart-coverage-reports==2.13.0 | monocart-coverage-reports 2.13.0 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `StrykerJS + Mocha` | primary | @stryker-mutator/core==9.6.1 | this family happened to resolve an older, mutually-compatible mocha/Stryker pairing at install time -- confirms the N20 incompatibility is driven by registry/cache state, not Node version. _(measured: real invocation confirmed by Claude Code)_ |
| `gutcheck` | alternative | gutcheck==0.10.0 | gutcheck 0.10.0 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `diff-cover` | primary | diff_cover.js (Node port of diff-cover) | diff-cover is implemented in Node (`tools/diff-cover/diff_cover.js`), a port of the Python diff-cover CLI: it reads the changed lines from `git diff origin/main...HEAD` plus the staged and unstaged changes and matches them against nyc's cobertura report (coverage/cobertura-coverage.xml). It needs only Node and git; its coverage-delta output depends on that report existing. _(measured: real invocation confirmed by Claude Code)_ |
| `ESLint (eslint-scope)` | primary | eslint-scope==8.4.0 | clean on every family tested (12 through 26) -- the dataflow script is hand-written without `?.`/`??` specifically so it never depends on the same syntax features that break other tools on the oldest families. _(measured: real invocation confirmed by Claude Code)_ |
| `knip` | alternative | knip==5.88.1 | knip 5.88.1 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `PyDriller` | primary | git log (Node port of the PyDriller wrapper) | The PyDriller churn wrapper, ported from Python to Node (`tools/pydriller/run_pydriller.js`): it reads this branch's history with `git log --name-status` and needs only Node and git -- Node-version-independent by construction. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `Git-Spark` | alternative | git-spark==1.3.0 | Git-Spark 1.3.0 declares Node 21 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |

### Dark here

| Tool | Role | Status | Why |
| --- | --- | --- | --- |
| `Dolos` | alternative | not installed | dolos's native tree-sitter step needs a C++ toolchain -- already documented as absent on this host by the C#/Python sibling corpora's own precedent; installs with --ignore-scripts, which skips the native build |
| `oxlint` | alternative | skipped | a third distinct oxlint-1.83.0 breakage mode: `node_modules/@oxlint/` exists but is empty -- the platform-specific native-binding optional dependency didn't install, so oxlint's own bindings loader has nothing to load, regardless of package manager. |
| `OpenGrep` | alternative | not installed | standalone binary, not installable via npm/pip on this host -- same category as Dolos's native step and the Python corpus's own Trivy/OpenGrep entries |
| `Trivy` | alternative | not installed | standalone binary, not installable via npm/pip on this host |

## Build

```
npm install    # or yarn / pnpm / bun, per this branch's packageManager field
npm run build
```

## Run

```
node src/index.js
```

## Test

```
npm test              # mocha tests/**/*.test.js
npm run coverage      # nyc + mocha
make check            # tools/full_check.js -- cross-file consistency audit
```

## Tool entry points

Every tool directory carries a `trigger.yaml` recording its pin, its
declared status and what a working run should find. Run one tool directly,
or all of them:

```
bash tools/eslint/run_eslint.sh
node tools/tool_integration.js --run
node tools/tool_integration.js --verify
```

`--run` distinguishes three outcomes: a tool that ran, a tool that skipped
for a reason `dataset.json` already records, and a tool that skipped for a
reason it does not. Only the third is a finding.

## Planted fixtures

| Fixture | File(s) | Planted for |
| --- | --- | --- |
| Duplication | [`src/http-errors.js`](src/http-errors.js) + [`src/http-errors-legacy.js`](src/http-errors-legacy.js) | jscpd, Dolos |

## Workspace layout

```
javascript-combos/  (JS_V21_VITE_NPM_MONO)
|-- .github/
|-- src/
|-- tests/  (or packages/shared/tests/ for Microservices)
|-- tools/  (21 tool directories + _skip.sh, tool_integration.js, full_check.js)
|-- Makefile
|-- README.md
|-- dataset.json
|-- package.json
```

## History

This branch's real commit history starts from the corpus's scaffolding
generation, followed by the six genuine scaffolding-bug fixes found by
Claude Code's actual local install/test runs across all nine Node families
(see `javascript-repos-build-contract.md`) -- every commit here is real,
authored by the accounts that actually did the work; nothing is
back-filled or fabricated.

## Machine-readable

[`dataset.json`](dataset.json) carries every branch variable and the full
tool-status breakdown, including the reason each dark tool is dark and
whether that status was actually measured. It is the answer key: a run is
correct when what the tool platform reports matches what `dataset.json`
says should happen, including the tools that are supposed to be dark.

See `javascript-repos-build-contract.md` in the Testable (Tools) project for
the full 103-metric roster, the repair notes, and the live pin-resolution
method (npm registry `engines.node` ranges, prereleases excluded).
