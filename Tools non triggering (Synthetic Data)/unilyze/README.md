# unilyze

Domain: thatch bundling rates

Keys on: a project's source together with its compiled assemblies' IL

Shape: real source, because this tool reads a project's source as well as its IL.

Nothing to report on it: It reads both, so the source here is a real input even though nothing is
  compiled. Every one of its default smell thresholds is cleared by a wide
  margin: one small class, one short method, complexity 1, no nesting, two
  parameters at most, no inheritance and no cycle.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
