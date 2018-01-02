/*
 * Copyright 2018 ADTRAN, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adtran

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ComponentSelection

class BetablockerPlugin implements Plugin<Project> {
    private Project project

    void apply(Project project) {
        this.project = project
        project.extensions.create("betablocker", BetablockerPluginExtension)
        project.afterEvaluate {
            configureComponentSelection()
        }
    }

    private void configureComponentSelection() {
        if (project.betablocker.enabled) {
            project.configurations.each {
                it.resolutionStrategy.componentSelection.all { screen(it) }
            }
        }
    }

    private void screen(ComponentSelection selection) {
        def inWhiteList = project.betablocker.whitelist.find { String s ->
            selection.candidate.displayName.contains(s)
        }
        if (!inWhiteList) {
            project.betablocker.rejectedVersions.each {
                if (selection.candidate.version.toLowerCase().contains(it)) {
                    selection.reject("betablocker rejecting $selection.candidate.displayName (contains $it)")
                }
            }
        } else {
            project.logger.debug("betablocker allowing $selection.candidate.displayName (whitelisted)")
        }
    }
}
