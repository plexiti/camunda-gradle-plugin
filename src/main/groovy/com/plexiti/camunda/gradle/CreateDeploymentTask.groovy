package com.plexiti.camunda.gradle

import com.plexiti.camunda.rest.Deployment
import com.plexiti.camunda.rest.Login
import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class CreateDeploymentTask extends DefaultTask {

    File resources

    @TaskAction
    def createDeployment() {
        def deployment = [:]
        resources.eachFileRecurse(FileType.FILES) { File file ->
            deployment[file.path.substring(resources.path.size() + 1)] = file
        }
        Deployment.create(
            Login.login(
                project.property('camunda.api') as String,
                project.property('camunda.username') as String,
                project.property('camunda.password') as String
            ),
            deployment,
            true
        )
    }

}
