import com.plexiti.camunda.gradle.CreateDeploymentTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class CamundaGradlePluginTest extends Specification {

    void greeterPluginAddsGreetingTaskToProject() {
        given:
        Project project = ProjectBuilder.builder().build()
        when:
        project.apply plugin: 'com.plexiti.camunda'
        then:
        assertThat(project.tasks.'camunda-deploy').isInstanceOf(CreateDeploymentTask)
    }
    
}
