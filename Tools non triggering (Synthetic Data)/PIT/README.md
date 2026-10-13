# PIT

Domain: flax retting ponds

Keys on: compiled classes plus a test suite to re-run against each mutant

Shape: no source, because this tool re-runs a test suite against each mutant. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: Mutation testing needs something to mutate and a suite that can tell the
  difference. There is neither bytecode nor a test here, so the baseline
  run never starts.

Expected: NOT TRIGGERED, no input discovered.
