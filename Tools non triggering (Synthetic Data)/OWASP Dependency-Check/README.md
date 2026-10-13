# OWASP Dependency-Check

Domain: charcoal burning stacks

Keys on: declared or bundled dependencies, matched against the NVD

Shape: no source, because this tool fingerprints a declared or bundled dependency set. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: It needs a dependency set to fingerprint. Nothing here declares or bundles
  one, so no CPE is ever resolved.

Expected: NOT TRIGGERED, no input discovered.
