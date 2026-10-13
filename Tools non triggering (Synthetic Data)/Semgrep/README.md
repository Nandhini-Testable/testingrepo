# Semgrep

Domain: flax retting ponds

Keys on: files matching a rule's language, discovered by extension

Shape: real source, because this tool matches rule patterns against parsed source.

Nothing to report on it: Same shape as Opengrep, which it is the upstream of. Measured on this
  folder with a real ruleset: zero findings, zero parse errors.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
