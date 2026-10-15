# Testable Java corpus — JV_V23_MAVEN_THINJAR_MONO

Grid cell `MVN-THIN-M` of the 24-cell Java grid.

## Project type

Order pricing and risk domain. The domain layer is byte-identical across all 24 branches
in this family, so any difference in tool output is attributable to the branch variables
below and not to the code the tool was pointed at.

## Branches

This repository holds 432 orphan branches, one per grid cell across every Java version, build system and packaging combination in this corpus. `main` carries the title
only. See `dataset.json` for the machine-readable description of this branch.

## Branch variables

| Variable | Value |
|---|---|
| Java version | 23 |
| Host JDK | 25 |
| Build system | Maven |
| Packaging | Thin jar |
| Architecture | Monolith |

## Supported tools

19 tools are wired on this branch (one `Tool Triggering (Synthetic Data)/<dir>/` folder
each). **13 of them run on JDK 23 (host JDK 25); 6 do not.**

That is the measurement, not a defect. A tool that cannot run exits **3**,
not 0 -- a skip that looks like a pass is the failure mode this corpus
exists to expose. Every `trigger.yaml` records the real, specific reason
when a tool can't run on this family.

### Running here

| Tool | Role | Block |
|---|---|---|
| `checkstyle` | primary | Lint / Rule Violations |
| `cpd` | primary | Code Duplication |
| `diff-cover` | primary | Coverage Delta |
| `git-churn` | primary | Code Churn |
| `grype` | alternative | Dependency Risk (SCA) |
| `jacoco` | primary | Statement / Branch / Path Coverage |
| `lizard` | alternative | Cyclomatic Complexity |
| `owasp-dependency-check` | primary | Dependency Risk (SCA) |
| `pit` | primary | Mutation Score |
| `pmd` | primary | Cognitive Complexity |
| `pydriller` | alternative | Code Churn |
| `spoon` | primary | Data Flow Testing |
| `spotbugs` | primary | Static Vulnerabilities (SAST) |

### Dark here

| Tool | Role | Block | Why |
|---|---|---|---|
| `asm-defuse` | primary | Data Flow Testing | no jar published under a stable coordinate - place one in Tool Triggering (Synthetic Data)/asm-defuse/ |
| `ba-dua` | primary | All Definition Coverage | ba-dua 0.8.0 links JaCoCo 0.8.1, which stops at Java 10 class files |
| `ck` | primary | Cyclomatic Complexity | CK 0.7.0 bundles Eclipse JDT 3.26.0, which parses only up to Java 16 - it cannot read this branch's records or sealed types |
| `custom-def-use` | n/a (placeholder) | Data Flow Testing | not a tool - the sheet names no package, version or vendor |
| `nullaway` | primary | All Definition Coverage | runs as an Error Prone compiler plugin, not a standalone step |
| `sonar` | primary | Coverage Delta | needs a running SonarQube server; set SONAR_HOST_URL to enable |

## Build

```
mvn -B clean package
```

Main and test sources both compile at Java 23 (bytecode major version 67), built by
`javac 25` with `--release 23`.

**Java 23 added no final language syntax.** Its one language JEP was **Markdown documentation
comments** (JEP 467) - and `///` is lexically an ordinary line comment, so it compiles
unchanged all the way back to Java 8 and locks nothing. Verified rather than assumed: a file
using `///` compiles clean at `--release 21, 22, 23` and `25` alike. Everything else Java 23
carried was preview or incubator:

| Feature | Status in Java 23 | Final in |
|---|---|---|
| primitive types in patterns (JEP 455) | preview | - |
| module import declarations (JEP 476) | preview | 25 |
| implicitly declared classes (JEP 477) | 3rd preview | 25 |
| flexible constructor bodies (JEP 482) | 2nd preview | 25 |
| stream gatherers (JEP 473) | 2nd preview | 24 |
| Class-File API (JEP 466) | 2nd preview | 24 |
| structured concurrency (JEP 480) | 3rd preview | - |
| scoped values (JEP 481) | 3rd preview | 25 |

So java23 is API-only - the **fifth** such family, after 12, 13, 18, 19 and 20.

### The ct.sym diff again, and what it found

As at java20, no feature list names Java 23's final API additions, so the lock was found by
diffing `javac`'s `ct.sym` release signatures between 22 and 23 (`M` and `N` in `javac 25`'s
table). That turned up 45 changed `java.base` classes and 23 with added public members. Most
were `java.lang.classfile` - the **preview** Class-File API - and correctly excluded. Four
families of addition survive as final and monotonic through 25, all in
`util/StrictFormats.java`:

| API | What it changes |
|---|---|
| `NumberFormat.setStrict` / `isStrict` | parsing stops guessing |
| `Instant.until(Instant)` | a `Duration` directly, without `Duration.between` |
| `Inet4Address.ofPosixLiteral` | one documented parser for the POSIX address forms |
| `MemorySegment.maxByteAlignment` | on the FFM API this corpus introduced at java22 |

Seven errors at `--release 22`, all in that one file; whole-family and per-file agree, because
there is no parse-time lock to halt on.

### Two of these close real defect classes

**Strict parsing.** `NumberFormat.parse` has always stopped at the first character it cannot
use and returned what it had, so `"12abc"` parses to `12` and `"1,2,3"` to `123`. For a value
arriving from outside the system that is a silent-corruption bug, not a convenience. Java 23
added `setStrict(true)` to make both a `ParseException`. The test asserts the difference
directly: the same input, one lenient parse returning `12`, one strict parse rejecting.

**POSIX address literals.** `0177.0.0.1`, `0x7f.0.0.1`, `127.1` and `2130706433` all denote
127.0.0.1, because `inet_addr` accepts octal, hex and shortened forms. A filter that
blocklists the string `"127.0.0.1"` stops none of them, and before Java 23 the JDK's own
parsers disagreed about which forms they accepted. `Inet4Address.ofPosixLiteral` is one method
with documented semantics, which is what lets a caller normalise first and compare afterwards.
The test asserts all four forms normalise to the same string - the corpus's SAST fixtures now
have a real, non-synthetic example of a normalisation bypass.

Forward-checked at `--release 24` and `25`: clean.

Java 23 is **not an LTS release**. It shipped September 2024 and reached end of life in March
2025, six months later. It is in this corpus to complete the version axis, not as
a recommendation.

Produces: `dist/jv-385.jar or target/jv-385-1.0.0.jar`

## Run

```
java -jar <artifact> O-1234
```

## Test

```
mvn -B test
```

## Workspace projects

- `src/main/java/` (single module)


## Tool test-data folders

Three sibling folders sit at the repo root, alongside this branch's own
`Tool Triggering (Synthetic Data)/` (above).

### `Tool Triggering (Tool Github Test data)/`
16 of this branch's 19 wired tools carry their own real upstream test suite
or source, pulled as-is from that tool's actual GitHub project -- not
generated: `CK/`, `Spoon/`, `JaCoCo/`, `PMD/`, `SpotBugs/`, `FindSecBugs/`,
`Checkstyle/`, `CPD/`, `PIT/`, `OWASP Dependency-Check/`, `Grype/`, `Lizard/`,
`ASM-DefUse/`, `ba-dua/`, `diff-cover/` and `pydriller/`. The remaining 3
wired tools have no folder here because there is no applicable upstream test
suite to pull: `git-churn` is just git's own log, not a packaged tool;
`nullaway` runs embedded as an Error Prone compiler plugin, not a standalone
artifact; and `sonar` needs a live SonarQube server rather than a static
test suite. (`custom-def-use` is the corpus's own deliberate non-tool
placeholder -- see the Supported tools table above -- so it was never a
candidate for this folder either.)

### `Tool Clean (Synthetic Data)/`
Most tools here carry 18 generated fixture packages, one per JDK family
(`java8` to `java25`), engineered to be clean so the tool should report
zero findings: the **Tool Clean (100% pass)** condition. This branch is
Java 23, so its own family is the `java23` folder inside each tool. Every
tool has a folder for every family, including families it cannot run
(those report not-installed or skipped, they are not left out). The real
tools were run on five of the families (8, 9, 16, 24, 25); the other
thirteen hold the same source, compiled and unit-tested at their own
`--release` only. The tools are `custom-def-use`, `git-churn`, `nullaway`
and `sonar` as well as the sixteen already there.

### `Tool Invalid (Synthetic Data)/`
Same shape as Clean -- the same 18 JDK-family folders -- but engineered so
every fixture makes the tool flag or fail rather than pass: the
**Tool Invalid** condition. `diff-cover`, `git-churn` and `pydriller` each carry one
real git repository's worth of history in both Clean and Invalid rather than 18
per-version copies, restored from `_git-bundles/` via `restore-git.ps1`
rather than kept as a live `.git` folder, so a plain file copy never
silently drops their content.

## Tool entry points

Each tool has `Tool Triggering (Synthetic Data)/<tool>/trigger.yaml` and `Tool Triggering (Synthetic Data)/<tool>/run.sh`. Runners follow the
exit-code contract: 0 ran, 1 failed, 3 skipped-cannot-run, 4 not-installed.

## Planted CVE pins

| Dependency | Version |
|---|---|
| `commons-collections:commons-collections` | 3.2.1 |
| `org.apache.commons:commons-text` | 1.9 |
| `com.fasterxml.jackson.core:jackson-databind` | 2.9.10.1 |
| `log4j:log4j` | 1.2.17 |
| `org.yaml:snakeyaml` | 1.30 |

Deliberately vulnerable versions, so the SCA metrics have known true positives. Do not
upgrade them.

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

`Tools non triggering (Synthetic Data)/` holds 20 tool-named folders, matching the names in Clean
and Invalid so the data sets line up name-for-name:

* **8 tools read source**, so they get a minimal program per boundary
  family (java8 .. java25 (all 18)) -- one class or one function, no branching, no
  duplication, no dependency, no dead export, no magic number.
* **12 tools cannot be answered by a program** -- they read a lockfile,
  a coverage report, compiled bytecode or the commit history -- so they carry
  the subject matter as a plain record instead, with the reason stated in that
  folder's own README.

**No manifest, no lockfile, no tool configuration, no runner and no
`trigger.yaml` anywhere in it**, so the folder adds no discovered project and
no task to a run.

See `Tools non triggering (Synthetic Data)/README.md` for the per-tool table, the mechanism each tool is
inert by, and what was measured.
