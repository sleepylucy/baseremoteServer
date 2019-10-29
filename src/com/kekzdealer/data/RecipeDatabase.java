package com.kekzdealer.data;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import com.google.gson.Gson;
import com.kekzdealer.jsonObjects.Recipe;
import com.kekzdealer.jsonObjects.RecipesJSON;

public class RecipeDatabase {
	
	public static final String DB_FILENAME = "recipe_database.json";
	public static final String DB_LOC = "/com/kekzdealer/data/";
	
	private final HashSet<Recipe> recipes = new HashSet<>();
	
	public RecipeDatabase() {
		loadFromDisk();
	}
	
	public synchronized String addRecipe(Recipe recipe) {
		String response = "Success";
		// Check for duplicates
		for(Recipe r : recipes) {
			if(r.result.equals(recipe.result)) {
				response = "Recipe with same result already exists";
				return response;
			}
		}
		// Add recipe
		recipes.add(recipe);
		saveToDisk();
		return response;
	}
	
	public synchronized String removeRecipe(String result) {
		String response = "Success";
		final Iterator<Recipe> it = recipes.iterator();
		while(it.hasNext()) {
			final Recipe r = it.next();
			if(r.result.equals(result)) {
				it.remove();
				return response;
			}
		}
		response = "Could not find Recipe";
		return response;
	}
	
	private void saveToDisk() {
		final RecipesJSON recipesJSON = new RecipesJSON();
		recipesJSON.recipes = recipes;
		final Gson gson = new Gson();
		final String jsonString = gson.toJson(recipesJSON);
		
		final java.io.File overwrite = new java.io.File(DB_LOC + DB_FILENAME);
		try {
			final FileWriter fw = new FileWriter(overwrite, false);
			fw.write(jsonString);
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void loadFromDisk() {
		final RecipesJSON recipesJSON = 
				parseLocalJSON(DB_LOC, DB_FILENAME, RecipesJSON.class);
		recipes.clear();
		recipes.addAll(recipesJSON.recipes);
	}
	
	private <T> T parseLocalJSON (String loc, String jsonName, Class<T> dataHolder) {
		
		final File file = new File(loc + jsonName);
		String jsonString = "";
		
		try (BufferedReader br = file.getReader()){
			final StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			jsonString = sb.toString();
		} catch (IOException ex) {
			System.err.println("IOException while reading: " +jsonName);
		}
		
		final Gson gson = new Gson();
		return gson.fromJson(jsonString, dataHolder);
	}
	
}
