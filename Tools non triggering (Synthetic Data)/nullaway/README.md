# nullaway

Domain: osier weaving frames

Keys on: a javac compilation it is attached to as an Error Prone plugin

Shape: real source, because this tool reads source during a javac compilation it attaches to.

Nothing to report on it: It is a compiler plugin, not a standalone tool: it runs during a build
  that does not happen here. Its subject is nullable dereferences, and
  nothing in this code is ever null.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
