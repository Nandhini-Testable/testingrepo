# Tool Triggering (Synthetic Data) -- git log --numstat

## What is planted here

Nothing in source -- its input is this branch's commit graph.

## What a correct run should measure

contributors == 4 and a non-empty per-file churn table

## Language version

This folder ships no C# file, because its input is the repository's commit graph, not its source.
Inventing source for it would be padding: the tool would never read it.

`expected.json` records what should be read from the history instead, so
a zero is distinguishable from a scan that never ran.

## Why there is no project file in this folder

No `.csproj`, no `.sln`, no `packages.config`, no lockfile. The platform
treats a directory carrying a project manifest as a project of its own and
runs the whole tool roster against it. With 19 tool folders on 312 branches
that is a task explosion, and it is not hypothetical -- the TypeScript corpus
hit it, where 203 of 276 tasks in a run were fixture overhead.
