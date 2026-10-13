# FindSecBugs

Synthetic, clean-by-design Java project for **FindSecBugs**.

Domain: vault-access control (VaultGate)

**Not installed here**: see Notes for why, and what was checked instead.

## What a passing result looks like

FindSecBugs is a SpotBugs plugin and cannot run without SpotBugs itself (also not installed here -- see the SpotBugs folder). `semgrep --config p/java --config p/security-audit` -- the same engine already used as OpenGrep's stand-in in the sibling JavaScript-Tools-Clean corpus, and the exact alternative the platform's own build-contract doc proposes for this block -- was run for real against VaultGate and found zero findings.

## Command

```bash
semgrep --config p/java --config p/security-audit src/
```

## Notes

FindSecBugs adds detectors to SpotBugs; it cannot run, or disagree with SpotBugs, on its own. With SpotBugs unreachable here, FindSecBugs is unreachable too. semgrep was already installed in this sandbox (from the sibling corpus's OpenGrep stand-in work) and was run for real rather than asserted.

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
| `java24` | JDK 25 host, `--release 24` | CLEAN |
| `java25` | JDK 25 (native) | CLEAN |

Families `java10` to `java15` and `java17` to `java23` were added after the original measurement, so that every tool has a
folder for every JDK family 8 to 25 (a branch's own family is always present, including the ones this tool cannot run).
Their source is the same file, byte for byte, as `java8` (identical git blob hashes -- nothing here is version-gated). Each
family was compiled with `javac --release N` on a JDK 25 host and its unit tests were run; they pass at every N from 8 to 25.
The tool itself was **not run** on these thirteen families, so their result column says so instead of guessing; the rows for `java8`, `java9`, `java16`, `java24` and `java25` are the measured ones.


