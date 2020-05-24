package com.lucy.ocserver

import com.lucy.ocserver.httpHandlers.MeStatusHandler
import com.lucy.ocserver.httpHandlers.PowerGenStatusHandler
import com.lucy.ocserver.httpHandlers.PssStatusHandler
import com.lucy.ocserver.httpHandlers.RootHandler
import com.sun.net.httpserver.HttpServer
import java.io.IOException
import java.net.InetSocketAddress

fun main(args: Array<String>) {
    val port = 9876
    val server: HttpServer?
    try {
        server = HttpServer.create(InetSocketAddress(port), 0)
        checkNotNull(server)
    } catch (e: IOException) {
        e.printStackTrace()
        return
    }
    println("Server attached to port: $port")
    server.createContext("/", RootHandler())
    server.createContext("/meStatus", MeStatusHandler())
    server.createContext("/pssStatus", PssStatusHandler())
    server.createContext("/powerStatus", PowerGenStatusHandler())
    server.executor = null
    server.start()
    println("Ready.")
}

typealias EUt = Int
typealias EU = Long