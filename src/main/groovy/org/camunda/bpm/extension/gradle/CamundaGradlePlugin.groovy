package org.camunda.bpm.extension.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class CamundaGradlePlugin implements Plugin<Project> {

    void apply(Project project) {
        project.tasks.create('deployResources', CreateDeploymentTask) {
            resources = new File("$project.rootDir/src/main/resources")
        }
        project.tasks.create('startProcessInstance', StartProcessInstanceTask)
    }

}

