package com.lucy.ocserver.httpHandlers

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class MeStatusHandler: HttpHandler {
    var meStatus = true
    var euUsage = 0

    override fun handle(exchange: HttpExchange?) {
        val parameters = RootHandler.queryToMap(exchange!!.requestURI.query)

        // update Values
        if (parameters.containsKey("status")) {
            val setStatus = parameters["status"]
            meStatus = setStatus == "true"
            println("Set status to $meStatus")
        }
        if (parameters.containsKey("usageRF")) {
            val usageRF = java.lang.Double.valueOf(parameters["usageRF"])
            val usageEU = usageRF / 3.77
            euUsage = Math.floor(usageEU).toInt()
            println("Set ME EU Usage to $euUsage")
        }

        val meStatusString = if (meStatus) "true/$euUsage" else "false/$euUsage"
        RootHandler.sendResponse(exchange, meStatusString)
    }
}