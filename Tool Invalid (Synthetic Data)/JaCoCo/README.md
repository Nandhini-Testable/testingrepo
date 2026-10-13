# JaCoCo

Inverse-corpus counterpart to the sibling Java-Tools-Clean's **JaCoCo**
folder. Engineered so the real tool finds something genuinely wrong,
confirmed by actually running it -- not asserted.

Domain: furnace-temperature monitoring (FurnaceGauge)

**Measured**: installed and actually invoked in the build environment;
the result below is real, not asserted.

## What a failing result looks like

The real JaCoCo offline instrumenter runs FurnaceGauge's own JUnit4
test through instrumented bytecode and the resulting coverage is
genuinely weak: the test suite exercises only the `stageFor(100)` ->
"idle" path, leaving `temper`, `anneal`, `peak`, the over-temperature
throw, `isSafe` and `headroom` all unexercised. Measured instruction
coverage **23.91-27.50%**, branch coverage **20.00%** -- a clear
majority of FurnaceGauge's own logic goes untested.

## Command

```bash
java -javaagent:jacocoagent.jar=destfile=jacoco.exec -cp ... org.junit.runner.JUnitCore FurnaceGaugeTest && java -jar jacococli.jar report jacoco.exec --classfiles ... --xml coverage.xml
```

## Notes

Same installed `libjacoco-java` 0.8.11 as the sibling Clean corpus
(`apt install libjacoco-java`); only FurnaceGauge's own (deliberately
under-tested) source and test suite differ.

## Per-family results

Boundary families this tool was exploded into, and what each one's own
real invocation found (not asserted -- see `_generator/verify_live.py`):

| Family | Host JDK | Result |
|---|---|---|
| `java8` | JDK 8 (native) | **FINDING** -- `INSTRUCTION_PCT=23.91 BRANCH_PCT=20.00` |
| `java9` | JDK 11 host, `--release 9` | **FINDING** -- `INSTRUCTION_PCT=27.50 BRANCH_PCT=20.00` |
| `java10` | JDK 25 host, `--release 10` | not run (compile + unit tests only) |
| `java11` | JDK 25 host, `--release 11` | not run (compile + unit tests only) |
| `java12` | JDK 25 host, `--release 12` | not run (compile + unit tests only) |
| `java13` | JDK 25 host, `--release 13` | not run (compile + unit tests only) |
| `java14` | JDK 25 host, `--release 14` | not run (compile + unit tests only) |
| `java15` | JDK 25 host, `--release 15` | not run (compile + unit tests only) |
| `java16` | JDK 17 host, `--release 16` | **FINDING** -- `INSTRUCTION_PCT=27.50 BRANCH_PCT=20.00` |
| `java17` | JDK 25 host, `--release 17` | not run (compile + unit tests only) |
| `java18` | JDK 25 host, `--release 18` | not run (compile + unit tests only) |
| `java19` | JDK 25 host, `--release 19` | not run (compile + unit tests only) |
| `java20` | JDK 25 host, `--release 20` | not run (compile + unit tests only) |
| `java21` | JDK 25 host, `--release 21` | not run (compile + unit tests only) |
| `java22` | JDK 25 host, `--release 22` | not run (compile + unit tests only) |
| `java23` | JDK 25 host, `--release 23` | not run (compile + unit tests only) |
| `java24` | JDK 25 host, `--release 24` | **FINDING** -- ASM ceiling (see below) |
| `java25` | JDK 25 (native) | **FINDING** -- ASM ceiling (see below) |

Families `java10` to `java15` and `java17` to `java23` were added after the original measurement, so that every tool has a
folder for every JDK family 8 to 25 (a branch's own family is always present, including the ones this tool cannot run).
Their source is the same file, byte for byte, as `java8` (identical git blob hashes -- nothing here is version-gated). Each
family was compiled with `javac --release N` on a JDK 25 host and its unit tests were run; they pass at every N from 8 to 25.
The tool itself was **not run** on these thirteen families, so their result column says so instead of guessing; the rows for `java8`, `java9`, `java16`, `java24` and `java25` are the measured ones.


Genuine finding(s) on this tool:

**java8/java9/java16**: branch coverage measured at 20.00% (instruction
coverage 23.91-27.50%, the small native-vs-cross-compiled bytecode
difference is real and expected) -- a real, majority-uncovered result
from the real JaCoCo instrumenter and analyzer, not an edge case.

**java24, java25**: JaCoCo's offline instrumenter uses the same
apt-installed ASM 9.7 internally (Debian's jacoco package links the
system libasm-java rather than bundling its own copy). Instrumenting a
Java-24/25-targeted FurnaceGauge.class throws the identical
`IllegalArgumentException: Unsupported class file major version
68/69` inside `InstrSupport.classReaderFor`. Identical root cause to
the sibling Clean corpus's own JaCoCo folder, independent of this
corpus's own planted under-coverage -- a genuine environmental ceiling,
so this cell is "not CLEAN" (and thus FINDING) for a reason unrelated
to the planted test gaps.
