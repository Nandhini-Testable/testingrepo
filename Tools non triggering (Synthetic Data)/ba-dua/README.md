# ba-dua

Domain: tin smelting charges

Keys on: compiled classes, instrumented and then exercised for def-use coverage

Shape: no source, because this tool measures def-use pairs exercised by a running suite. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: It measures which definition-use pairs a test suite actually exercises.
  Nothing is compiled and nothing runs, so no pair is ever marked covered.

Expected: NOT TRIGGERED, no input discovered.
