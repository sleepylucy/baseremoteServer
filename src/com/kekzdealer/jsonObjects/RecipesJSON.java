package com.kekzdealer.jsonObjects;

import java.util.Set;

public class RecipesJSON {
	
	public Set<Recipe> recipes;
	
	public Recipe findRecipesByResult(String result) {
		for(Recipe recipe : recipes) {
			if(recipe.result.equals(result)) {
				return recipe;
			}
		}
		return null;
	}
}
