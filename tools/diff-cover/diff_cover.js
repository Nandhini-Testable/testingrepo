#!/usr/bin/env node
'use strict';
/**
 * diff-cover for JavaScript: coverage of the lines this branch changed.
 *
 *   node tools/diff-cover/diff_cover.js <cobertura.xml> [options]
 *
 *   --compare-branch <ref>   branch or commit to diff against   (default: origin/main)
 *   --json-report <file>     also write the report as JSON
 *   --format json:<file>     same as --json-report
 *   --fail-under <percent>   exit 1 when diff coverage is below this (default: 0)
 *
 * Replaces the Python diff-cover CLI and needs only Node and git. Like the CLI it
 * reads the changed lines from `git diff <ref>...HEAD` plus the staged and the
 * unstaged changes, matches them against the cobertura report (nyc writes one to
 * coverage/cobertura-coverage.xml), and prints the same text and JSON reports.
 *
 * Exit codes: 0 report written, 1 failed (bad input, git error, or below --fail-under).
 */
const fs = require('fs');
const path = require('path');
const { spawnSync } = require('child_process');

const ROOT = path.dirname(path.dirname(__dirname));
const MAX_BYTES = 256 * 1024 * 1024;

function fail(message) {
  console.error('diff-cover: ' + message);
  process.exit(1);
}

function parseArgs(argv) {
  const opts = { coverage: [], compareBranch: 'origin/main', jsonReport: null, failUnder: 0 };
  for (let i = 0; i < argv.length; i += 1) {
    const arg = argv[i];
    const eq = arg.indexOf('=');
    const name = arg.startsWith('--') && eq > 0 ? arg.slice(0, eq) : arg;
    const inline = arg.startsWith('--') && eq > 0 ? arg.slice(eq + 1) : null;
    const value = () => (inline !== null ? inline : argv[++i]);
    if (name === '--compare-branch') opts.compareBranch = value();
    else if (name === '--json-report') opts.jsonReport = value();
    else if (name === '--fail-under') opts.failUnder = Number(value());
    else if (name === '--format') {
      const v = value() || '';
      if (!v.startsWith('json:')) fail(`unsupported --format ${v} (only json:<file>)`);
      opts.jsonReport = v.slice(5);
    } else if (arg.startsWith('--')) fail(`unknown option ${arg}`);
    else opts.coverage.push(arg);
  }
  if (!opts.coverage.length) fail('no coverage report given (expected a cobertura XML file)');
  if (Number.isNaN(opts.failUnder)) fail('--fail-under needs a number');
  return opts;
}

function git(args) {
  const r = spawnSync('git', ['-C', ROOT, '-c', 'core.quotepath=false', ...args], { encoding: 'utf8', maxBuffer: MAX_BYTES });
  if (r.error) fail(r.error.code === 'ENOENT' ? 'git is not on PATH' : r.error.message);
  if (r.status !== 0) fail(`git ${args.join(' ')} failed: ${(r.stderr || '').trim()}`);
  return r.stdout;
}

// Added or changed line numbers per file, from a -U0 unified diff.
function addChangedLines(diffText, changed) {
  let file = null;
  for (const line of diffText.split('\n')) {
    if (line.startsWith('+++ ')) {
      const p = line.slice(4).replace(/\t.*$/, '');
      file = p === '/dev/null' ? null : p.replace(/^b\//, '');
      continue;
    }
    const m = /^@@ -\d+(?:,\d+)? \+(\d+)(?:,(\d+))? @@/.exec(line);
    if (!m || !file) continue;
    const start = Number(m[1]);
    const count = m[2] === undefined ? 1 : Number(m[2]);
    if (!changed.has(file)) changed.set(file, new Set());
    for (let n = 0; n < count; n += 1) changed.get(file).add(start + n);
  }
}

function changedLines(compareBranch) {
  const base = ['diff', '-U0', '--no-color', '--no-ext-diff'];
  const changed = new Map();
  addChangedLines(git([...base, `${compareBranch}...HEAD`]), changed);
  addChangedLines(git([...base, '--cached']), changed);
  addChangedLines(git(base), changed);
  return changed;
}

const unescapeXml = (s) => s.replace(/&quot;/g, '"').replace(/&apos;/g, "'").replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, '&');
const attr = (tag, name) => { const m = new RegExp(`\\b${name}="([^"]*)"`).exec(tag); return m ? unescapeXml(m[1]) : null; };
const normalise = (p) => p.split('\\').join('/').replace(/^\.\//, '');

// file -> Map(line number -> hits); a line is covered when any report hit it.
function readCoverage(files) {
  const coverage = new Map();
  for (const file of files) {
    let xml;
    try {
      xml = fs.readFileSync(path.resolve(ROOT, file), 'utf8');
    } catch (err) {
      fail(`cannot read coverage report ${file}: ${err.message}`);
    }
    const classes = xml.match(/<class\b[\s\S]*?<\/class>/g) || [];
    if (!classes.length) fail(`${file} has no <class> elements; is it a cobertura report?`);
    for (const cls of classes) {
      const name = attr(cls.slice(0, cls.indexOf('>') + 1), 'filename');
      if (!name) continue;
      const lines = coverage.get(normalise(name)) || new Map();
      const body = cls.replace(/<methods>[\s\S]*?<\/methods>/g, '');
      for (const tag of body.match(/<line\b[^>]*>/g) || []) {
        const number = Number(attr(tag, 'number'));
        const hits = Number(attr(tag, 'hits') || 0);
        if (Number.isInteger(number)) lines.set(number, Math.max(lines.get(number) || 0, hits));
      }
      coverage.set(normalise(name), lines);
    }
  }
  return coverage;
}

function coverageFor(changedPath, coverage) {
  for (const [name, lines] of coverage) {
    if (name === changedPath || changedPath.endsWith('/' + name) || name.endsWith('/' + changedPath)) return lines;
  }
  return null;
}

function ranges(numbers) {
  const out = [];
  for (let i = 0; i < numbers.length; i += 1) {
    let j = i;
    while (j + 1 < numbers.length && numbers[j + 1] === numbers[j] + 1) j += 1;
    out.push(j > i ? `${numbers[i]}-${numbers[j]}` : String(numbers[i]));
    i = j;
  }
  return out.join(',');
}

const plural = (n, word) => `${n} ${word}${n === 1 ? '' : 's'}`;
const percentText = (p) => (p === 100 ? '100%' : `${p.toFixed(1)}%`);

function build(opts) {
  const coverage = readCoverage(opts.coverage);
  const changed = changedLines(opts.compareBranch);
  const stats = {};
  let total = 0, missing = 0, numChanged = 0;
  for (const file of [...changed.keys()].sort()) {
    const touched = [...changed.get(file)].sort((a, b) => a - b);
    numChanged += touched.length;   // every changed line in the diff, covered file or not
    const lines = coverageFor(file, coverage);
    if (!lines) continue;
    const measured = touched.filter((n) => lines.has(n));
    if (!measured.length) continue;
    const covered = measured.filter((n) => lines.get(n) > 0);
    const violations = measured.filter((n) => lines.get(n) === 0);
    total += measured.length;
    missing += violations.length;
    stats[file] = {
      percent_covered: 100 - (violations.length / measured.length) * 100,
      violation_lines: violations,
      covered_lines: covered,
      violations: violations.map((n) => [n, null]),
    };
  }
  const percent = total ? Math.floor(((total - missing) / total) * 100) : 100;
  return {
    report_name: 'XML',
    diff_name: `${opts.compareBranch}...HEAD, staged and unstaged changes`,
    src_stats: stats,
    total_num_lines: total,
    total_num_violations: missing,
    total_percent_covered: percent,
    num_changed_lines: numChanged,
  };
}

function printReport(report) {
  const rule = '-'.repeat(13);
  console.log(`${rule}\nDiff Coverage\nDiff: ${report.diff_name}\n${rule}`);
  if (!report.total_num_lines) {
    console.log('No lines with coverage information in this diff.');
    console.log(rule + '\n');
    return;
  }
  for (const [file, s] of Object.entries(report.src_stats)) {
    const head = `${file} (${percentText(s.percent_covered)})`;
    console.log(s.violation_lines.length ? `${head}: Missing lines ${ranges(s.violation_lines)}` : head);
  }
  console.log(`${rule}\nTotal:   ${plural(report.total_num_lines, 'line')}`);
  console.log(`Missing: ${plural(report.total_num_violations, 'line')}`);
  console.log(`Coverage: ${report.total_percent_covered}%\n${rule}\n`);
}

const opts = parseArgs(process.argv.slice(2));
const report = build(opts);
printReport(report);
if (opts.jsonReport) {
  const target = path.resolve(ROOT, opts.jsonReport);
  fs.mkdirSync(path.dirname(target), { recursive: true });
  fs.writeFileSync(target, JSON.stringify(report));
}
process.exit(report.total_percent_covered < opts.failUnder ? 1 : 0);
