package com.plexiti.camunda.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */

class CamundaGradlePlugin implements Plugin<Project> {

    void apply(Project project) {
        project.tasks.create('camunda-deploy', CreateDeploymentTask)
        project.tasks.create('camunda-deploy-resources', CreateDeploymentTask) {
            resources = new File("$project.rootDir/src/main/resources")
        }
    }

}

