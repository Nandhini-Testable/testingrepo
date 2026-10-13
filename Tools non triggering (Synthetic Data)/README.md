# C# tools non-triggering corpus

17 tool-named folders, one per tool, matching the folder names in
`Tool Clean (Synthetic Data)` and `Tool Invalid (Synthetic Data)` so the three
data sets line up name-for-name.

Clean makes each tool run and report nothing wrong. Invalid makes it run and
report something. This folder holds data with nothing in it for any tool to
catch -- and, where the tool cannot be given a program at all, nothing for it
to start on.

## Why the folder is split two ways

A hello world, one class with one function, a `main` file -- that answers the
question for a tool that reads source. It answers nothing for a tool that reads
a lockfile, a coverage report, or the commit history: a dependency scanner
handed a program has no dependency set, so it does not run and find nothing,
it simply does not run. Shipping it a `Main.cs` would state a verdict
this folder cannot support.

So each tool gets whichever shape is true for it.

### 8 tools get a minimal program

`netfx45`, `netfx46`, `netcoreapp30`, `net9` and `net10`, one program each, matching the family split in `Tool Clean`.
One class or one function, no branching, no duplication, no dependency, no
dead export, no magic number. No manifest, no tool config.

| Tool | Reads source because it |
| --- | --- |
| Dolos | compares parsed source files for similarity |
| Lizard | counts methods and complexity from its own tokeniser |
| Opengrep | matches rule patterns against parsed source |
| Roslyn | parses source into syntax trees |
| Roslyn SAST | runs security analyzers over syntax trees |
| Semgrep | matches rule patterns against parsed source |
| jscpd | tokenises files and compares token runs |
| unilyze | reads a project's source as well as its IL |

### 9 tools get an inert record instead

| Tool | No program would help because it |
| --- | --- |
| Coverlet | instruments an assembly and needs a test run |
| MiniCover | instruments IL, then reports what a test run hit |
| NuGet Audit | resolves a package graph from a project file |
| OpenTelemetry .NET | emits spans from a running process |
| Stryker.NET | builds the project and re-runs tests per mutant |
| Trivy | resolves coordinates from a manifest or artifact |
| altcover | rewrites a compiled assembly's IL |
| diff-cover | intersects a coverage report with a diff |
| pydriller | iterates the repository's commits |

## What the code deliberately avoids, and why

Syntax is gated by era, because this corpus spans C# 5 to C# 14: no string
interpolation or expression-bodied members before `netfx46` (both are C# 6),
no switch expressions or `using` declarations before `netcoreapp30` (C# 8), and
file-scoped namespaces only on the two newest families. Writing a modern
construct into `netfx45` would be a compile error rather than a finding, which
is a different kind of wrong but still wrong.

Identifiers carry their family suffix, the same convention `Tool Clean` uses
(`ObservationRequestV9`), so the whole set has no duplicate type name.

The code also clears `unilyze`'s documented default smell thresholds - GodClass,
LongMethod, HighComplexity, DeepNesting, ExcessiveParameters, LowCohesion,
HighCoupling, DeepInheritance, LowMaintainability, CyclicDependency - since
unilyze reads source as well as IL and is therefore one of the few tools on
this roster a .cs file genuinely reaches.

## What could NOT be verified here, and why that matters

**No C# compiler was available in the build environment.** `dotnet` is not
installed, the .NET SDK is not in the apt archive reachable from here, and
`builds.dotnet.microsoft.com` is outside the egress allowlist - the same
blocker this corpus has hit from the start. Mono is absent too.

So unlike the Java folder, where compiling all 18 families is what caught a
real defect, **this folder's C# has not been compiled**. It was parsed, which
is weaker:

| Check | Tool | Result |
| --- | --- | --- |
| Parse | tree-sitter-c-sharp | every file, 0 errors |
| Parse + patterns | semgrep 1.180.0 (csharp) | every file scanned, 0 parse errors, 0 findings |
| Complexity | lizard | 0 warnings |
| Duplication | jscpd | see below |

A parse is not a compile. Tree-sitter accepts source a compiler would reject -
an undefined type, a bad override, an unassigned readonly field - so the
honest status of this folder is **parsed, not compiled**, and it should be
compiled on a host with a real SDK before it is trusted the way the other four
are. That is stated here rather than left for someone to assume.

## Invariants

| # | Invariant |
| --- | --- |
| G2 | No manifest and no lockfile anywhere, so the folder adds no discovered project and no task. |
| G3 | No tool configuration file. |
| G4 | No coverage report, SBOM or other consumed artifact. |
| G5 | Pure ASCII. |
| G6 | No secret-shaped strings. |
| G7 | Zero duplicate blocks at 5 lines / 30 tokens, across both shapes. |

`verify.py` checks all of them. G1 of the earlier draft -- no source extension
anywhere -- is gone by design: the CODE folders now carry real source.

## Layout

```text
Tools non triggering (Synthetic Data)/
  README.md
  <Tool Name>/              CODE
    README.md
    <first family>/Main.cs  ...  <last family>/Main.cs
  <Tool Name>/              INERT
    README.md
    record.txt
```

## Verifying

```bash
python3 verify.py "<path to this folder>"
python3 measure.py "<path to this folder>" --bin <node_modules/.bin>
```
