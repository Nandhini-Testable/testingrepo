# Tool Triggering (Synthetic Data) -- OpenTelemetry (.NET) + OTLP exporter

## What is planted here

Span-shaped work with a counter, so an exported zero is distinguishable from a no-op processor.

## What a correct run should measure

a non-zero span count; NOT a path-coverage number

## Language version

The C# core of every file here is C# 5-compatible, so it is identical on all
thirteen families and the measurement above cannot drift between them.

Each file then carries a `LanguageMarker` class pinning it to this family's
language version, C# 9.0, using: record type, target-typed `new()`, `is not` pattern.

That marker is the version claim, and it is settled by the compiler rather
than asserted in prose:

```
csc -langversion:9.0     ->  clean
csc -langversion:8.0     ->  FAILS
```

## Why there is no project file in this folder

No `.csproj`, no `.sln`, no `packages.config`, no lockfile. The platform
treats a directory carrying a project manifest as a project of its own and
runs the whole tool roster against it. With 19 tool folders on 312 branches
that is a task explosion, and it is not hypothetical -- the TypeScript corpus
hit it, where 203 of 276 tasks in a run were fixture overhead.
