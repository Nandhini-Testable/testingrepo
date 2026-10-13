# SpotBugs

Domain: glass annealing ramps

Keys on: compiled .class files, scanned by its bug-pattern detectors

Shape: no source, because this tool scans .class files for bug patterns, never source. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: The one thing worth stating plainly for this roster: SpotBugs never reads
  source. With no .class file in this folder its detectors have no input
  at all, whatever the Java source beside them looks like.

Expected: NOT TRIGGERED, no input discovered.
