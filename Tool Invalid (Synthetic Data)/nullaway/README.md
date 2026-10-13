# nullaway

Inverse-corpus counterpart to the sibling Java-Tools-Clean's **nullaway** folder. Engineered so the tool should find something genuinely wrong.

Domain: toll-booth axle counts and pricing (`TollBooth`).

**Not installed here**: NullAway is not a standalone program. It is an Error Prone compiler plugin, resolved from Maven Central, which this sandbox's egress proxy refuses (HTTP 403 on the CONNECT, measured this session). Nothing below was produced by running NullAway or Error Prone; see Notes.

## What a failing result looks like

NullAway (annotated package `tollbooth`) should report five defects against TollBooth. They are planted by construction -- read from the source, **not measured**:

1. `toll` dereferences the result of the `@Nullable` method `axlesFor` without a check.
2. `lastPlateLength` dereferences the `@Nullable` field `lastPlate`.
3. `receipt` returns `null` from a method that is not `@Nullable`.
4. `operator` is a non-null field that no constructor or initializer ever assigns, so NullAway reports the field as not initialised (`operatorBadge` then uses it).
5. `auditStamp` passes the literal `null` to `stamp`, whose parameter is not `@Nullable`.

The unit tests only walk the happy paths, so they pass: the defects are invisible to the tests, which is exactly what a null-safety checker is for.

## Command

```bash
javac -XDcompilePolicy=simple -processorpath <error_prone_core + nullaway jars> \
    "-Xplugin:ErrorProne -XepDisableAllChecks -Xep:NullAway:ERROR -XepOpt:NullAway:AnnotatedPackages=tollbooth" \
    -d build/main java8/src/main/java/tollbooth/*.java
```

Expected: five errors, non-zero exit code (not run -- see Notes)

## Layout

```text
nullaway/
  README.md
  java8/ ... java25/        one folder per JDK family (18), identical source
    src/main/java/tollbooth/TollBooth.java
    src/main/java/tollbooth/Nullable.java   local @Nullable annotation
    src/test/java/tollbooth/TollBoothTest.java
```

## Notes

`Nullable.java` is a local annotation with the simple name `Nullable`; NullAway matches nullness annotations by simple name, so the fixture needs no third-party annotation jar to compile.

What **was** run, in every family: the source compiles with plain `javac --release N` on a JDK 25 host and the JUnit 4 tests pass, for N = 8 to 25 (18 of 18 families compile and pass).

## Per-family results

| Family | Host JDK | Result |
|---|---|---|
| `java8` | JDK 25 host, `--release 8` | NOT INSTALLED |
| `java9` | JDK 25 host, `--release 9` | NOT INSTALLED |
| `java10` | JDK 25 host, `--release 10` | NOT INSTALLED |
| `java11` | JDK 25 host, `--release 11` | NOT INSTALLED |
| `java12` | JDK 25 host, `--release 12` | NOT INSTALLED |
| `java13` | JDK 25 host, `--release 13` | NOT INSTALLED |
| `java14` | JDK 25 host, `--release 14` | NOT INSTALLED |
| `java15` | JDK 25 host, `--release 15` | NOT INSTALLED |
| `java16` | JDK 25 host, `--release 16` | NOT INSTALLED |
| `java17` | JDK 25 host, `--release 17` | NOT INSTALLED |
| `java18` | JDK 25 host, `--release 18` | NOT INSTALLED |
| `java19` | JDK 25 host, `--release 19` | NOT INSTALLED |
| `java20` | JDK 25 host, `--release 20` | NOT INSTALLED |
| `java21` | JDK 25 host, `--release 21` | NOT INSTALLED |
| `java22` | JDK 25 host, `--release 22` | NOT INSTALLED |
| `java23` | JDK 25 host, `--release 23` | NOT INSTALLED |
| `java24` | JDK 25 host, `--release 24` | NOT INSTALLED |
| `java25` | JDK 25 (native) | NOT INSTALLED |

Every family has the same blocker (the Error Prone plugin cannot be fetched), so the column is the same for all 18. Each family's source was compiled with `javac --release N` on a JDK 25 host and its tests pass.
