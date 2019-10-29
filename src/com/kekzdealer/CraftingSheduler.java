package com.kekzdealer;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.kekzdealer.data.FluidStorage;
import com.kekzdealer.data.ItemStorage;
import com.kekzdealer.jsonObjects.Recipe;

public class CraftingSheduler implements Runnable {

	private final FluidStorage fluidStorage;
	private final ItemStorage itemStorage;
	private final Vector<Recipe> queue = new Vector<>();

	public CraftingSheduler(FluidStorage fluidStorage, ItemStorage itemStorage) {
		this.fluidStorage = fluidStorage;
		this.itemStorage = itemStorage;
		
		fluidStorage.subscribeForUpdates(this);
		itemStorage.subscribeForUpdates(this);
	}
	
	@Override
	public void run() {
		// Wait for fresh data
		
		
	}
	
	public void sheduleCraft(Recipe recipe) {
		
	}
	
	private List<Recipe> resolveNestedRecipe(Recipe recipe){
		final List<Recipe> chain = new ArrayList<>();
		
	}

}
