package com.kekzdealer.data;

import java.util.HashMap;
import java.util.HashSet;

import com.kekzdealer.jsonObjects.Item;
import com.kekzdealer.jsonObjects.TFFTJSON;

public class FluidStorage {
	
	private HashMap<String, Integer> tfftFluids = new HashMap<>();
	private HashSet<Runnable> updateSubscribers = new HashSet<>();
	
	public void updateFromFeed(TFFTJSON tfftJson) {
		tfftFluids.clear();
		for(Item fluid : tfftJson.fluids) {
			if(tfftFluids.containsKey(fluid.name)) {
				tfftFluids.put(fluid.name, tfftFluids.get(fluid.name) + fluid.amount);
			} else {
				tfftFluids.put(fluid.name, fluid.amount);
			}
		}
		
		for(Runnable sub : updateSubscribers) {
			sub.notify();
		}
	}
	
	public Item isAvailable(Item fluid) {
		final Item available = new Item();
		available.name = fluid.name;
		available.amount = 
				tfftFluids.containsKey(fluid.name) ? Math.min(fluid.amount, tfftFluids.get(fluid.name)) 
						: 0;
		return available;
	}
	
	public void subscribeForUpdates(Runnable runnable) {
		updateSubscribers.add(runnable);
	}
	
}
