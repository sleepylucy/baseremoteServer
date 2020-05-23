package com.lucy.ocserver.httpHandlers

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.IOException

class PSSstatusHandler : HttpHandler {
    private var capacity: Long = 1
    private var stored: Long = 0
    private var reqMaintenance = false

    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {
        val parameters = RootHandler.queryToMap(exchange.requestURI.query)

        // update values
        if (parameters.containsKey("capacity")) {
            capacity = java.lang.Long.valueOf(parameters["capacity"])
            println("Set capacity to $capacity")
        }
        if (parameters.containsKey("stored")) {
            stored = java.lang.Long.valueOf(parameters["stored"])
            println("Set stored EU to $stored")
        }
        if (parameters.containsKey("maintenance")) {
            reqMaintenance = parameters["maintenance"] == "true"
            println("Set maintenance to $reqMaintenance")
        }
        val pssStatus = "$stored/$capacity/$reqMaintenance"
        RootHandler.sendResponse(exchange, pssStatus)
    }
}