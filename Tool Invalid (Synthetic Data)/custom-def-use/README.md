# custom-def-use

Inverse-corpus counterpart to the sibling Java-Tools-Clean's **custom-def-use** folder. Engineered so the tool should find something genuinely wrong.

Domain: loading-dock pallet tallies (`PalletTally`).

**What this is**: the metric sheet lists "custom def-use (JaCoCo + AST)" for the data-flow metrics. That is a description of an approach, not a packaged tool: it names no package, version or vendor, so there is no binary to install. This folder is the Java data-flow fixture such an analysis would read. The one real def-use analyser available here is the sibling **ASM-DefUse** library (`../ASM-DefUse/vendor/asm-defuse.jar`, built from upstream source); it was used to measure the fixture, so the numbers below are real, but they are ASM-DefUse's numbers, not a "custom def-use" tool's.

## What a failing result looks like

The methods define variables that no use ever reads: `tally` defines `spare`, `checked`, `heaviest`, `lightest` and `doubled` and never reads them (5 of its 8 variables); `surcharge` defines `bonus` and `adjusted` (the last one only ever overwritten). ASM-DefUse reports the orphan definitions below.

## Command

```bash
javac --release 8 -d build/main java8/src/main/java/pallettally/PalletTally.java
javac -d build/tools -cp ../ASM-DefUse/vendor/asm-defuse.jar:<asm jars> ../ASM-DefUse/tools/DefUseRunner.java
java -cp build/tools:../ASM-DefUse/vendor/asm-defuse.jar:<asm jars> DefUseRunner build/main/pallettally/PalletTally.class tally
```

Measured on `java8`: `tally` -> `VARIABLES=8 ORPHAN_DEFINITIONS=5`, `surcharge` -> `VARIABLES=4 ORPHAN_DEFINITIONS=1`.

## Layout

```text
custom-def-use/
  README.md
  java8/ ... java25/        one folder per JDK family (18), identical source
    src/main/java/pallettally/PalletTally.java
    src/test/java/pallettally/PalletTallyTest.java
```

## Notes

For each family N from 8 to 25 the source was compiled with `javac --release N` on a JDK 25 host and the ASM-DefUse cross-check run on that family's own class file (and the JUnit tests run); the table is that measurement. ASM 9.7, the version available here, reads class files up to major version 67 (Java 23), so the java24 and java25 rows fail for the same reason the ASM-DefUse folder's java24 and java25 rows do. JaCoCo-based coverage, the other half of the sheet's description, was not run on this fixture.

## Per-family results

| Family | Host JDK | Result |
|---|---|---|
| `java8` | JDK 25 host, `--release 8` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java9` | JDK 25 host, `--release 9` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java10` | JDK 25 host, `--release 10` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java11` | JDK 25 host, `--release 11` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java12` | JDK 25 host, `--release 12` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java13` | JDK 25 host, `--release 13` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java14` | JDK 25 host, `--release 14` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java15` | JDK 25 host, `--release 15` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java16` | JDK 25 host, `--release 16` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java17` | JDK 25 host, `--release 17` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java18` | JDK 25 host, `--release 18` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java19` | JDK 25 host, `--release 19` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java20` | JDK 25 host, `--release 20` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java21` | JDK 25 host, `--release 21` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java22` | JDK 25 host, `--release 22` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java23` | JDK 25 host, `--release 23` | **FINDING** -- `ORPHAN_DEFINITIONS=5/1` |
| `java24` | JDK 25 host, `--release 24` | **FINDING** -- ASM 9.7 cannot read class file major version 68 |
| `java25` | JDK 25 (native) | **FINDING** -- ASM 9.7 cannot read class file major version 69 |

