# Checkstyle

Inverse-corpus counterpart to the sibling Java-Tools-Clean's **Checkstyle**
folder. Engineered so the real tool finds something genuinely wrong,
confirmed by actually running it -- not asserted.

Domain: valve-panel signal control (ValveStation)

**Measured**: installed and actually invoked in the build environment;
the result below is real, not asserted.

## What a failing result looks like

`checkstyle -c /usr/share/checkstyle/sun_checks.xml` reports a real
pile of `[ERROR]` lines against ValveStation's source: a star import, a
badly-cased constant and field, tab characters, missing Javadoc on
every public member, non-final parameters, and an over-length line --
**25 real errors**, confirmed by direct invocation.

## Command

```bash
checkstyle -c sun_checks.xml src/main/java/**/*.java
```

## Notes

Same installed `checkstyle-8.36.1.jar` as the sibling Clean corpus
(`apt install checkstyle`); only ValveStation's own source differs.

## Per-family results

Boundary families this tool was exploded into, and what each one's own
real invocation found (not asserted -- see `_generator/verify_live.py`):

| Family | Host JDK | Result |
|---|---|---|
| `java8` | JDK 8 (native) | **FINDING** |
| `java9` | JDK 11 host, `--release 9` | **FINDING** |
| `java10` | JDK 25 host, `--release 10` | not run (compile + unit tests only) |
| `java11` | JDK 25 host, `--release 11` | not run (compile + unit tests only) |
| `java12` | JDK 25 host, `--release 12` | not run (compile + unit tests only) |
| `java13` | JDK 25 host, `--release 13` | not run (compile + unit tests only) |
| `java14` | JDK 25 host, `--release 14` | not run (compile + unit tests only) |
| `java15` | JDK 25 host, `--release 15` | not run (compile + unit tests only) |
| `java16` | JDK 17 host, `--release 16` | **FINDING** |
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


Genuine finding(s) on this tool: 25 real `[ERROR]` lines against a
single ~35-line class -- a clear majority of ValveStation's own
constructs (every field, every method, the only import, two lines)
trip at least one real sun_checks rule. Identical across all five
families since Checkstyle reads only source text, never bytecode.
