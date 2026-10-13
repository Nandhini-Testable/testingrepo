# unilyze -- REPAIRED, not absent

This folder previously said "NOT A TOOL." That was wrong, and this file corrects it.

## What was wrong

The original check searched **PyPI** (`unilyze` 0.2.1, an unrelated Unicode
character-info package, last released 2020-07-23) and **npm** (404), and
concluded no such C# tool exists. It never checked **NuGet** or **GitHub** --
the two places a .NET tool actually lives. `unilyze` is real:
<https://github.com/bigdra50/unilyze>, MIT licensed, actively maintained,
latest release **v0.6.1**, published on NuGet as `Unilyze` and also shipped as
self-contained binaries on GitHub Releases (`osx-arm64`, `osx-x64`,
`linux-x64`, `win-x64` -- no .NET installation required for those).

## What was verified, not assumed

v0.6.1's `linux-x64` release binary was downloaded from GitHub Releases and
actually run against this corpus's own domain code (`src/`, both this branch
and the net45 floor branch):

- `unilyze -p src -f json` reports real per-method `cyclomaticComplexity` and
  `cognitiveComplexity`. Its peak findings match `dataset.json`'s own planted
  fixtures **exactly** on both branches tested: `OrderKit.PricingService.PriceCents`
  for cyclomatic complexity, `OrderKit.RiskScorer.Score` for cognitive complexity.
- `unilyze hotspot -p src` computes real git-churn x complexity from `git log`
  (field `changeCount` is the raw churn count) -- a genuine alternative to
  pydriller for Code Churn, not a fabricated one.
- It is **Class B**: its own Roslyn-based syntax walk needs no restore, no
  build and no NuGet to analyze source, so the target framework is irrelevant
  to it -- same category as Lizard, jscpd and Semgrep, already active on every
  branch in this corpus including the net45 floor.
- It installs here from its **GitHub Release binary**, a channel this host's
  egress proxy does not block -- unlike the NuGet-gated `dotnet tool install`
  channel that Coverlet, AltCover, MiniCover, Stryker.NET and the Roslyn
  analyzer packages are all stuck behind in this corpus.

## What is still broken, and is NOT fixed by this correction

The sheet assigned `unilyze` as the alternative for **34** metrics across five
blocks. Only **18** of those are legitimate:

| Block | Metrics | Legitimate? |
|---|---|---|
| Cyclomatic Complexity | 6 | yes -- real `cyclomaticComplexity` field |
| Cognitive Complexity | 7 | yes -- real `cognitiveComplexity` field |
| Code Churn | 5 | yes -- real `hotspot` churn x complexity |
| All Definition Coverage | 6 | **no** |
| All Uses Coverage | 10 | **no** |

`unilyze`'s entire metric catalogue -- complexity, Halstead, LCOM-HS, WMC, the
Chidamber-Kemerer suite, Martin package metrics, Maintainability Index,
TypeRank, Code Health -- contains no def-use chains, no data-flow analysis of
any kind. Reassigning it as the alternative for the two Data Flow blocks would
repeat the exact mistake this correction fixes: a capability claim with
nothing behind it. Those 16 metrics remain exactly as broken as the roster
defects already on record -- see `tools/roslyn-dataflow/` for the proposed
real repair (Roslyn's `SemanticModel.AnalyzeDataFlow`), which is a separate,
still-open defect.

`run.sh` now exits **0** and produces real output, for the 18 metrics this
tool actually supports.