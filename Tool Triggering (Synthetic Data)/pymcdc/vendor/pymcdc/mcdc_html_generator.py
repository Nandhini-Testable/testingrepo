from __future__ import annotations

from pathlib import Path
from html import escape
from typing import Any


def _is_true_text(value: Any) -> bool:
    if isinstance(value, bool):
        return value
    if value is None:
        return False
    text = str(value).strip().lower()
    return text in {"true", "yes", "y", "1", "covered", "coberto"}


def _is_false_text(value: Any) -> bool:
    if isinstance(value, bool):
        return not value
    if value is None:
        return False
    return str(value).strip().lower() == "false"


def _coverage_percent(covered: int, total: int) -> int:
    if total <= 0:
        return 0
    return round((covered / total) * 100)


def _cell_class(value: Any, is_cover_column: bool) -> str:
    if not is_cover_column:
        text = str(value).strip()
        if text == "----":
            return "na"
        if _is_true_text(value):
            return "true"
        if _is_false_text(value):
            return "false"
        return ""
    return "covered" if _is_true_text(value) else "not-covered"


def _pick_first(condition: dict[str, Any], *keys: str, default: Any = None) -> Any:
    for key in keys:
        if key in condition:
            return condition[key]
    return default


def _normalize_content(condition: dict[str, Any]) -> list[list[Any]]:
    content = _pick_first(condition, "content", "Content", default=[])
    if not isinstance(content, list):
        return []
    return content


def _condition_has_uncovered_rows(condition: dict[str, Any]) -> bool:
    content = _normalize_content(condition)
    if len(content) < 2:
        return False
    for row in content[1:]:
        if row and not _is_true_text(row[-1]):
            return True
    return False


def _condition_covered_stats(condition: dict[str, Any]) -> tuple[int, int]:
    content = _normalize_content(condition)
    if len(content) < 2:
        return 0, 0
    rows = content[1:]
    total = len(rows)
    covered = sum(1 for row in rows if row and _is_true_text(row[-1]))
    return covered, total


def _line_key(condition: dict[str, Any]) -> tuple[int, int]:
    line_number = _pick_first(condition, "Line number", "Line Number", default=(10**9, 10**9))
    if isinstance(line_number, (list, tuple)) and len(line_number) >= 2:
        try:
            return int(line_number[0]), int(line_number[1])
        except Exception:
            return 10**9, 10**9
    return 10**9, 10**9


def _render_nav(condition: dict[str, Any], index: int) -> str:
    line_number = _pick_first(condition, "Line number", "Line Number", default=("?", "?"))
    covered, total = _condition_covered_stats(condition)
    uncovered = _condition_has_uncovered_rows(condition)
    badge = "not-covered-badge" if uncovered else "covered-badge"
    line_label = f"L{line_number[0]}:{line_number[1]}" if isinstance(line_number, (list, tuple)) and len(line_number) >= 2 else "L?:?"
    return (
        f'<a class="nav-pill {badge}" href="#decision-{index}" '
        f'data-uncovered="{"true" if uncovered else "false"}">'
        f'<span class="nav-title">D{index}</span>'
        f'<span class="nav-meta">{escape(line_label)} · {covered}/{total}</span>'
        f'</a>'
    )


def _render_condition(condition: dict[str, Any], index: int) -> str:
    line_number = _pick_first(condition, "Line number", "Line Number", default=("?", "?"))
    decision = _pick_first(condition, "Decision", default="")
    content = _normalize_content(condition)
    uncovered = _condition_has_uncovered_rows(condition)
    covered_rows, total_rows = _condition_covered_stats(condition)
    section_class = "decision-card uncovered-decision" if uncovered else "decision-card covered-decision"
    section_attr = "true" if uncovered else "false"

    if not content:
        table_html = '<p class="empty">Sem combinações para exibir.</p>'
    else:
        headers = content[0]
        rows = content[1:]
        last_col = len(headers) - 1

        pairs = condition.get("pairs", [])
        if not isinstance(pairs, list):
            pairs = []

        header_cells = []
        for col_index, header in enumerate(headers):
            # Result. and Cover. are not MC/DC conditions and are not clickable.
            if 0 < col_index < last_col:
                pair_index = col_index - 1
                if pair_index < len(pairs):
                    pair = pairs[pair_index]
                    if isinstance(pair, (list, tuple)) and len(pair) >= 2:
                        row1, row2 = pair[0], pair[1]
                        header_cells.append(
                            f'<th class="condition-header" '
                            f'data-row1="{escape(str(row1))}" '
                            f'data-row2="{escape(str(row2))}" '
                            f'title="Click to show only the MC/DC pair; click again to show all rows">'
                            f'{escape(str(header))}</th>'
                        )
                        continue
            header_cells.append(f"<th>{escape(str(header))}</th>")

        header_html = "".join(header_cells)

        body_rows = []
        for row_index, row in enumerate(rows, start=1):
            row_uncovered = bool(row) and not _is_true_text(row[-1])
            tr_class = ' class="uncovered-row"' if row_uncovered else ""
            cells = [f'<td class="row-number">{row_index}</td>']
            for col_index, value in enumerate(row):
                css_class = _cell_class(value, is_cover_column=(col_index == last_col))
                class_attr = f' class="{css_class}"' if css_class else ""
                cells.append(f"<td{class_attr}>{escape(str(value))}</td>")
            body_rows.append(
                f'<tr data-row="{row_index}"{tr_class}>{"".join(cells)}</tr>'
            )

        table_html = f"""
        <div class="table-wrap">
          <table>
            <thead>
              <tr>
                <th>#</th>
                {header_html}
              </tr>
            </thead>
            <tbody>
              {''.join(body_rows)}
            </tbody>
          </table>
        </div>
        """

    chip_class = "status-chip status-uncovered" if uncovered else "status-chip status-covered"
    chip_text = "Has uncovered requirements" if uncovered else "Fully covered"

    return f"""
    <section id="decision-{index}" class="{section_class}" data-uncovered="{section_attr}">
      <div class="decision-header">
        <div>
          <h2>Decision {index}</h2>
          <div class="decision-stats">{covered_rows}/{total_rows} covered rows</div>
        </div>
        <div class="decision-side">
          <div class="{chip_class}">{chip_text}</div>
          <div class="line-number">Line number: {escape(str(line_number))}</div>
        </div>
      </div>
      <div class="decision-text"><strong>Decision:</strong> <code>{escape(str(decision))}</code></div>
      <div class="subtitle">Combinations to be covered</div>
      {table_html}
    </section>
    """



def generate_mcdc_html(report: dict[str, Any], output_filename: str = "index.html") -> Path:
    output_dir = Path(report["path"])
    output_dir.mkdir(parents=True, exist_ok=True)

    analyzed_name = report.get("name", "")
    conditions = report.get("conditions", [])
    if not isinstance(conditions, list):
        conditions = []
    conditions = sorted(conditions, key=_line_key)

    covered, total = report.get("totals", (0, 0))
    covered = int(covered)
    total = int(total)
    percent = _coverage_percent(covered, total)
    decisions_count = len(conditions)
    uncovered_decisions = sum(1 for condition in conditions if _condition_has_uncovered_rows(condition))

    nav_html = "\n".join(_render_nav(condition, index) for index, condition in enumerate(conditions, start=1))
    conditions_html = "\n".join(_render_condition(condition, index) for index, condition in enumerate(conditions, start=1))

    html = f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>MC/DC Report - {escape(str(analyzed_name))}</title>
  <style>
    :root {{
      --bg: #0f172a;
      --panel: #111827;
      --panel-2: #1f2937;
      --text: #e5e7eb;
      --muted: #9ca3af;
      --border: #374151;
      --accent: #60a5fa;
      --ok-bg: #052e16;
      --ok-fg: #86efac;
      --bad-bg: #3f0d12;
      --bad-fg: #fca5a5;
      --true-bg: #0b3b2e;
      --false-bg: #3b1020;
      --na-bg: #3a3a3a;
      --shadow: 0 10px 30px rgba(0, 0, 0, 0.25);
    }}

    * {{ box-sizing: border-box; }}
    html {{ scroll-behavior: smooth; }}

    body {{
      margin: 0;
      font-family: Arial, Helvetica, sans-serif;
      background: linear-gradient(180deg, #0b1020 0%, var(--bg) 100%);
      color: var(--text);
      padding: 32px;
    }}

    .container {{
      max-width: 1280px;
      margin: 0 auto;
    }}

    .hero {{
      background: rgba(17, 24, 39, 0.92);
      border: 1px solid var(--border);
      border-radius: 18px;
      padding: 24px;
      box-shadow: var(--shadow);
      margin-bottom: 24px;
    }}

    h1 {{
      margin: 0 0 10px;
      font-size: 2rem;
      color: white;
    }}

    .filename {{
      color: var(--accent);
      font-weight: bold;
      word-break: break-word;
    }}

    .summary {{
      display: flex;
      flex-wrap: wrap;
      gap: 12px;
      margin-top: 18px;
    }}

    .summary-card {{
      background: var(--panel-2);
      border: 1px solid var(--border);
      border-radius: 14px;
      padding: 16px 18px;
      min-width: 180px;
    }}

    .summary-label {{
      font-size: 0.85rem;
      color: var(--muted);
      margin-bottom: 6px;
    }}

    .summary-value {{
      font-size: 1.35rem;
      font-weight: bold;
    }}

    .toolbar {{
      background: rgba(17, 24, 39, 0.95);
      border: 1px solid var(--border);
      border-radius: 18px;
      padding: 18px 20px;
      box-shadow: var(--shadow);
      margin-bottom: 24px;
      position: sticky;
      top: 12px;
      z-index: 10;
      backdrop-filter: blur(8px);
    }}

    .toolbar-top {{
      display: flex;
      gap: 14px;
      align-items: center;
      justify-content: space-between;
      flex-wrap: wrap;
      margin-bottom: 14px;
    }}

    .toolbar h2 {{
      margin: 0;
      font-size: 1.1rem;
    }}

    .toolbar-info {{
      color: var(--muted);
      font-size: 0.95rem;
    }}

    .filter-box {{
      display: inline-flex;
      align-items: center;
      gap: 10px;
      background: #0b1220;
      border: 1px solid var(--border);
      border-radius: 999px;
      padding: 10px 14px;
      cursor: pointer;
      user-select: none;
    }}

    .filter-box input {{
      accent-color: var(--accent);
      width: 16px;
      height: 16px;
    }}

    .nav-grid {{
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
    }}

    .nav-pill {{
      display: inline-flex;
      flex-direction: column;
      gap: 2px;
      min-width: 112px;
      text-decoration: none;
      color: var(--text);
      background: #0b1220;
      border: 1px solid var(--border);
      border-radius: 12px;
      padding: 9px 11px;
      transition: transform 0.15s ease, border-color 0.15s ease, box-shadow 0.15s ease;
    }}

    .nav-pill:hover {{
      transform: translateY(-1px);
      border-color: var(--accent);
      box-shadow: 0 6px 16px rgba(0, 0, 0, 0.22);
    }}

    .nav-title {{
      font-weight: bold;
      color: white;
    }}

    .nav-meta {{
      font-size: 0.82rem;
      color: var(--muted);
    }}

    .covered-badge {{
      border-color: #21553a;
    }}

    .not-covered-badge {{
      border-color: #6b2330;
    }}

    .decision-card {{
      background: rgba(17, 24, 39, 0.95);
      border: 1px solid var(--border);
      border-radius: 18px;
      padding: 22px;
      box-shadow: var(--shadow);
      margin-bottom: 22px;
      scroll-margin-top: 110px;
    }}

    .covered-decision {{
      border-left: 5px solid #1f7a46;
    }}

    .uncovered-decision {{
      border-left: 5px solid #9f1239;
    }}

    .decision-header {{
      display: flex;
      justify-content: space-between;
      gap: 16px;
      align-items: flex-start;
      flex-wrap: wrap;
      margin-bottom: 10px;
    }}

    .decision-header h2 {{
      margin: 0;
      font-size: 1.25rem;
      color: white;
    }}

    .decision-side {{
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      gap: 8px;
    }}

    .decision-stats, .line-number {{
      color: var(--muted);
      font-size: 0.95rem;
    }}

    .status-chip {{
      border-radius: 999px;
      padding: 6px 10px;
      font-size: 0.82rem;
      font-weight: bold;
      border: 1px solid var(--border);
    }}

    .status-covered {{
      background: var(--ok-bg);
      color: var(--ok-fg);
    }}

    .status-uncovered {{
      background: var(--bad-bg);
      color: var(--bad-fg);
    }}

    .decision-text {{
      margin-bottom: 14px;
      line-height: 1.5;
    }}

    code {{
      background: #0b1220;
      border: 1px solid var(--border);
      border-radius: 8px;
      padding: 2px 6px;
      color: #dbeafe;
      word-break: break-word;
    }}

    .subtitle {{
      font-weight: bold;
      margin-bottom: 10px;
      color: #d1d5db;
    }}

    .table-wrap {{
      overflow-x: auto;
    }}

    table {{
      width: 100%;
      border-collapse: collapse;
      min-width: 640px;
      background: #0b1220;
      border-radius: 12px;
      overflow: hidden;
    }}

    th, td {{
      border: 1px solid var(--border);
      padding: 10px 12px;
      text-align: center;
      vertical-align: middle;
    }}

    th {{
      background: #172033;
      color: white;
      position: sticky;
      top: 0;
    }}

    .condition-header {{
      cursor: pointer;
      user-select: none;
      transition: background 0.15s ease, outline-color 0.15s ease;
    }}

    .condition-header:hover {{
      outline: 2px solid var(--accent);
      outline-offset: -2px;
    }}

    .condition-header.active-condition {{
      background: #254872;
      outline: 2px solid var(--accent);
      outline-offset: -2px;
    }}

    td.row-number {{
      color: var(--muted);
      font-weight: bold;
      background: #101826;
      width: 52px;
    }}

    tr.uncovered-row td {{
      box-shadow: inset 0 0 0 9999px rgba(127, 29, 29, 0.04);
    }}

    td.true {{ background: var(--true-bg); }}
    td.false {{ background: var(--false-bg); }}

    td.na {{
      background: var(--na-bg);
      color: #d1d5db;
      font-style: italic;
    }}

    td.covered {{
      background: var(--ok-bg);
      color: var(--ok-fg);
      font-weight: bold;
    }}

    td.not-covered {{
      background: var(--bad-bg);
      color: var(--bad-fg);
      font-weight: bold;
    }}

    .footer-summary {{
      margin-top: 28px;
      color: var(--muted);
      font-size: 0.95rem;
      text-align: center;
    }}

    .empty {{
      color: var(--muted);
      font-style: italic;
    }}

    .hidden-by-filter {{
      display: none !important;
    }}

    .hidden-by-condition {{
      display: none !important;
    }}

    @media (max-width: 700px) {{
      body {{ padding: 16px; }}
      h1 {{ font-size: 1.5rem; }}
      .toolbar {{ position: static; }}
      .decision-side {{ align-items: flex-start; }}
    }}
  </style>
</head>
<body>
  <div class="container">
    <section class="hero">
      <h1>MC/DC Report</h1>
      <div>Analyzed file: <span class="filename">{escape(str(analyzed_name))}</span></div>
      <div class="summary">
        <div class="summary-card">
          <div class="summary-label">Covered requirements</div>
          <div class="summary-value">{covered}</div>
        </div>
        <div class="summary-card">
          <div class="summary-label">Total requirements</div>
          <div class="summary-value">{total}</div>
        </div>
        <div class="summary-card">
          <div class="summary-label">Decisions</div>
          <div class="summary-value">{decisions_count}</div>
        </div>
        <div class="summary-card">
          <div class="summary-label">Coverage</div>
          <div class="summary-value">{percent}%</div>
        </div>
      </div>
    </section>

    <section class="toolbar">
      <div class="toolbar-top">
        <div>
          <h2>Navigation</h2>
          <div class="toolbar-info"><span id="visible-decisions">{decisions_count}</span> of {decisions_count} decisions visible · {uncovered_decisions} with uncovered requirements</div>
        </div>
        <label class="filter-box">
          <input type="checkbox" id="only-uncovered">
          <span>Show only decisions with uncovered requirements</span>
        </label>
      </div>
      <div class="nav-grid" id="nav-grid">
        {nav_html}
      </div>
    </section>

    <div id="decisions-container">
      {conditions_html}
    </div>

    <div class="footer-summary">
      Covered {covered} out of {total} requirements in {decisions_count} decisions ({percent}%).
    </div>
  </div>

  <script>
    (function () {{
      const checkbox = document.getElementById('only-uncovered');
      const decisionCards = Array.from(document.querySelectorAll('.decision-card'));
      const navPills = Array.from(document.querySelectorAll('.nav-pill'));
      const visibleCounter = document.getElementById('visible-decisions');

      function applyFilter() {{
        const onlyUncovered = checkbox.checked;
        let visibleCount = 0;

        decisionCards.forEach((card) => {{
          const uncovered = card.dataset.uncovered === 'true';
          const hide = onlyUncovered && !uncovered;
          card.classList.toggle('hidden-by-filter', hide);
          if (!hide) visibleCount += 1;
        }});

        navPills.forEach((pill) => {{
          const uncovered = pill.dataset.uncovered === 'true';
          const hide = onlyUncovered && !uncovered;
          pill.classList.toggle('hidden-by-filter', hide);
        }});

        visibleCounter.textContent = String(visibleCount);
      }}

      const conditionHeaders = Array.from(document.querySelectorAll('.condition-header'));

      conditionHeaders.forEach((header) => {{
        header.addEventListener('click', () => {{
          const table = header.closest('table');
          const rows = Array.from(table.querySelectorAll('tbody tr'));
          const alreadyActive = header.classList.contains('active-condition');

          table.querySelectorAll('.condition-header').forEach((item) => {{
            item.classList.remove('active-condition');
          }});

          if (alreadyActive) {{
            rows.forEach((row) => {{
              row.classList.remove('hidden-by-condition');
            }});
            return;
          }}

          const row1 = header.dataset.row1;
          const row2 = header.dataset.row2;

          rows.forEach((row) => {{
            const visible = row.dataset.row === row1 || row.dataset.row === row2;
            row.classList.toggle('hidden-by-condition', !visible);
          }});

          header.classList.add('active-condition');
        }});
      }});

      checkbox.addEventListener('change', applyFilter);
      applyFilter();
    }})();
  </script>
</body>
</html>
"""

    output_path = output_dir / output_filename
    output_path.write_text(html, encoding="utf-8")
    return output_path


if __name__ == "__main__":
    sample_report = {
        "path": "./mcdc_html_output",
        "name": "invoice.py",
        "conditions": [
            {
                "Line number": (232, 7),
                "Decision": 'self.customer_type == "premium" and days_late > 5',
                "content": [
                    ['Result.', 'self.customer_type == "premium"', 'days_late > 5', 'Cover.'],
                    ['False', 'True', 'False', 'False'],
                    ['False', 'False', '----', 'False'],
                    ['True', 'True', 'True', 'False'],
                ],
                "pairs": [(2, 3), (1, 3)],
            },
            {
                "Line Number": (202, 5),
                "Decision": 'not isinstance(on_date, date)',
                "Content": [
                    ['Result.', 'isinstance(on_date, date)', 'Cover.'],
                    ['True', 'False', 'True'],
                    ['False', 'True', 'False'],
                ],
                "pairs": [(1, 2)],
            },
        ],
        "totals": (1, 5),
    }

    generated = generate_mcdc_html(sample_report)
    print(f"HTML gerado em: {generated}")
