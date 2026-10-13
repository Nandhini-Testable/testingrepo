# CPD

Domain: kiln drying schedules

Keys on: Java source, tokenised and compared for duplicate blocks

Shape: real source, because this tool tokenises source and compares token runs.

Nothing to report on it: Duplication needs a repeated token run above the minimum. Each family here
  is structurally distinct and each tool folder uses its own domain, so
  nothing reaches the threshold.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
