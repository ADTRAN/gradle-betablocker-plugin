# Commit Format

This project is no longer auto-versioned using
[gradle-semantic-release-plugin](https://github.com/tschulte/gradle-semantic-release-plugin).
Instead, it gets its MAJOR.MINOR versions from the ``artifact_version`` in ``gradle.properties`` and
then determines the BUGFIX number automatically by inspecting the git tags and selecting the next
sequential number in that MAJOR.MINOR range (or zero if none yet).
