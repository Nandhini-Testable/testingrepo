"""Exercise the Beniget / coverage.py + beniget fixtures.

py-all-defs-uses (Beniget) intersects DefUseChains with coverage.json
executed lines. These tests run the planted call-graph module so the
coverage-py exam actually hits those defs.
"""
from orderlab.analysis.call_graph_sample import entry_point, fan_out, ping, pong


def test_entry_point_chain():
    assert entry_point(1) == 3
    assert entry_point(2) == 6


def test_ping_pong_cycle():
    assert ping(0) == "ping"
    assert pong(0) == "pong"
    assert ping(3) == "pong"


def test_fan_out_coupling():
    values, ping_result, stage = fan_out([1, 2])
    assert values == [3, 6]
    assert ping_result == "ping"
    assert stage == 2
