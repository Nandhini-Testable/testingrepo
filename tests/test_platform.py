"""The planted dependency pins must be genuinely importable.

A pin that nothing imports produces a manifest-versus-source disagreement that
looks like a tool defect and is not. This test is the guard against the
TypeScript corpus's structurally-zero-span trap reappearing here.

It also guards the reverse: paramiko 2.4.1 imports `six` WITHOUT declaring it.
On the Python 3.6 family that never showed, because cryptography 2.3 declared
six and supplied it by accident. cryptography 42.0.0 dropped six, so from 3.7
onward a runtime-only install raises ModuleNotFoundError. `six` is therefore pinned
explicitly in the support section of requirements-runtime.txt, and imported
here so the guard cannot rot.
"""
import pytest

# Do not use pytest.importorskip at import time. pymcdc exec()s this
# file outside pytest; a Skipped exception becomes NO_INDEPENDENCE.
# Coverage-py installs planted pins into .wb_py_deps; pymcdc does not.
_PLANTED = ("requests", "jinja2", "urllib3", "cryptography", "paramiko", "six")
_MISSING = []
for _name in _PLANTED:
    try:
        __import__(_name)
    except ImportError:
        _MISSING.append(_name)

if not _MISSING:
    from orderlab.platform.integrations import (           # noqa: E402
        dependency_versions,
        render_invoice,
    )
else:
    dependency_versions = None
    render_invoice = None

# Generated from the branch's own planted set, never hand-written. The 3.10
# family had to move two of these pins -- urllib3 for a tool's transitive floor
# and paramiko for a removed stdlib alias -- and a hand-maintained copy here
# would have silently disagreed with requirements-runtime.txt.
EXPECTED = {
    "requests": "2.31.0",
    "jinja2": "3.1.3",
    "urllib3": "2.2.2",
    "cryptography": "42.0.0",
    "paramiko": "2.10.1",
}


def test_every_planted_pin_is_imported_and_reports_its_version():
    if _MISSING:
        pytest.skip("planted deps not in this process: {0}".format(", ".join(_MISSING)))
    resolved = dependency_versions()
    assert sorted(resolved) == sorted(EXPECTED)


def test_resolved_versions_match_the_manifest_pins():
    if _MISSING:
        pytest.skip("planted deps not in this process: {0}".format(", ".join(_MISSING)))
    resolved = dependency_versions()
    mismatched = {name: (resolved[name], want)
                  for name, want in EXPECTED.items()
                  if resolved[name] != want}
    assert not mismatched, "installed versions disagree with the pins: {0}".format(
        mismatched)


def test_invoice_template_renders():
    if _MISSING:
        pytest.skip("planted deps not in this process: {0}".format(", ".join(_MISSING)))
    text = render_invoice("A-1", "US", 123.45, 2)
    assert "A-1" in text and "123.45" in text
