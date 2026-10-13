# Grype

Domain: net mending gauges

Keys on: an SBOM, a lockfile or a built artifact, resolved into package coordinates

Shape: no source, because this tool resolves coordinates from an SBOM, build file or artifact. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: No pom.xml, no build.gradle, no jar. The coordinate set it would match
  against advisories is empty.

Expected: NOT TRIGGERED, no input discovered.
