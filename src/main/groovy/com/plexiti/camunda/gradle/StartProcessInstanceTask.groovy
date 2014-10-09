package com.plexiti.camunda.gradle

import com.plexiti.camunda.rest.Deployment
import com.plexiti.camunda.rest.Login
import com.plexiti.camunda.rest.ProcessDefinition
import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class StartProcessInstanceTask extends DefaultTask {

    String processDefinitionKey

    @TaskAction
    def startProcessInstance() {
        ProcessDefinition.start(
            Login.login(
                project.property('camunda.api') as String,
                project.property('camunda.username') as String,
                project.property('camunda.password') as String
            ),
            processDefinitionKey
        )
    }

}