# NuGet Audit

Domain: sail loft panel cuts

Keys on: a project or packages file, resolved into a package graph

Shape: no source, because this tool resolves a package graph from a project file. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: No .csproj, no packages.config, no Directory.Packages.props. The graph it
  would audit does not exist, so no advisory request is made.

Expected: NOT TRIGGERED, no input discovered.
