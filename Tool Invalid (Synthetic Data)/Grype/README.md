# Grype

Inverse-corpus counterpart to the sibling Java-Tools-Clean's **Grype**
folder. Stays **NOT_INSTALLED** here too -- the blocker is
infrastructure (GitHub Releases + the Go module proxy both blocked),
not anything about this corpus being Clean or Invalid, so there is
nothing to invert.

Package: github.com/anchore/grype (Go binary / GitHub release)

Domain: container manifest package auditing (ManifestAudit)

**Not installed here**: see Notes for why, and what was checked instead.

## What a result would look like

A Grype scan of ManifestAudit's declared package versions would report
known-vulnerable dependencies. Not measurable here: see Notes.

## Command

```bash
grype dir:.
```

## Notes

Grype ships as a GitHub Release binary; `go install` is blocked the
same way (`proxy.golang.org` not in the allowlist), and no package for
it exists in Ubuntu's own apt archive. Identical to the sibling Clean
corpus's own finding.

## Per-family results

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

