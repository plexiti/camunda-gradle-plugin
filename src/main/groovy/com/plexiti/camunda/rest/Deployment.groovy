package com.plexiti.camunda.rest

import groovyx.net.http.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder

import groovyx.net.http.Method

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class Deployment {
    
    /**
     * curl --trace-ascii "$TRACE" -w "\n" --cookie cookie.txt \
     * -H "Accept: application/json" \
     * -F "deployment-name=rest-test" \
     * -F "enable-duplicate-filtering=false" \
     * -F "deploy-changed-only=false" \
     * -F "com/plexiti/test/MyTestScript.groovy=@$SCRIPT;filename=com/plexiti/test/MyTestScript.groovy" \
     * -F "process.bpmn=@$PROCESS" \
     * $API/engine/engine/default/deployment/create
     */
    static void create(
            Login login, 
            Map<String, File> resources,
            boolean deployChangedOnly = false,
            String deploymentName = "RestClientDeployment",
            Closure onSuccess = Default.onSuccess, 
            Closure onFailure = Default.onFailure
    ) {
        def builder = MultipartEntityBuilder.create()
        builder.addTextBody('deployment-name', deploymentName)
        builder.addTextBody('deploy-changed-only', deployChangedOnly as String)
        builder.addTextBody('enable-duplicate-filtering', deployChangedOnly as String)
        def i = 1; resources.each {
            builder.addBinaryBody("deployment-resource-${i++}", it.value, 
                org.apache.http.entity.ContentType.APPLICATION_OCTET_STREAM, it.key)
        }
        login.httpBuilder().request(Method.POST, ContentType.JSON) { request ->
            uri.path = "${login.api}/engine/engine/${login.engineName}/deployment/create"
            println(uri.path)
            requestContentType = "multipart/form-data"
            request.setEntity(builder.build())
            response.success = onSuccess
            response.failure = onFailure
        }
    }
    
}
