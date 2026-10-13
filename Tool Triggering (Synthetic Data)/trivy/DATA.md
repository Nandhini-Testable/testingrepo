# Tool Triggering (Synthetic Data) -- Trivy

## What is planted here

A cyclonedx sbom naming the five planted vulnerable pins.

## What a correct run should measure

advisories against all five purls

## Language version

This folder ships no C# file, because its .NET scanner reads manifests, lockfiles and SBOMs, never C# source.
Inventing source for it would be padding: the tool would never read it.

The version claim is carried by the SBOM instead: its metadata properties
record this branch's target framework and its C# language version, 9.0,
alongside the five planted pins.

## Why there is no project file in this folder

No `.csproj`, no `.sln`, no `packages.config`, no lockfile. The platform
treats a directory carrying a project manifest as a project of its own and
runs the whole tool roster against it. With 19 tool folders on 312 branches
that is a task explosion, and it is not hypothetical -- the TypeScript corpus
hit it, where 203 of 276 tasks in a run were fixture overhead.
