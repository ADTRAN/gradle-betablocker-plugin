.. image:: https://api.bintray.com/packages/adtran/maven/betablocker-plugin/images/download.svg
  :target: https://bintray.com/adtran/maven/betablocker-plugin/_latestVersion
  :alt: download
.. image:: https://travis-ci.org/ADTRAN/gradle-betablocker-plugin.svg?branch=main
  :target: https://travis-ci.org/ADTRAN/gradle-betablocker-plugin

==================
Betablocker Plugin
==================

This Gradle plugin rejects dependencies (including transitives) whose version string includes a configurable list of
patterns. It is intended to be used to block non-stable versions of dependencies from inadvertently being consumed by a
project.

Setup
=====

First, add the appropriate buildscript repository and dependency::

    buildscript {
      repositories {
        mavenCentral()
      }
      dependencies {
        classpath "com.adtran:betablocker-plugin:1.+"
      }
    }

Then apply the plugin using either the old style::

    apply plugin: "com.adtran.betablocker-plugin"

...or the new style::

    plugins {
      id "com.adtran.betablocker-plugin"
    }

It's also available via `plugins.gradle.org`_.

.. _plugins.gradle.org: https://plugins.gradle.org/plugin/com.adtran.betablocker-plugin

Configuration
=============

To configure the plugin, add a block like the following to your ``build.gradle``::

    betablocker {
      enabled true
      whitelist = []
      rejectedVersions = ["alpha", "beta", "m", "snap", "latest", "rc"]
      rejectedVersionPatterns = [".*-[a-f0-9]{7}"]
    }

These properties are described in the following table:

===========================  =============  ========================  ========================================================
Property                     Type           Default                   Description
===========================  =============  ========================  ========================================================
``enabled``                  Boolean        ``true``                  Set to false to disable the plugin entirely
``whitelist``                List<String>   ``[]``                    A list of strings which if present in the display name
                                                                      of an artifact will prevent the plugin from rejecting
                                                                      any versions
``rejectedVersions``         List<String>   ``["alpha", "beta", "m",  A list of strings which if present in the version of an
                                            "snap", "latest",         artifact will cause it to be rejected (case insensitive).
                                            "rc"]``
``rejectedVersionPatterns``  List<String>   ``[".*-[a-f0-9]{7}"]``    A list of regular expression patterns which will cause
                                                                      a dependency to be rejected on match (case insensitive
                                                                      / lower case).
===========================  =============  ========================  ========================================================

License
=======

This project is licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0). Copyright
2018, ADTRAN, Inc.

Contributing
============

Issues and pull requests are welcome if you have bugs/suggestions/improvements!

Publishing
==========

CI is managed with Github actions. Artifacts are published to `plugins.gradle.org`_ and `Maven
Central`_. Publishing to Maven Central is done via OSSRH. After CI uploads the artifacts to
OSSRH, a maintainer has to promote the build to Maven Central:

1. Log in to the Nexus instance at https://s01.oss.sonatype.org/ as the ``adtran-maven`` user
2. Click on "Staging Repositories" in the left-hand pane
3. Select the new repository (artifact)
4. Click "Close" in the toolbar
5. Once it's closed (hit "Refresh" if needed), click "Release"
6. It should show up in Maven Central eventually

Official instructions here_.

.. _plugins.gradle.org: https://plugins.gradle.org/plugin/com.adtran.betablocker-plugin
.. _Maven Central: https://search.maven.org/artifact/com.adtran/betablocker-plugin
.. _here: https://central.sonatype.org/publish/release/
