package com.lucy.ocserver.httpHandlers

import com.google.gson.Gson
import com.lucy.ocserver.EUt
import com.lucy.ocserver.objects.PowerGen
import com.lucy.ocserver.objects.PowerGenType
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.IOException

class PowerGenStatusHandler: HttpHandler {
    private val powerGens = mutableMapOf<PowerGenLocation, PowerGen>()

    // url: /powerStatus ?name=Dam11 &production=12345
    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {
        val parameters = RootHandler.queryToMap(exchange.requestURI.query)
        handleUpdate(parameters)

        val json = Gson().toJson(powerGens)
        RootHandler.sendResponse(exchange, json)
    }

    private fun handleUpdate(parameters: Map<String, String>) {
        val name = parameters["name"] ?: return
        val production: EUt = parameters["production"]?.toInt() ?: return
        val location = PowerGenLocation.valueOf(name)

        println("Handle Update for $name : Production set to $production")

        var powerGen = powerGens[location]
        if (powerGen != null) {
            powerGen.production = production
        } else {
            powerGen = PowerGen(location.type(), production)
        }

        powerGens[location] = powerGen
    }
}

enum class PowerGenLocation {
    Dam11, Dam12, Dam13, Dam14, Dam15, Dam16, Dam17,
    Dam21, Dam22, Dam23, Dam24, Dam25, Dam26, Dam27;

    fun type(): PowerGenType {
        return when(this) {
            Dam11, Dam12, Dam13, Dam14, Dam15, Dam16, Dam17 -> PowerGenType.Hydro
            Dam21, Dam22, Dam23, Dam24, Dam25, Dam26, Dam27 -> PowerGenType.Hydro
        }
    }
}