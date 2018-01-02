.. image:: https://api.bintray.com/packages/adtran/maven/betablocker-plugin/images/download.svg
  :target: https://bintray.com/adtran/maven/betablocker-plugin/_latestVersion
  :alt: download

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
      rejectedVersions = ["alpha", "beta", "m", "snap", "latest"]
    }

These properties are described in the following table:

=====================  =============  ========================  ========================================================
Property               Type           Default                   Description
=====================  =============  ========================  ========================================================
``enabled``            Boolean        ``true``                  Set to false to disable the plugin entirely
``whitelist``          List<String>   ``[]``                    A list of strings which if present in the display name
                                                                of an artifact will prevent the plugin from rejecting
                                                                any versions
``rejectedVersions``   List<String>   ``["alpha", "beta", "m",  A list of strings which if present in the version of an
                                      "snap", "latest"]``       artifact will cause it to be rejected (case insensitive)
=====================  =============  ========================  ========================================================

License
=======

This project is licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0). Copyright
2018, ADTRAN, Inc.

Contributing
============

Issues and pull requests are welcome if you have bugs/suggestions/improvements!
