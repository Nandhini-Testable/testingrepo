# PMD

Domain: wool fulling cycles

Keys on: Java source, parsed and checked against a rule set

Shape: real source, because this tool checks parsed source against a rule set.

Nothing to report on it: Written to clear its default quickstart rules: no unused variable, no
  empty catch, no missing braces, no long method, nothing shadowed.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
