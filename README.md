# OrderKit - CS_VNET6_SDKMSBUILD_NUGETPACKAGEREFERENCE_MONO

One branch of the C# tool-evaluation corpus. Test **data**, not a product: tool
execution is not part of the build, and evaluation happens later.

## Branch

`CS_VNET6_SDKMSBUILD_NUGETPACKAGEREFERENCE_MONO`

## Cell

| Axis | Value |
|---|---|
| Bundler | SDK-style MSBuild |
| Package manager | NuGet (PackageReference) |
| Architecture | Monolith |

Projects:

- `OrderKit` (`src/OrderKit`)

## Target framework

`net6.0` - .NET 6

The first LTS of the unified line. Out of support since 12 November 2024. packages.config is still dead, so six of the 24 cells remain impossible -- three families running now, which makes it a standing property of the grid rather than a transitional artefact of the .NET Core crossing.

## Language version

C# 10.0, taken as the default of the target framework. `LangVersion` is deliberately
never written to any project file, which is what keeps the family axis one number
instead of two.

The domain layer is written to that language subset exactly, and the lock is
verified by execution rather than asserted:

```
csc -langversion:10.0   ->  clean
csc -langversion:9.0   ->  error CS8025: Feature 'async function' is not available
```

## Supported tools

6 of the 19 tools in the roster are expected to fire on this branch. The rest exit
3 (cannot run here) or 4 (absent from this host), each with its reason in both
`run.sh` and `trigger.yaml`.

**A tool that does not fire is a result, not a failure.** What a family with *no*
firing tools cannot do is distinguish "correctly detected nothing" from "the scan
never ran" - so the source-level tools below are the built-in negative control:

- Lizard
- jscpd
- Semgrep
- PyDriller
- git log --numstat

| Tool | Status | Why |
|---|---|---|
| Lizard | active | Python tokeniser. Class B: own parser, never loads a compiler, so the TFM is irrelevant  |
| jscpd | active | Node, own tokeniser. Class B. |
| Dolos | not-installed | Node, tree-sitter C# grammar. Class B, and the sheet's declaration ('C# language-version |
| Semgrep | active | Python, tree-sitter C# grammar. Class B. |
| Opengrep | not-installed | Standalone binary, absent from this host. Class B when present. NOTE: the sheet declares |
| Trivy | not-installed | Standalone Go binary, absent from this host. Class C: reads manifests/lockfiles. |
| PyDriller | active | Python, reads git only. Class C: never touches the source language. |
| git log --numstat | active | stdlib git. Class C. Counts Co-authored-by trailers, not just the author field -- the Ty |
| diff-cover | skipped | Class C: reads Cobertura/OpenCover XML. No coverage report can be produced on this famil |
| Roslyn analyzers (cognitive complexity) | not-installed | The sheet says 'Roslyn', which is a compiler platform, not a package. Resolved here to S |
| Roslyn SAST analyzers | not-installed | The sheet says 'roslyn sast', also not a package. Resolved here to SecurityCodeScan + Mi |
| Roslyn SemanticModel.AnalyzeDataFlow | not-installed | PROPOSED REPAIR, not in the source sheet. The sheet assigns Data Flow to Stryker.NET, a  |
| dotnet list package --vulnerable | skipped | Requires PackageReference and an SDK-style project. Structurally impossible on the packa |
| Coverlet | not-installed | STATUS CHANGED AT THIS FAMILY. net45 and net46 sat below netstandard2.0, whose .NET Fram |
| AltCover | not-installed | Declares .NET Framework 2.0+ via a net20 recorder, so it is the one coverage tool in the |
| MiniCover | skipped | MiniCover 3.10.0 declares .NET SDK 8.0/9.0/10.0 only -- verified against its own MiniCov |
| Stryker.NET | skipped | Requires an SDK-style project and a modern SDK to run. Also: the sheet makes it primary  |
| OpenTelemetry (.NET) + OTLP exporter | not-installed | STATUS CHANGED AT THIS FAMILY, for the same reason as Coverlet: netstandard2.0 clears at |
| unilyze | active | Real tool (github.com/bigdra50/unilyze, MIT, v0.6.1) -- not absent. Verified: live cyclomatic/cognitive comp |
## Build

```bash
./build.sh
```

Restore will not succeed on a host where NuGet is unreachable; see `dataset.json`
`blockedHosts`.

## Test

```bash
./build.sh          # the test step is wired into the build
```

19 tests are declared. They are **not** executed in this corpus's build, because no
test framework can be restored.

## Tool data folders

Four folders carry the per-tool data, the same four every language corpus in
this programme uses. Their contents are byte-identical on every branch of this
repository.

| Folder | What it holds |
|---|---|
| `Tool Triggering (Synthetic Data)` | 19 folders of planted data, one per runner, written to trigger its tool at this family's language version. Each folder's `DATA.md` says what is planted and what a correct run should measure. |
| `Tool Clean (Synthetic Data)` | The 17 roster tools, with code a tool should find **nothing** in. The false-positive control. |
| `Tool Invalid (Synthetic Data)` | The same 17 tools, with code a tool **should** flag. The true-positive control. |
| `Tool Triggering (Tool Github Test data)` | Each tool's own upstream test suite, copied byte-for-byte from the tool's repository. `SOURCE.md` records the repo, commit and licence. |

Clean and Invalid carry five version folders per tool -- `netfx45`, `netfx46`,
`netcoreapp30`, `net9`, `net10`. Each compiles at its own C# level, and its
`LanguageMarker` is rejected one level below, so the version a folder claims is
verified by execution rather than asserted.

**Clean, Invalid and Tool Triggering (Synthetic Data) contain no project
manifest** -- no `.csproj`, `.sln`, `packages.config` or lockfile. One there
would make the platform treat the folder as a project of its own and run the
whole roster against it. `Tool Triggering (Tool Github Test data)` is the
deliberate exception: it is upstream code kept exactly as upstream ships it,
manifests included.

## Tool entry points

```bash
python3 "Tool Triggering (Synthetic Data)/tool_integration.py"            # banner
python3 "Tool Triggering (Synthetic Data)/tool_integration.py" --verify   # every runner present and executable
python3 "Tool Triggering (Synthetic Data)/tool_integration.py" --run      # run them all, compare to dataset.json
python3 "Tool Triggering (Synthetic Data)/full_check.py"                  # cross-file consistency audit
```

## What is expected to fire

`dataset.json` asserts the answer rather than describing it, so a platform that
reports nothing for a given tool can be *proven* correct instead of assumed correct.
Every runner's expected exit code is recorded there, and
`tool_integration.py --run` exits **2** if any tool produces a result the dataset
does not already record - a new finding, as distinct from a measurement.

<!-- tools-non-triggering -->
## Tools non triggering (Synthetic Data)

A fifth per-branch data folder, beside `Tool Clean (Synthetic Data)`,
`Tool Invalid (Synthetic Data)`, `Tool Triggering (Synthetic Data)` and
`Tool Triggering (Tool Github Test data)`.

Clean makes each tool run and report nothing wrong. Invalid makes it run and
report something. This folder holds data with nothing in it for any tool to
catch -- and, where no program would reach the tool at all, nothing for it to
start on. It is the negative control that tells *correctly detected nothing*
apart from *the scan never ran*.

`Tools non triggering (Synthetic Data)/` holds 17 tool-named folders, matching the names in Clean
and Invalid so the data sets line up name-for-name:

* **8 tools read source**, so they get a minimal program per boundary
  family (netfx45, netfx46, netcoreapp30, net9, net10) -- one class or one function, no branching, no
  duplication, no dependency, no dead export, no magic number.
* **9 tools cannot be answered by a program** -- they read a lockfile,
  a coverage report, compiled bytecode or the commit history -- so they carry
  the subject matter as a plain record instead, with the reason stated in that
  folder's own README.

**No manifest, no lockfile, no tool configuration, no runner and no
`trigger.yaml` anywhere in it**, so the folder adds no discovered project and
no task to a run.

See `Tools non triggering (Synthetic Data)/README.md` for the per-tool table, the mechanism each tool is
inert by, and what was measured.
