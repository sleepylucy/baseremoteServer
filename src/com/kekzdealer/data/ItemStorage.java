package com.kekzdealer.data;

import java.util.HashMap;
import java.util.HashSet;

import com.kekzdealer.jsonObjects.Item;

public class ItemStorage {
	
	private HashMap<String, Integer> items = new HashMap<>();
	private HashSet<Runnable> updateSubscribers = new HashSet<>();
	
	public void updateFromFeed(Object json) {
		items.clear();

		for(Runnable sub : updateSubscribers) {
			sub.notify();
		}
	}
	
	public Item isAvailable(Item item) {
		final Item available = new Item();
		available.name = item.name;
		available.amount = 
				items.containsKey(item.name) ? Math.min(item.amount, items.get(item.name)) 
						: 0;
		return available;
	}
	
	public void subscribeForUpdates(Runnable runnable) {
		updateSubscribers.add(runnable);
	}
	
}
