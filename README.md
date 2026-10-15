# Testable Java corpus — JV_V13_MAVEN_THINJAR_MONO

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
| Java version | 13 |
| Host JDK | 17 |
| Build system | Maven |
| Packaging | Thin jar |
| Architecture | Monolith |

## Supported tools

19 tools are wired on this branch (one `Tool Triggering (Synthetic Data)/<dir>/` folder
each). **14 of them run on JDK 13 (host JDK 17); 5 do not.**

That is the measurement, not a defect. A tool that cannot run exits **3**,
not 0 -- a skip that looks like a pass is the failure mode this corpus
exists to expose. Every `trigger.yaml` records the real, specific reason
when a tool can't run on this family.

### Running here

| Tool | Role | Block |
|---|---|---|
| `checkstyle` | primary | Lint / Rule Violations |
| `ck` | primary | Cyclomatic Complexity |
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
| `custom-def-use` | n/a (placeholder) | Data Flow Testing | not a tool - the sheet names no package, version or vendor |
| `nullaway` | primary | All Definition Coverage | runs as an Error Prone compiler plugin, not a standalone step |
| `sonar` | primary | Coverage Delta | needs a running SonarQube server; set SONAR_HOST_URL to enable |

## Build

```
mvn -B clean package
```

Main and test sources both compile at Java 13 (bytecode major version 57), built by
`javac 17` with `--release 13`.

**The Java 13 lock is an API lock, not a syntax lock — the second family in a row.** Java
13's two headline language changes were both *preview*: text blocks (JEP 355, preview in
13, final in 15) and switch expressions (still preview in 13, final in 14). Under the
corpus rule that preview features stay off, Java 13 contributes **no final syntax at all**.
`javac 17 --release 13` says so directly: *"text blocks are not supported in -source 13"*.

The lock is carried by three APIs that shipped final in Java 13, across two files:

| API | Where | JDK issue |
|---|---|---|
| `ByteBuffer.get(int, byte[])` / `put(int, byte[])` | `util/OrderCodec.java` | JDK-5029431 |
| `CharBuffer.get(int, char[])` | `util/OrderCodec.java` | JDK-5029431 |
| `FileSystems.newFileSystem(Path, Map)` | `util/PriceListArchive.java` | JDK-8218875 |

The absolute bulk transfers are a real API, not a curiosity: before Java 13, a bulk read at
a known offset meant mutating the buffer's position, which is why the absolute forms were
added. `OrderCodec.peekSku` reads the same record twice and still decodes it, which is only
safe because of them.

The branch fails to compile under `--release 12` in exactly five places, and the Java 17
family's own lock files still fail under `--release 13` (*sealed classes*, *text
blocks*, *pattern matching in instanceof*, *switch expressions* — all "not supported in
-source 13"). The differentiation holds in both directions.

### A rejected lock worth recording: `String.formatted` is not monotonic

`String.formatted`, `String.stripIndent` and `String.translateEscapes` look like ideal Java
13 locks — they compile at `--release 13` and fail at `--release 12`. They are **not used
here**, because they do not survive the next release. Measured with `javac 17`/`javac 25`:

| `--release` | 12 | 13 | 14 | 15 | 17 | 21 | 25 |
|---|---|---|---|---|---|---|---|
| `String.formatted` | absent | **present** (warning) | **preview API: error** | present | present | present | present |

They shipped in JDK 13 alongside preview text blocks, carried
`@Deprecated(forRemoval=true)`, were **reclassified as preview APIs in JDK 14**, and became final in
JDK 15. At `--release 14` javac rejects them (*"is a preview API and is disabled by default"*).
At `--release 13` javac accepts them but warns *"has been deprecated and marked for
removal"*.

This corpus's ladder is cumulative — each family keeps the one below it and adds to it — so
a lock that stops compiling one release later would break that property and make any future java14
family inconsistent with java13. The three buffer and filesystem APIs above are monotonic
from 13 through 25 and were chosen for that reason. **A version lock has to be checked
forward as well as backward**, and this is the first case in the corpus where an API that
passes the backward check fails the forward one.

Java 13 is **not an LTS release**. It shipped September 2019 and reached end of life in
March 2020, six months later. It is in this corpus to complete the version axis, not as a
recommendation.

Produces: `dist/jv-193.jar or target/jv-193-1.0.0.jar`

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
Java 13, so its own family is the `java13` folder inside each tool. Every
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
