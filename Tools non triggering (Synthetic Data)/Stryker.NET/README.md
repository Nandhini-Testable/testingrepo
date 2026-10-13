# Stryker.NET

Domain: wool fulling cycles

Keys on: a built project and a test suite to re-run against each mutant

Shape: no source, because this tool builds the project and re-runs tests per mutant. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: Mutation testing needs something to build and a suite that can tell a
  mutant from the original. There is neither here, so the initial run that
  must precede mutation never starts.

Expected: NOT TRIGGERED, no input discovered.
