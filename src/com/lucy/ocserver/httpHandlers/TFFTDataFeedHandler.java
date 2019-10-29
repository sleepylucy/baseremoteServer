package com.lucy.ocserver.httpHandlers;

import java.io.IOException;

import com.google.gson.Gson;
import com.kekzdealer.data.FluidStorage;
import com.kekzdealer.jsonObjects.TFFTJSON;
import com.kekzdealer.util.NetworkUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TFFTDataFeedHandler  implements HttpHandler {

	private final FluidStorage fluidStorage;
	
	public TFFTDataFeedHandler(FluidStorage fluidStorage) {
		this.fluidStorage = fluidStorage;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		final String post = NetworkUtil.readStreamToEnd(exchange.getRequestBody());
		final Gson gson = new Gson();
		final TFFTJSON tfftJson = gson.fromJson(post, TFFTJSON.class);
		fluidStorage.updateFromFeed(tfftJson);
	}
}
