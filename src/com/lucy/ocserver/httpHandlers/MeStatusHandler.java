package com.lucy.ocserver.httpHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

public class MeStatusHandler implements HttpHandler {
    private boolean meStatus = true;
    private int euUsage = 0;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> parameters = RootHandler.queryToMap(exchange.getRequestURI().getQuery());

        // update Values
        if (parameters.containsKey("status")) {
            String setStatus = parameters.get("status");
            meStatus = setStatus.equals("true");
            System.out.println("Set status to " + meStatus);
        }
        if (parameters.containsKey("usageRF")) {
            double usageRF = Double.valueOf(parameters.get("usageRF"));
            double usageEU = usageRF / 3.77;
            euUsage = (int) Math.floor(usageEU);
            System.out.println("Set ME EU Usage to " + euUsage);
        }

        String meStatusString = meStatus ? "true/" + euUsage : "false/" + euUsage;
        RootHandler.sendResponse(exchange, meStatusString);
    }
}
