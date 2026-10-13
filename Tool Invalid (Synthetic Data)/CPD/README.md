# CPD

Inverse-corpus counterpart to the sibling Java-Tools-Clean's **CPD**
folder. Engineered so the real stand-in tool finds something genuinely
wrong, confirmed by actually running it -- not asserted.

Domain: depot-parcel tallying (ParcelTally)

**Not installed here** (CPD itself): see Notes for why, and what was
checked instead -- the same infrastructure gap as Clean's own CPD
folder.

## What a failing result looks like

CPD (PMD's copy-paste detector) is not reachable here (see notes), so
`jscpd` -- the same real, npm-installed stand-in already used for this
purpose in the sibling Clean corpus -- was run for real against
ParcelTally's Java source and found **1 real clone**: `summarizeEastWing()`
and `summarizeWestWing()` are copy-pasted, 10 duplicated lines (17.54%
of the file) and 59 duplicated tokens (8.78%).

## Command

```bash
npx jscpd . --min-lines 5 --min-tokens 30 --threshold 0
```

## Notes

CPD has no standalone distribution outside PMD's own release zip (a
GitHub Release asset); PMD/CPD have no Debian package either. Both are
unreachable at this sandbox's egress proxy, so CPD itself is NOT
INSTALLED -- identical to the sibling Clean corpus's own finding.
jscpd -- real, npm-installed, already vetted in Clean -- was run for
real in its place, against a fixture deliberately written with a
copy-pasted pair of methods.

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


Genuine finding(s) on this tool: 1 real clone (10 lines / 59 tokens),
identical across all five families since jscpd reads only source text.
