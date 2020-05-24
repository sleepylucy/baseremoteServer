package com.lucy.ocserver.httpHandlers

import com.google.gson.Gson
import com.lucy.ocserver.Utilities
import com.lucy.ocserver.objects.MeSystem
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class MeStatusHandler: HttpHandler {
    private val meSystems = mutableMapOf(MELocation.OldBase to MeSystem(true, 0))

    override fun handle(exchange: HttpExchange?) {
        val parameters = RootHandler.queryToMap(exchange!!.requestURI.query)
        val oldBaseME = meSystems[MELocation.OldBase] ?: return

        // update Values
        if (parameters.containsKey("status")) {
            val setStatus = parameters["status"]
            oldBaseME.active = setStatus == "true"
            println("Set status to ${oldBaseME.active}")
        }
        if (parameters.containsKey("usageRF")) {
            val usageRF = java.lang.Double.valueOf(parameters["usageRF"])
            oldBaseME.powerConsumption = Utilities.convertRFtoEU(usageRF)
            println("Set ME EU Usage to ${oldBaseME.powerConsumption}")
        }
        if (parameters.containsKey("statusFor")) {
            val name = parameters["statusFor"] ?: return
            val location = MELocation.valueOf(name)
            val meSystem = meSystems[location] ?: return
            RootHandler.sendResponse(exchange, meSystem.active.toString())
            return
        }

        meSystems[MELocation.OldBase] = oldBaseME
        val json = Gson().toJson(meSystems)
        RootHandler.sendResponse(exchange, json)
    }
}

enum class MELocation {
    OldBase
}