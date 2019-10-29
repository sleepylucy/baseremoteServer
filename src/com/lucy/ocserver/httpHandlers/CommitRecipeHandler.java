package com.lucy.ocserver.httpHandlers;

import java.io.IOException;

import com.google.gson.Gson;
import com.kekzdealer.data.RecipeDatabase;
import com.kekzdealer.jsonObjects.Recipe;
import com.kekzdealer.util.NetworkUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CommitRecipeHandler implements HttpHandler {

	private final RecipeDatabase recipeDatabase;
	
	public CommitRecipeHandler(RecipeDatabase recipeDatabase) {
		this.recipeDatabase = recipeDatabase;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		final String post = NetworkUtil.readStreamToEnd(exchange .getRequestBody());
		final Gson gson = new Gson();
		final Recipe recipe = gson.fromJson(post, Recipe.class);
		final String result = recipeDatabase.addRecipe(recipe);
	}

}
