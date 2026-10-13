# Roslyn

Domain: eel trap placements

Keys on: C# source, parsed into syntax trees and compiled

Shape: real source, because this tool parses source into syntax trees.

Nothing to report on it: The compiler will parse this and report nothing: no unused variable, no
  unreachable code, no unassigned field, no hidden member.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
