package com.lucy.ocserver.httpHandlers

import com.google.gson.Gson
import com.lucy.ocserver.EU
import com.lucy.ocserver.objects.PowerPack
import com.lucy.ocserver.objects.PowerPackJson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.IOException

class PssStatusHandler : HttpHandler {
    private val powerPacks = mutableMapOf(PowerPackLocation.OldBase to PowerPack(1, 0, false))

    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {
        val parameters = RootHandler.queryToMap(exchange.requestURI.query)
        handleUpdate(parameters)

        val powerPacksJson: MutableMap<PowerPackLocation, PowerPackJson> = mutableMapOf()
        for ((location, pack) in powerPacks) {
            powerPacksJson[location] = pack.getJsonObject()
        }
        val json = Gson().toJson(powerPacksJson)
        RootHandler.sendResponse(exchange, json)
    }

    private fun handleUpdate(parameters: Map<String, String>) {
        val name = parameters["name"] ?: return
        val capacity: EU = parameters["capacity"]?.toLong() ?: return
        val stored: EU = parameters["stored"]?.toLong() ?: return
        val maintenance = parameters["maintenance"] == "true"
        val location = PowerPackLocation.valueOf(name)

        var powerPack = powerPacks[location]
        if (powerPack != null) {
            powerPack.capacity = capacity
            powerPack.set(stored)
            powerPack.maintenance = maintenance
        } else {
            powerPack = PowerPack(capacity, stored, maintenance)
        }

        powerPacks[location] = powerPack
    }
}

enum class PowerPackLocation {
    OldBase, NewBase, Chem
}