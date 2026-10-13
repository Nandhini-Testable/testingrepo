# Spoon

Domain: dye vat batches

Keys on: Java source, parsed into its own analysable model

Shape: real source, because this tool parses source into its own analysable model.

Nothing to report on it: Spoon is a model-building library rather than a checker, so it produces a
  tree and no verdict. The tree here is one class with one method.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
