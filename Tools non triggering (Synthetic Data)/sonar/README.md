# sonar

Domain: cider press pressings

Keys on: a Sonar project configuration and an analysable source set, submitted to a
  server

Shape: real source, because this tool analyses a source set, when it has a project and a server.

Nothing to report on it: No sonar-project.properties means no project key, and no server is
  reachable to receive an analysis even if one existed.

Contents: one minimal program per boundary family (node12, node14, node20, node24, node26). One class or one function, no branching, no duplication, no dependency, no dead export, no magic number. No manifest and no tool configuration, so nothing here is discovered as a project.

Expected: the tool runs and reports nothing.
