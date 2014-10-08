package com.plexiti.camunda.rest

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import org.apache.http.Header

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class Default {

    static def onSuccess = { HttpResponseDecorator response, Object content ->
        println("> HTTP request succeeded with $response.statusLine")
        println("> HTTP response body: $content")
    }

    static def onFailure = { HttpResponseDecorator response ->
        System.err.println("> HTTP request failed with $response.statusLine")
        response.headers.each { Header header ->
            System.err.println("> $header.name: $header.value")
        }
        if (response.data)
            System.err.println("> HTTP response body: $response.data")
        throw new HttpResponseException(response)
    }

}
