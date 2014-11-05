package org.camunda.bpm.extension.gradle.rest

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.apache.http.Header
import org.apache.http.HttpResponse

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class Login {

    String api
    String cookie = ''
    String username
    String password
    String engineName

    HTTPBuilder httpBuilder() {
        def http = new HTTPBuilder(api)
        if (cookie)
            http.headers['Cookie'] = cookie
        http.auth.basic (username, password)
        return http
    }

    /**
     * curl -v -w "\n" --cookie-jar cookie.txt \
     * -H "Accept: application/json" \
     * -d "username=$USERNAME" \
     * -d "password=$PASSWORD" \
     * $API/admin/auth/user/default/login/cockpit
     */
    static Login login(
            String api,
            String username,
            String password,
            String engineName = Default.engineName,
            Closure onSuccess = Default.onSuccess,
            Closure onFailure = Default.onFailure
    ) {
        def login = new Login(api: api, username: username, password: password, engineName: engineName)
        login.httpBuilder().request(Method.POST, ContentType.JSON) {
            uri.path = "$api/admin/auth/user/default/login/cockpit"
            requestContentType = ContentType.URLENC
            body = [username: username, password: password]
            response.success = { HttpResponse response, content ->
                response.getHeaders('Set-Cookie').each { Header header ->
                    login.cookie += (header.value.split(';')[0])
                    if (header != response.getLastHeader("Set-Cookie"))
                        login.cookie += '; '
                }
                onSuccess.call(response, content)
            }
            response.failure = onFailure
        }
        return login
    }

}
