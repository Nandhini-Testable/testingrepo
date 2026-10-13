# Roslyn SAST

Domain: charcoal burning stacks

Keys on: C# syntax trees and their semantic model, checked by security analyzers

Shape: real source, because this tool runs security analyzers over syntax trees.

Nothing to report on it: Its analyzers look for a tainted value reaching a sink - a command, a
  query, a path, a deserializer. This code performs one arithmetic
  formatting step and calls nothing.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
