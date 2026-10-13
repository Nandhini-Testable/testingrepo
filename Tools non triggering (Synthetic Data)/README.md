# Java tools non-triggering corpus

20 tool-named folders, one per tool, matching the folder names in
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
it simply does not run. Shipping it a `Main.java` would state a verdict
this folder cannot support.

So each tool gets whichever shape is true for it.

### 8 tools get a minimal program

`java8` through `java25` -- all 18 families, one program each, matching the family split in `Tool Clean`.
One class or one function, no branching, no duplication, no dependency, no
dead export, no magic number. No manifest, no tool config.

| Tool | Reads source because it |
| --- | --- |
| CK | parses source with its bundled Eclipse JDT for OO metrics |
| CPD | tokenises source and compares token runs |
| Checkstyle | checks parsed source against a rule set |
| Lizard | counts methods and complexity from its own tokeniser |
| PMD | checks parsed source against a rule set |
| Spoon | parses source into its own analysable model |
| nullaway | reads source during a javac compilation it attaches to |
| sonar | analyses a source set, when it has a project and a server |

### 12 tools get an inert record instead

| Tool | No program would help because it |
| --- | --- |
| ASM-DefUse | walks compiled method bodies for def-use pairs |
| FindSecBugs | runs SpotBugs security detectors over .class files |
| Grype | resolves coordinates from an SBOM, build file or artifact |
| JaCoCo | records probe hits while instrumented code runs |
| OWASP Dependency-Check | fingerprints a declared or bundled dependency set |
| PIT | re-runs a test suite against each mutant |
| SpotBugs | scans .class files for bug patterns, never source |
| ba-dua | measures def-use pairs exercised by a running suite |
| custom-def-use | walks a compiled method body |
| diff-cover | intersects a coverage report with a diff |
| git-churn | computes churn from commit history |
| pydriller | iterates the repository's commits |

## What the code deliberately avoids, and why

Java's Clean folder splits every tool across all 18 families rather than five
boundary versions, so this folder does too. Syntax is gated by era: classic
classes before 10, `var` from 10, switch expressions from 14, records from 16,
`formatted` from 21. The code clears Checkstyle's common rule sets (Javadoc on
every type and method, one top-level class per file, no magic number) and PMD's
quickstart set (no unused local, no empty catch, no missing braces).

### Measured

Every family compiled with its own release target: `javac --release 8 .. 21`
for fourteen families, and `--release 21` for java22-25 because the build host
carries JDK 21 and cannot target later. **18 of 18 compile clean.** That the
last four are verified as valid Java 21 source rather than against a JDK of
their own version is stated here rather than implied.

Compiling every family is what found the one real defect: a static factory
named `carried` is rejected inside a record as `invalid accessor method in
record`, because `carried` is already a component accessor. Four families
failed on it. No linter would have caught that, and neither would compiling a
single family.

| Tool | Result |
| --- | --- |
| javac, all 18 families | 0 errors |
| lizard | 128 methods, average CCN 1.0, 0 warnings |
| detect-secrets | 0 matches |
| semgrep (security ruleset) | 0 findings |
| jscpd (5 lines / 30 tokens) | 61 clones, 13.73% of java tokens |

### On that duplication figure

It is not zero, unlike the sibling corpora, and it should be read against the
folder it mirrors. The same measurement over this corpus's own `Tool Clean
(Synthetic Data)`, across three tool folders:

| Folder | Clones | Duplicated java tokens |
| --- | --- | --- |
| `Tool Clean (Synthetic Data)` | 106 | **94.44%** |
| this folder | 61 | **13.73%** |

Clean's figure is not a defect: the byte-identical-domain invariant requires
the same project in every family, so near-total duplication is the design
working. This folder carries 18 families per tool for the same alignment
reason, and Java's boilerplate -- Javadoc, a private constructor, a method
signature -- is large relative to the one line that varies.

Three selection schemes were measured before settling: independent
per-dimension hashes gave 100 clones, body-by-tool with everything else by
family gave 166, and the scheme shipped here -- every dimension offset by both
the family index and the tool name -- gives 61. Driving it to zero would mean
abandoning per-family alignment with Clean, which is worth more than the number.

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
    <first family>/Main.java  ...  <last family>/Main.java
  <Tool Name>/              INERT
    README.md
    record.txt
```

## Verifying

```bash
python3 verify.py "<path to this folder>"
python3 measure.py "<path to this folder>" --bin <node_modules/.bin>
```
