#!/usr/bin/env node
'use strict';
/**
 * PyDriller -- primary tool, Code Churn block. Real git-history miner, not a
 * stub: walks this branch's actual commit log and reports per-file commit
 * counts, the same numbers the former Python wrapper (run_pydriller.py, built
 * on the pydriller package) wrote to reports/pydriller.json.
 *
 * Needs only Node and git. Exit codes follow tools/_skip.sh:
 *   0  ran and wrote reports/pydriller.json
 *   1  ran and failed
 *   4  not installed: this is not a git repository, or git is not on PATH
 *
 * Ported from tools/pydriller/run_pydriller.py.
 */
const fs = require('fs');
const path = require('path');
const { spawnSync } = require('child_process');

const ROOT = path.dirname(path.dirname(__dirname));
const MAX_LOG_BYTES = 512 * 1024 * 1024;

function finish(status, lines, code) {
  console.log('STATUS: ' + status);
  for (const line of lines) console.log(line);
  process.exit(code);
}

function notInstalled(reason) {
  finish('NOT INSTALLED', ['  tool  : pydriller', `  reason: ${reason}`], 4);
}

if (!fs.existsSync(path.join(ROOT, '.git'))) {
  notInstalled(`${ROOT} is not a git repository, and this tool mines history.`);
}

// Oldest commit first, like PyDriller. One entry per commit: "\x01<hash>" followed by "<status>\t<path>[\t<new path>]" lines.
const log = spawnSync(
  'git',
  ['-C', ROOT, '-c', 'core.quotepath=false', 'log', '--reverse', '--format=%x01%H', '--name-status', '-M', 'HEAD'],
  { encoding: 'utf8', maxBuffer: MAX_LOG_BYTES }
);

if (log.error && log.error.code === 'ENOENT') {
  notInstalled('git is not on PATH, and this tool mines history.');
}
if (log.error || log.status !== 0) {
  finish('FAILED', ['  tool  : pydriller', `  error : ${log.error ? log.error.message : (log.stderr || '').trim()}`], 1);
}

const churn = new Map();
let commits = 0;
for (const entry of log.stdout.split('\x01').slice(1)) {
  commits += 1;
  for (const line of entry.split('\n').slice(1)) {
    if (!line) continue;
    const parts = line.split('\t');
    // Renames and copies list "old<TAB>new"; every other status lists one path.
    const file = /^[RC]/.test(parts[0]) ? parts[2] : parts[1];
    if (!file) continue;
    churn.set(file, (churn.get(file) || 0) + 1);
  }
}

const reportsDir = path.join(ROOT, 'reports');
fs.mkdirSync(reportsDir, { recursive: true });
fs.writeFileSync(
  path.join(reportsDir, 'pydriller.json'),
  JSON.stringify({ commits, churnByFile: Object.fromEntries(churn) }, null, 2)
);

finish('RAN', ['  tool    : pydriller', `  commits : ${commits}`, `  files   : ${churn.size}`], 0);
