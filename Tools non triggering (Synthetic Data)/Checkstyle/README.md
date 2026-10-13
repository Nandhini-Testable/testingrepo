# Checkstyle

Domain: rope walk strand counts

Keys on: Java source, checked against a configured rule set

Shape: real source, because this tool checks parsed source against a rule set.

Nothing to report on it: Checkstyle cannot start without a configuration file and there is none
  here. Given one, the code is written to clear the common Sun and Google
  rule sets: Javadoc on every type and method, one top-level class per
  file, no magic number, no long line.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
