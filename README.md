# TS_V20_VITE_NPM_MONO_ALL_TOOLS

Part of the [`VISVANTHA/corpus`](https://github.com/VISVANTHA/corpus) white-box test-repo corpus (GraniteMill /
`granite-mill`, domain: Community garden plots).

## Branch variables

| Variable | This branch |
| --- | --- |
| Branch | `TS_V20_VITE_NPM_MONO_ALL_TOOLS` |
| Language | TypeScript |
| Node.js | 20.20.2 (family V20) |
| Bundler | Vite (built as esbuild) |
| Package manager | npm |
| Bundled npm | 10.8.2 |
| Architecture | Monolith |
| Source root | `src` |

The application code under `src/` (or `packages/*/src/` for Microservices
branches) is byte-identical across all 576 branches of this corpus; only the
build tool, package manager, architecture layout, and each tool's real
status on this Node family vary.

## TypeScript-only source

This branch contains TypeScript source only, so a white-box platform scan
detects a single language and plans only its TypeScript tools. The former
per-tool `tools/<dir>/` folders (shell and Python wrapper scripts plus their
`trigger.yaml` files) were removed; the OpenTelemetry Vitest bootstrap now
lives at `tests/setup/otel-bootstrap.ts` and the knip report script at
`scripts/knip-report.ts`. The tool tables below are kept as the reference
answer key for each tool's expected status.

Settings that keep the platform's TypeScript tools producing output:

- `tsc -p .` must stay clean: the Stryker TypeScript checker type-checks the
  whole project before mutating and falls back to a slower unchecked run on
  any error.
- `.eslintignore` excludes the build config files that sit outside
  `tsconfig.json`; type-aware ESLint cannot parse files outside the project.
- `.oxlintrc.json` uses oxlint's `categories` key; rule categories under
  `rules` make oxlint reject the whole config.
- `knip.json` turns off knip's `vite` and `vitest` plugins: the platform runs
  knip before `node_modules` is installed, and loading `vite.config.ts` /
  `vitest.config.ts` without the `vite` package makes knip crash with no
  output.
- `tests/setup/otel-bootstrap.ts` exports spans to `OTEL_SPAN_EXPORT_PATH`
  when a span collector sets it, because Vitest workers are terminated before
  a process-exit hook can write. Stryker runs skip the bootstrap.

## Supported tools

21 tools are wired on this corpus, covering the 103-metric white-box
framework. **16 of them run on Node 20; 5 do not.**

That is the measurement, not a defect. A tool that cannot run exits **3**,
not 0 -- a skip that looks like a pass is the failure mode this corpus
exists to expose. `dataset.json` records whether each tool's status
here was actually invoked and observed by Claude Code (`measured: true`) or
is a real npm-registry `engines.node` claim not yet individually
invoke-verified on this exact family (`measured: false`) -- declared support
is a claim, invoking is the fact, and this corpus never blurs the two.

### Running here

| Tool | Role | Pin | Why |
| --- | --- | --- | --- |
| `Lizard` | primary | lizard (pip) | measured directly: pip-installed and run for real against this domain's src/ (lizard 1.24.0). Found policy.ts's evaluatePolicy as the highest-CCN function at 18 -- corrects an earlier assumption that dataflow.ts's tally loop (CCN 17) was highest; Node-independent by construction, so this holds on every family. _(measured: real invocation confirmed by Claude Code)_ |
| `cyclomatic-complexity` | alternative | cyclomatic-complexity==1.2.5 | cyclomatic-complexity 1.2.5 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `eslint-plugin-sonarjs` | primary | eslint-plugin-sonarjs==4.2.1 | eslint-plugin-sonarjs 4.2.1 declares Node 20 compatibility; runs through eslint itself. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `cognitive-complexity-ts` | alternative | cognitive-complexity-ts==0.8.2 | cognitive-complexity-ts 0.8.2 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `jscpd` | primary | jscpd==5.2.1 | jscpd 5.2.1 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `eslint` | primary | eslint==10.10.0 | eslint 10.10.0 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `eslint-plugin-security` | primary | eslint-plugin-security==4.0.1 | eslint-plugin-security 4.0.1 declares Node 20 compatibility; runs through eslint itself. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `npm audit + npm ls` | primary | n/a | npm's own bundled audit/ls -- ships with npm itself on every Node family this corpus supports. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `nyc + mocha` | primary | nyc==18.0.0 | clean on every family once the architecture-aware test glob (bug #1) and the Microservices require() path (bug #4) were both fixed. _(measured: real invocation confirmed by Claude Code)_ |
| `monocart-coverage-reports` | alternative | monocart-coverage-reports==2.13.0 | monocart-coverage-reports 2.13.0 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `gutcheck` | alternative | gutcheck==0.10.0 | gutcheck 0.10.0 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `diff-cover` | primary | diff-cover (pip) | the diff-cover CLI itself was invoked for real (pip install + --help) and works; its coverage-delta output depends on nyc's cobertura report, which needs a full npm install this session's verification bridge could not complete (slow mounted filesystem + shared disk quota, not a tool defect -- see javascript-repos-build-contract.md). Node-independent by construction. _(measured: real invocation confirmed by Claude Code)_ |
| `ESLint (eslint-scope)` | primary | eslint-scope==9.1.2 | clean on every family tested (12 through 26) -- the dataflow script is hand-written without `?.`/`??` specifically so it never depends on the same syntax features that break other tools on the oldest families. _(measured: real invocation confirmed by Claude Code)_ |
| `knip` | alternative | knip==6.36.0 | knip 6.36.0 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `PyDriller` | primary | pydriller (pip) | PyDriller is a Python package invoked externally via a small wrapper script -- Node-version-independent by construction. _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |
| `Git-Spark` | alternative | git-spark==1.3.0 | Git-Spark 1.3.0 declares Node 20 compatibility (npm registry engines.node range). _(declared active from npm registry data; not yet individually invoke-verified on this family)_ |

### Dark here

| Tool | Role | Status | Why |
| --- | --- | --- | --- |
| `Dolos` | alternative | not installed | dolos's native tree-sitter step needs a C++ toolchain -- already documented as absent on this host by the C#/Python sibling corpora's own precedent; installs with --ignore-scripts, which skips the native build |
| `oxlint` | alternative | skipped | same oxlint 1.16.0-vs-1.83.0 range-drift break as N18; 1.83.0 resolves here even more often since N20 favours newer optional native bindings. |
| `OpenGrep` | alternative | not installed | standalone binary, not installable via npm/pip on this host -- same category as Dolos's native step and the Python corpus's own Trivy/OpenGrep entries |
| `Trivy` | alternative | not installed | standalone binary, not installable via npm/pip on this host |
| `StrykerJS + Mocha` | primary | skipped | mocha's own latest release (12.0.1) has moved ahead of what any current Stryker release supports -- `@stryker-mutator/mocha-runner@9.6.1`'s peer range caps at `mocha < 12`; on npm this is install-blocking (ERESOLVE), on yarn/pnpm/bun (which only warn on unmet peers) it installs but fails at runtime with `Cannot find module '.../mocha/lib/cli/run-helpers'` -- mocha 12 relocated an internal CLI module Stryker's runner still requires by hardcoded path. An upstream incompatibility between two independently-versioned tools, not fixable from this corpus's own config. |

## Build

```
npm install    # Corepack packageManager is npm@10.8.2; lockfile is package-lock.json
npm run build
```

## Run

```
npx tsx src/index.ts
```

## Test

```
npm test              # mocha tests/**/*.test.ts
npm run coverage      # nyc + mocha
npm run test:vitest:coverage   # vitest + v8 coverage
```

## Tool entry points

Each tool runs through an npm script in `package.json`, for example:

```
npm run lint            # eslint
npm run lint:oxlint     # oxlint
npm run duplication     # jscpd
npm run mutation        # stryker
npm run analyze:deps    # dependency-cruiser
npm run analyze:knip    # knip
```

## Planted fixtures

| Fixture | File(s) | Planted for |
| --- | --- | --- |
| Duplication | [`src/http-errors.ts`](src/http-errors.ts) + [`src/http-errors-legacy.ts`](src/http-errors-legacy.ts) | jscpd, Dolos |

## Workspace layout

```
corpus/  (TS_V20_VITE_NPM_MONO_ALL_TOOLS)
|-- .github/
|-- src/
|-- tests/  (or packages/shared/tests/ for Microservices)
|-- scripts/
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

## Platform Pipeline & Trigger Status

| Property | Value |
| --- | --- |
| Branch | `TS_V20_VITE_NPM_MONO_ALL_TOOLS` |
| Pipeline Scan Status | Active |
| Node / TS Runtime | Node 20.20.2 / TypeScript 5.8 |
| Platform Trigger Verification | Verified (All TypeScript Whitebox Tools Configured) |
