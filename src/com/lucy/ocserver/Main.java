package com.lucy.ocserver;

import com.lucy.ocserver.httpHandlers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
	    int port = 9876;
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started at port: " + port);
        server.createContext("/", new RootHandler());
        server.createContext("/meStatus", new MeStatusHandler());
        server.createContext("/pssStatus", new PSSstatusHandler());

        server.setExecutor(null);
        server.start();
    }
}
