package org.camunda.bpm.extension.gradle.rest

import groovyx.net.http.ContentType
import groovyx.net.http.Method

import java.text.SimpleDateFormat

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class ProcessDefinition {

    /**
     * curl -v -w "\n" --cookie cookie.txt \
     * -H "Accept: application/json" \
     * -H "Content-Type: application/json" \
     * -d "{}" \
     * $API/engine/engine/default/process-definition/key/testProcess/start
     */
    static void start(
            Login login,
            String processDefinitionKey,
            Map<String, Object> variables = null,
            Closure onSuccess = Default.onSuccess,
            Closure onFailure = Default.onFailure
    ) {
        login.httpBuilder().request(Method.POST, ContentType.JSON) { request ->
            uri.path = "$login.api/engine/engine/${login.engineName}" +
                    "/process-definition/key/${processDefinitionKey}/start"
            body = [:]
            if (variables) {
                body['variables'] = [:]
                 variables.each {
                    def value = it.value instanceof Date ? 
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(it.value) : it.value
                    body['variables'][it.key] = [value: value, type: it.value.class.simpleName]
                }
            }
            response.success = onSuccess
            response.failure = onFailure
        }
    }

}
