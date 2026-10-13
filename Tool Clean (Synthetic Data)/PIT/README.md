# PIT

Synthetic, clean-by-design Java project for **PIT**.

Domain: stone-grading quarry logic (StoneGrader)

**Not installed here**: see Notes for why, and what was checked instead.

## What a passing result looks like

PIT mutation testing on StoneGrader would report a 100% mutation score -- every mutant PIT's operators can generate in the grading logic is killed by the JUnit4 suite.

## Command

```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

## Notes

PIT is distributed purely through Maven Central / the Gradle Plugin Portal, both blocked at this sandbox's egress proxy (measured: 403 on `repo1.maven.org` and `plugins.gradle.org`). No apt package exists.

## Per-family results

Boundary families this tool was exploded into, and what each one's own real invocation found (not asserted -- see `_generator/verify_live.py`):

| Family | Host JDK | Result |
|---|---|---|
| `java8` | JDK 8 (native) | NOT INSTALLED |
| `java9` | JDK 11 host, `--release 9` | NOT INSTALLED |
| `java10` | JDK 25 host, `--release 10` | NOT INSTALLED |
| `java11` | JDK 25 host, `--release 11` | NOT INSTALLED |
| `java12` | JDK 25 host, `--release 12` | NOT INSTALLED |
| `java13` | JDK 25 host, `--release 13` | NOT INSTALLED |
| `java14` | JDK 25 host, `--release 14` | NOT INSTALLED |
| `java15` | JDK 25 host, `--release 15` | NOT INSTALLED |
| `java16` | JDK 17 host, `--release 16` | NOT INSTALLED |
| `java17` | JDK 25 host, `--release 17` | NOT INSTALLED |
| `java18` | JDK 25 host, `--release 18` | NOT INSTALLED |
| `java19` | JDK 25 host, `--release 19` | NOT INSTALLED |
| `java20` | JDK 25 host, `--release 20` | NOT INSTALLED |
| `java21` | JDK 25 host, `--release 21` | NOT INSTALLED |
| `java22` | JDK 25 host, `--release 22` | NOT INSTALLED |
| `java23` | JDK 25 host, `--release 23` | NOT INSTALLED |
| `java24` | JDK 25 host, `--release 24` | NOT INSTALLED |
| `java25` | JDK 25 (native) | NOT INSTALLED |

Families `java10` to `java15` and `java17` to `java23` were added after the original measurement, so that every tool has a
folder for every JDK family 8 to 25 (a branch's own family is always present, including the ones this tool cannot run).
Their source is the same file, byte for byte, as `java8` (identical git blob hashes -- nothing here is version-gated). Each
family was compiled with `javac --release N` on a JDK 25 host and its unit tests were run; they pass at every N from 8 to 25.
The result column repeats the blocker described above: it does not depend on the JDK family, and the tool itself cannot be run here, so nothing was run for these families beyond the compile-and-test check.


Distributed purely through Maven Central / the Gradle Plugin Portal, both blocked at the egress proxy; no apt package exists. No JDK family changes this.

