"""The Python 3.14 runtime lock.

Fails on 3.13 AND on 3.15+, so a branch built or run against the wrong
interpreter says so immediately instead of producing plausible numbers.

This family's interpreter is a RELEASE CANDIDATE -- 3.14.0rc2 -- which is a
first for this corpus and is recorded rather than hidden. The version assertion
below therefore checks the feature version only, so the lock keeps holding when
3.14.0 final replaces rc2 underneath it.

Three things separate this family from 3.13:

  * PEP 750 template strings -- new SYNTAX, and the first since PEP 695 at
    3.12. Every parser-based tool in the roster handles it correctly; that
    negative result is recorded in TRAPS-PY314.md #2.
  * PEP 649/749 deferred annotation evaluation, with the new `annotationlib`
    module. Transparent through the public API, and lethal one level below it:
    see TRAPS-PY314.md #1.
  * `compression.*` and `concurrent.interpreters` join the standard library.
"""
import importlib
import sys

import pytest


def test_interpreter_is_python_314():
    # Feature version only: 3.14.0rc2 and 3.14.0 final must both satisfy this.
    assert sys.version_info[:2] == (3, 14)


# ---- floor: everything below fails on 3.13 ------------------------------------

def test_pep750_tstrings_compile():
    # PEP 750, new in 3.14. The 3.13 family asserts the OPPOSITE.
    namespace = {}
    exec('greeting = t"tier {0}"'.replace("{0}", "gold"), namespace)
    from string.templatelib import Template
    assert isinstance(namespace["greeting"], Template)


def test_a_tstring_is_not_a_str():
    # The whole point of PEP 750: deferred, inspectable interpolation.
    from string.templatelib import Template
    namespace = {"order_id": "A-1001"}
    exec('rendered = t"order {order_id}"', namespace)
    template = namespace["rendered"]
    assert isinstance(template, Template)
    assert not isinstance(template, str)
    parts = [p if isinstance(p, str) else p.value for p in template]
    assert "A-1001" in parts


def test_string_templatelib_exists():
    assert importlib.import_module("string.templatelib") is not None


def test_annotationlib_exists():
    # PEP 649/749, new in 3.14.
    annotationlib = importlib.import_module("annotationlib")
    assert {f.name for f in annotationlib.Format} >= {"VALUE", "FORWARDREF", "STRING"}


def test_deferred_annotations_are_transparent_through_the_public_api():
    # PEP 649 makes annotations lazy. Through __annotations__ that is
    # INVISIBLE when every name resolves -- which is why it broke nothing in
    # this roster's analysis and everything in one dependency's runtime.
    # See TRAPS-PY314.md #1.
    def subtotal(qty: int, unit: float) -> float:
        return qty * unit

    assert subtotal.__annotations__ == {"qty": int, "unit": float, "return": float}


def test_the_private_typing_api_that_broke_pydantic_changed_here():
    # pydantic 2.13.5 calls typing._eval_type(..., prefer_fwd_module=True).
    # 3.14 renamed that keyword to parent_fwdref. A PRIVATE function, with no
    # compatibility guarantee, three dependency levels below semgrep.
    import inspect
    import typing
    parameters = inspect.signature(typing._eval_type).parameters
    assert "parent_fwdref" in parameters
    assert "prefer_fwd_module" not in parameters


def test_compression_namespace_exists():
    # compression.zstd and friends, new in 3.14.
    assert importlib.import_module("compression.zstd") is not None


def test_concurrent_interpreters_exists():
    # PEP 734, new in 3.14.
    assert importlib.import_module("concurrent.interpreters") is not None


def test_typing_bytestring_is_gone():
    # typing.ByteString was REMOVED in 3.14.
    import typing
    assert not hasattr(typing, "ByteString")


def test_pep594_modules_are_still_gone():
    # Removed at 3.13; they must not come back.
    for name in ("telnetlib", "cgi", "crypt", "imghdr", "pipes", "xdrlib"):
        try:
            importlib.import_module(name)
        except ImportError:
            continue
        raise AssertionError("%s is importable -- this is not 3.13+" % name)


def test_the_roster_survives_this_interpreter():
    # semgrep is the SAST primary and dies here unless pydantic is held at
    # 2.12.3 -- see the comment block in requirements-dev.txt. Skipped rather
    # than failed when the dev requirements are absent: every other assertion
    # in this file is about the INTERPRETER and must pass on a bare pytest.
    pytest.importorskip("semgrep", reason="dev requirements not installed")
    pydantic = pytest.importorskip("pydantic", reason="dev requirements not installed")
    assert pydantic.VERSION.startswith("2.12."), (
        "pydantic %s is installed; 2.13+ breaks semgrep on this interpreter"
        % pydantic.VERSION)


# ---- ceiling: everything below fails on 3.15+ ---------------------------------

def test_locale_getdefaultlocale_is_still_present():
    # Deprecated, and scheduled for removal in 3.15.
    import locale
    assert hasattr(locale, "getdefaultlocale")


def test_purepath_is_reserved_is_still_present():
    # Deprecated in 3.13, scheduled for removal in 3.15.
    import pathlib
    assert hasattr(pathlib.PurePath, "is_reserved")


def test_asyncio_iscoroutinefunction_is_still_present():
    # Deprecated, scheduled for removal in 3.16.
    import asyncio
    assert hasattr(asyncio, "iscoroutinefunction")


def test_free_threading_is_not_the_default_build():
    # 3.14 ships an OPTIONAL free-threaded build (PEP 703). This corpus uses
    # the standard GIL build on every family, so a branch accidentally run on
    # a free-threaded interpreter is a different measurement and must say so.
    assert sys._is_gil_enabled(), (
        "this is a free-threaded build; the corpus is pinned to the GIL build")
