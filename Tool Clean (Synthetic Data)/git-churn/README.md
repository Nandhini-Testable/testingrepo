# git-churn

Synthetic, clean-by-design Java project for **git-churn (`git log --numstat`)**.

Domain: harbour-wall lantern roster (`LanternRoster`).

**Measured**: `git log --numstat` was run for real on the repository restored from `_git-bundles/git-churn-gitdata.tar.gz`; the numbers below are its output.

## What a passing result looks like

Low, spread-out churn: 5 commits by 3 authors, 76 lines added and 1 deleted in total, and no file touched by more than 3 commits (`src/main/java/lanternroster/LanternRoster.java`: 50 added, 1 deleted, 3 commits). Nothing is rewritten repeatedly, so no file is a churn hotspot.

## Command

```bash
git log --numstat --pretty=format:'%H|%an|%ae|%ad'
```

Per-file totals from that output (added / deleted / commits touching the file):

```text
src/main/java/lanternroster/LanternRoster.java                50 /   1 / 3
src/test/java/lanternroster/LanternRosterTest.java            26 /   0 / 2
```

## Layout

```text
git-churn/
  README.md
  src/main/java/lanternroster/LanternRoster.java
  src/test/java/lanternroster/LanternRosterTest.java
  .git/        real repo: 5 commits, 3 authors (restored from _git-bundles/git-churn-gitdata.tar.gz)
```

## Notes

Git history is what `git log --numstat` reads, not the Java source, so this folder is single-version and unversioned, like the diff-cover and pydriller folders. The repository is committed as a tar.gz of its `.git` folder because a plain file copy (and a git repository inside a git repository) would silently drop it; `restore-git.ps1` restores it. The authors are the three synthetic co-authors already used for the pydriller folders (Ada Renwick, Mikkel Aas, Priya Nallan); all commit dates are fixed (May 2026), so the history is the same every time it is restored. The working-tree files compile and their JUnit tests pass at every `--release` from 8 to 25 on a JDK 25 host.
