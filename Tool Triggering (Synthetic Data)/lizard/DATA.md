# Tool Triggering (Synthetic Data) -- Lizard

## What is planted here

One method with 20 decision points.

## What a correct run should measure

max_ccn >= 15 on ComplexityHotspot.Rate

## Language version

The C# core of every file here is C# 5-compatible, so it is identical on all
thirteen families and the measurement above cannot drift between them.

Each file then carries a `LanguageMarker` class pinning it to this family's
language version, C# 10.0, using: `record struct`, extended property pattern.

That marker is the version claim, and it is settled by the compiler rather
than asserted in prose:

```
csc -langversion:10.0    ->  clean
csc -langversion:9.0     ->  FAILS
```

## Why there is no project file in this folder

No `.csproj`, no `.sln`, no `packages.config`, no lockfile. The platform
treats a directory carrying a project manifest as a project of its own and
runs the whole tool roster against it. With 19 tool folders on 312 branches
that is a task explosion, and it is not hypothetical -- the TypeScript corpus
hit it, where 203 of 276 tasks in a run were fixture overhead.
