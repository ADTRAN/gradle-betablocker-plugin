/*
 * Copyright 2017 ADTRAN, Inc.
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
package unit

import com.adtran.BetablockerPlugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class TestBetablockerPlugin extends GroovyTestCase {

    private Project createProject(Closure config = {})
    {
        Project project = ProjectBuilder.builder().build()
        // fakeRepo contains "fake-dep-1.2.3.jar" and "fake-dep-1.3.0-beta0.jar"
        project.repositories.flatDir { dirs new File(System.getProperty("user.dir"), "src/test/fakeRepo") }
        project.configurations.create("compile")
        project.pluginManager.apply(BetablockerPlugin)
        config.delegate = project
        config(project)
        project.evaluate()
        return project
    }

    private void assertResolvedVersion(String expectedVersion, Project project) {
        def conf = project.configurations.getByName("compile").resolvedConfiguration.lenientConfiguration
        assert conf.unresolvedModuleDependencies.size() == 0
        def deps = conf.getAllModuleDependencies()
        assert deps.size() == 1
        assert deps.head().moduleVersion == expectedVersion
    }

    void testPluginApply() {
        assert createProject().pluginManager.hasPlugin("com.adtran.betablocker-plugin")
    }

    void testBlockBeta(){
        Project project = createProject { dependencies.compile "org.fake:fake-dep:1.+" }
        assertResolvedVersion("1.2.3", project)
    }

    void testAllowsInWhitelist() {
        Project project = createProject {
            betablocker.whitelist = ["fake-dep"]
            dependencies.compile "org.fake:fake-dep:1.+"
        }
        assertResolvedVersion("1.3.0-beta0", project)
    }

    void testAllowsNonBeta() {
        Project project = createProject { dependencies.compile "org.fake:fake-dep:1.2.3" }
        assertResolvedVersion("1.2.3", project)
    }

    void testRejectedVersionsOverride() {
        Project project = createProject {
            betablocker.rejectedVersions = ["0"]
            dependencies.compile "org.fake:fake-dep:1.+"
        }
        assertResolvedVersion("1.2.3", project)
    }

    void testResolveAllowBeta() {
        Project project = createProject {
            betablocker.rejectedVersions = []
            dependencies.compile "org.fake:fake-dep:1.+"
        }
        assertResolvedVersion("1.3.0-beta0", project)
    }

    void testDisable() {
        def project = createProject {
            project.betablocker.enabled = false
            dependencies.compile "org.fake:fake-dep:1.+"
        }
        assertResolvedVersion("1.3.0-beta0", project)
    }
}
