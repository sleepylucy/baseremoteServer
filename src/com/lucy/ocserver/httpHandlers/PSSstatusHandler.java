package com.lucy.ocserver.httpHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

public class PSSstatusHandler implements HttpHandler {
    private long capacity = 1;
    private long stored = 0;
    private boolean reqMaintenance = false;


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> parameters = RootHandler.queryToMap(exchange.getRequestURI().getQuery());

        // update values
        if (parameters.containsKey("capacity")) {
            capacity = Long.valueOf(parameters.get("capacity"));
            System.out.println("Set capacity to " + capacity);
        }
        if (parameters.containsKey("stored")) {
            stored = Long.valueOf(parameters.get("stored"));
            System.out.println("Set stored EU to " + stored);
        }
        if (parameters.containsKey("maintenance")) {
            reqMaintenance = parameters.get("maintenance").equals("true");
            System.out.println("Set maintenance to " + reqMaintenance);
        }

        String pssStatus = stored + "/" + capacity + "/" + reqMaintenance;
        RootHandler.sendResponse(exchange, pssStatus);
    }
}
