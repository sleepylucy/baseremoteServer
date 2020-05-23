package com.lucy.ocserver.httpHandlers

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.IOException
import java.util.*

class RootHandler : HttpHandler {
    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {
        val response = "This is Lucy's OC Server running"
        exchange.sendResponseHeaders(200, response.length.toLong())
        val os = exchange.responseBody
        os.write(response.toByteArray())
        os.close()
    }

    companion object {
        @Throws(IOException::class)
        fun sendResponse(exchange: HttpExchange, response: String) {
            exchange.sendResponseHeaders(200, response.length.toLong())
            val os = exchange.responseBody
            os.write(response.toByteArray())
            os.close()
        }

        fun queryToMap(query: String?): Map<String, String> {
            val result: MutableMap<String, String> = HashMap()
            if (query == null) {
                return result
            }
            for (param in query.split("&".toRegex()).toTypedArray()) {
                val entry = param.split("=".toRegex()).toTypedArray()
                if (entry.size > 1) {
                    result[entry[0]] = entry[1]
                } else {
                    result[entry[0]] = ""
                }
            }
            return result
        }
    }
}