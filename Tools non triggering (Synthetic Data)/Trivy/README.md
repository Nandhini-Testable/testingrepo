# Trivy

Domain: dye vat batches

Keys on: a lockfile, a project file, a filesystem or an image, resolved into
  coordinates

Shape: no source, because this tool resolves coordinates from a manifest or artifact. A minimal program would not change that, and shipping one would imply a verdict this folder cannot support.

Inert here because: Its filesystem scan finds no manifest to anchor on and no assembly to
  fingerprint, so the coordinate set is empty.

Expected: NOT TRIGGERED, no input discovered.
