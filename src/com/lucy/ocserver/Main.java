package com.lucy.ocserver;

import com.lucy.ocserver.httpHandlers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
	    int port = 9876;
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            if (server == null) {
                throw new IllegalStateException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Server attached to port: " + port);

        server.createContext("/", new RootHandler());
        server.createContext("/meStatus", new MeStatusHandler());
        server.createContext("/pssStatus", new PSSstatusHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Ready.");
    }
}
