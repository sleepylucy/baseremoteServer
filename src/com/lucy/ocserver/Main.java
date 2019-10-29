package com.lucy.ocserver;

import com.kekzdealer.CraftingSheduler;
import com.kekzdealer.data.FluidStorage;
import com.kekzdealer.data.ItemStorage;
import com.kekzdealer.data.RecipeDatabase;
import com.kekzdealer.util.Logger;
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
        Logger.INFO.print("Server attached to port: " + port);

        server.createContext("/", new RootHandler());
        server.createContext("/meStatus", new MeStatusHandler());
        server.createContext("/pssStatus", new PSSstatusHandler());
        
        // Chemistry Control
        Logger.INFO.print("Starting Chemistry Control Server...");
        final RecipeDatabase recipeDatabase = new RecipeDatabase();    
        final FluidStorage fluidStorage = new FluidStorage(); 
        final ItemStorage itemStorage = new ItemStorage();
        
        final Thread craftingShedulerThread = new Thread(new CraftingSheduler(fluidStorage, itemStorage));
        craftingShedulerThread.setName("Chemistry Crafting Sheduler");
        craftingShedulerThread.start();
        
        server.createContext("/chemistry/commit_recipe", new CommitRecipeHandler(recipeDatabase));
        server.createContext("/chemistry/shedule_craft", null);// TODO create new handler here
        server.createContext("/chemistry/tfft_data_feed", new TFFTDataFeedHandler(fluidStorage));
        
        
        server.setExecutor(null);
        server.start();
        Logger.INFO.print("Ready.");
    }
}
