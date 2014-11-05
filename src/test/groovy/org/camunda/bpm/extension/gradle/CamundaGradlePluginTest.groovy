package org.camunda.bpm.extension.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import static org.assertj.core.api.Assertions.*

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class CamundaGradlePluginTest extends Specification {

    void greeterPluginAddsGreetingTaskToProject() {
        given:
        Project project = ProjectBuilder.builder().build()
        when:
        project.apply plugin: 'org.camunda.bpm'
        then:
        assertThat(project.tasks.'deployResources').isInstanceOf(CreateDeploymentTask)
        and:
        assertThat(project.tasks.'startProcessInstance').isInstanceOf(StartProcessInstanceTask)
    }
    
}
