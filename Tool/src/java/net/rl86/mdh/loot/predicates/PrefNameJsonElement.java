package net.rl86.mdh.loot.predicates;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PrefNameJsonElement {
	
	private String preferredName;
	private JsonElement element;

	public PrefNameJsonElement(String prefName, JsonElement content) {
		preferredName = prefName;
		element = content;
	}
	
	public void addTo(JsonObject target) {
		target.add(preferredName, element);
	}
	
	public void addTo(JsonArray target) {
		target.add(element);
	}

}
