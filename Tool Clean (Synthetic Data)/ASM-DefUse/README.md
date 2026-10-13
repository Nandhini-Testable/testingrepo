# ASM-DefUse

Synthetic, clean-by-design Java project for **ASM-DefUse**.

Domain: fee-ledger arithmetic (`FeeLedger`) analysed at the bytecode level.

**Measured**: installed (or built from real source) and actually invoked in the build environment; the result below is real, not asserted.

## What a passing result looks like

asm-defuse's `FlowAnalyzer`/`DepthFirstDefUseChainSearch` walk `FeeLedger.class` and find a def-use chain for every local-variable definition -- zero definitions with no reachable use.

## Command

```bash
javac -d build/main -cp vendor/asm-defuse.jar:<asm jars> src/main/java/feeledger/FeeLedger.java
javac -d build/tools -cp vendor/asm-defuse.jar:<asm jars> tools/DefUseRunner.java
java -cp build/tools:build/main:vendor/asm-defuse.jar:<asm jars> DefUseRunner build/main/feeledger/FeeLedger.class computeFee
```

Expected: `ORPHAN_DEFINITIONS=0 CHAINS=9`

## Layout

```text
ASM-DefUse/
  README.md
  src/main/java/feeledger/FeeLedger.java   the analysed class -- static computeFee(double)
  src/test/java/feeledger/FeeLedgerTest.java
  tools/DefUseRunner.java                  drives asm-defuse's real API against the compiled class
  vendor/asm-defuse.jar                    built from source, see Notes
```

## Notes

`br.usp.each.saeg:asm-defuse` is not reachable via Maven Central here (blocked at this sandbox's egress proxy), so it was built from its real upstream source instead of asserted or faked:

1. `git clone https://github.com/saeg/asm-defuse` and `https://github.com/saeg/saeg-commons` -- the second is asm-defuse's one real dependency, found by reading the first project's own `pom.xml` for the exact `groupId:artifactId` it declares, not guessed.
2. Both were compiled with `javac` against the real `org.ow2.asm` 9.7 jars this sandbox already has installed via `apt install libasm-java` (asm, asm-tree, asm-analysis, asm-commons, asm-util -- the full module split).
3. The resulting classes were merged into `vendor/asm-defuse.jar` (39 classes: 16 from saeg-commons, 23 from asm-defuse's own main source).

The library itself is genuine, unmodified upstream source; only the fetch mechanism (clone + local compile instead of a Maven Central download) differs from how a project would normally pull the dependency in.

`tools/DefUseRunner.java` then drives asm-defuse's actual public API against the compiled `FeeLedger.class`: `DefUseAnalyzer` (wrapping `FlowAnalyzer` + `DefUseInterpreter`) builds the control-flow frames, and `DepthFirstDefUseChainSearch.search(...)` walks them to produce the real `DefUseChain` list. `FeeLedger.computeFee` is written `static` -- an instance method would leave the implicit `this` reference defined but never explicitly "used" in a chain, which is a genuine (if uninteresting) orphan definition, not a bug in asm-defuse.

## Per-family results

Boundary families this tool was exploded into, and what each one's own real invocation found (not asserted -- see `_generator/verify_live.py`):

| Family | Host JDK | Result |
|---|---|---|
| `java8` | JDK 8 (native) | CLEAN |
| `java9` | JDK 11 host, `--release 9` | CLEAN |
| `java10` | JDK 25 host, `--release 10` | not run (compile + unit tests only) |
| `java11` | JDK 25 host, `--release 11` | not run (compile + unit tests only) |
| `java12` | JDK 25 host, `--release 12` | not run (compile + unit tests only) |
| `java13` | JDK 25 host, `--release 13` | not run (compile + unit tests only) |
| `java14` | JDK 25 host, `--release 14` | not run (compile + unit tests only) |
| `java15` | JDK 25 host, `--release 15` | not run (compile + unit tests only) |
| `java16` | JDK 17 host, `--release 16` | CLEAN |
| `java17` | JDK 25 host, `--release 17` | not run (compile + unit tests only) |
| `java18` | JDK 25 host, `--release 18` | not run (compile + unit tests only) |
| `java19` | JDK 25 host, `--release 19` | not run (compile + unit tests only) |
| `java20` | JDK 25 host, `--release 20` | not run (compile + unit tests only) |
| `java21` | JDK 25 host, `--release 21` | not run (compile + unit tests only) |
| `java22` | JDK 25 host, `--release 22` | not run (compile + unit tests only) |
| `java23` | JDK 25 host, `--release 23` | not run (compile + unit tests only) |
| `java24` | JDK 25 host, `--release 24` | **FINDING** |
| `java25` | JDK 25 (native) | **FINDING** |

Families `java10` to `java15` and `java17` to `java23` were added after the original measurement, so that every tool has a
folder for every JDK family 8 to 25 (a branch's own family is always present, including the ones this tool cannot run).
Their source is the same file, byte for byte, as `java8` (identical git blob hashes -- nothing here is version-gated). Each
family was compiled with `javac --release N` on a JDK 25 host and its unit tests were run; they pass at every N from 8 to 25.
The tool itself was **not run** on these thirteen families, so their result column says so instead of guessing; the rows for `java8`, `java9`, `java16`, `java24` and `java25` are the measured ones.


Genuine finding(s) on this tool:

**java24**: apt's libasm-java 9.7 (also what org.jacoco.core links against on Debian) cannot parse a Java-24-targeted class file: `ClassReader.<init>` throws `IllegalArgumentException: Unsupported class file major version 68`. ASM 9.7 is the latest release reachable in this sandbox (Maven Central, where 9.8 would come from, is blocked at the egress proxy); no upgrade path exists here.

**java25**: Same root cause one major version further: major version 69 (Java 25) is also past ASM 9.7's ceiling. Same `IllegalArgumentException: Unsupported class file major version 69`, same unreachable fix.

