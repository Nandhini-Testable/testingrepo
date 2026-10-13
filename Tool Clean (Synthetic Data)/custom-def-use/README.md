# custom-def-use

Synthetic, clean-by-design Java project for **custom-def-use**.

Domain: grain-store seed-bin weighing (`SeedBin`).

**What this is**: the metric sheet lists "custom def-use (JaCoCo + AST)" for the data-flow metrics. That is a description of an approach, not a packaged tool: it names no package, version or vendor, so there is no binary to install. This folder is the Java data-flow fixture such an analysis would read. The one real def-use analyser available here is the sibling **ASM-DefUse** library (`../ASM-DefUse/vendor/asm-defuse.jar`, built from upstream source); it was used to measure the fixture, so the numbers below are real, but they are ASM-DefUse's numbers, not a "custom def-use" tool's.

## What a passing result looks like

Every local variable the methods define reaches at least one use: ASM-DefUse reports `ORPHAN_DEFINITIONS=0` for `netWeight`, `binsOver` and `grade` (loops, a counter, and a variable assigned on every branch of an if/else chain).

## Command

```bash
javac --release 8 -d build/main java8/src/main/java/seedbin/SeedBin.java
javac -d build/tools -cp ../ASM-DefUse/vendor/asm-defuse.jar:<asm jars> ../ASM-DefUse/tools/DefUseRunner.java
java -cp build/tools:../ASM-DefUse/vendor/asm-defuse.jar:<asm jars> DefUseRunner build/main/seedbin/SeedBin.class netWeight
```

Measured on `java8`: `netWeight` -> `VARIABLES=6 ORPHAN_DEFINITIONS=0`, `binsOver` -> `VARIABLES=4 ORPHAN_DEFINITIONS=0`, `grade` -> `VARIABLES=2 ORPHAN_DEFINITIONS=0`.

## Layout

```text
custom-def-use/
  README.md
  java8/ ... java25/        one folder per JDK family (18), identical source
    src/main/java/seedbin/SeedBin.java
    src/test/java/seedbin/SeedBinTest.java
```

## Notes

For each family N from 8 to 25 the source was compiled with `javac --release N` on a JDK 25 host and the ASM-DefUse cross-check run on that family's own class file (and the JUnit tests run); the table is that measurement. ASM 9.7, the version available here, reads class files up to major version 67 (Java 23), so the java24 and java25 rows fail for the same reason the ASM-DefUse folder's java24 and java25 rows do. JaCoCo-based coverage, the other half of the sheet's description, was not run on this fixture.

## Per-family results

| Family | Host JDK | Result |
|---|---|---|
| `java8` | JDK 25 host, `--release 8` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java9` | JDK 25 host, `--release 9` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java10` | JDK 25 host, `--release 10` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java11` | JDK 25 host, `--release 11` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java12` | JDK 25 host, `--release 12` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java13` | JDK 25 host, `--release 13` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java14` | JDK 25 host, `--release 14` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java15` | JDK 25 host, `--release 15` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java16` | JDK 25 host, `--release 16` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java17` | JDK 25 host, `--release 17` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java18` | JDK 25 host, `--release 18` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java19` | JDK 25 host, `--release 19` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java20` | JDK 25 host, `--release 20` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java21` | JDK 25 host, `--release 21` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java22` | JDK 25 host, `--release 22` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java23` | JDK 25 host, `--release 23` | CLEAN -- `ORPHAN_DEFINITIONS=0` in all 3 methods |
| `java24` | JDK 25 host, `--release 24` | **FINDING** -- ASM 9.7 cannot read class file major version 68 |
| `java25` | JDK 25 (native) | **FINDING** -- ASM 9.7 cannot read class file major version 69 |

