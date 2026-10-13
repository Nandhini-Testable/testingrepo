# git-churn

Inverse-corpus counterpart to the sibling Java-Tools-Clean's **git-churn** folder. Engineered so the tool should find something genuinely wrong.

Domain: grain-hopper batch blending (`HopperBatch`).

**Measured**: `git log --numstat` was run for real on the repository restored from `_git-bundles/git-churn-gitdata.tar.gz`; the numbers below are its output.

## What a failing result looks like

One churn hotspot: `src/main/java/hopperbatch/HopperBatch.java` is rewritten in all 8 commits (73 lines added and 42 deleted across 8 commits, for a file that is only a few dozen lines long); the test file is touched once. The history is 8 commits by 3 authors.

## Command

```bash
git log --numstat --pretty=format:'%H|%an|%ae|%ad'
```

Per-file totals from that output (added / deleted / commits touching the file):

```text
src/main/java/hopperbatch/HopperBatch.java                    73 /  42 / 8
src/test/java/hopperbatch/HopperBatchTest.java                13 /   0 / 1
```

## Layout

```text
git-churn/
  README.md
  src/main/java/hopperbatch/HopperBatch.java
  src/test/java/hopperbatch/HopperBatchTest.java
  .git/        real repo: 8 commits, 3 authors (restored from _git-bundles/git-churn-gitdata.tar.gz)
```

## Notes

Git history is what `git log --numstat` reads, not the Java source, so this folder is single-version and unversioned, like the diff-cover and pydriller folders. The repository is committed as a tar.gz of its `.git` folder because a plain file copy (and a git repository inside a git repository) would silently drop it; `restore-git.ps1` restores it. The authors are the three synthetic co-authors already used for the pydriller folders (Ada Renwick, Mikkel Aas, Priya Nallan); all commit dates are fixed (May 2026), so the history is the same every time it is restored. The working-tree files compile and their JUnit tests pass at every `--release` from 8 to 25 on a JDK 25 host.
