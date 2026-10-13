# MiniCover

Domain: rope walk strand counts

Keys on: compiled assemblies plus a test run

Shape: no source, because this tool instruments IL, then reports what a test run hit. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: It instruments IL and then reports which lines a test run hit. There is no
  assembly and no test here, so nothing is instrumented and nothing is
  hit.

Expected: NOT TRIGGERED, no input discovered.
