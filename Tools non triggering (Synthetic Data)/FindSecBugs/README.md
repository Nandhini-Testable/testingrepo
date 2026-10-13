# FindSecBugs

Domain: sail loft panel cuts

Keys on: compiled .class files, scanned by its SpotBugs security detectors

Shape: no source, because this tool runs SpotBugs security detectors over .class files. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: Like SpotBugs it analyses bytecode. With nothing compiled here, its
  detectors never run - and the source would give them nothing anyway,
  since it performs no IO, no deserialization and no cryptography.

Expected: NOT TRIGGERED, no input discovered.
