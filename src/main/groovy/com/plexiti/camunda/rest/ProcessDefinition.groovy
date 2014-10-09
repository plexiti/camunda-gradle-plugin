package com.plexiti.camunda.rest

import groovyx.net.http.ContentType
import groovyx.net.http.Method

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
            Closure onSuccess = Default.onSuccess,
            Closure onFailure = Default.onFailure
    ) {
        login.httpBuilder().request(Method.POST, ContentType.JSON) { request ->
            uri.path = "$login.api/engine/engine/${login.engineName}/process-definition/key/${processDefinitionKey}/start"
            body = [:]
            response.success = onSuccess
            response.failure = onFailure
        }
    }

}
