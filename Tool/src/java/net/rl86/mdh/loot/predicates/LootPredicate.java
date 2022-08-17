package net.rl86.mdh.loot.predicates;

import com.google.gson.JsonObject;

public class LootPredicate<P extends IPredicate> {
	
	private P predicate;
	
	public LootPredicate(P predicate) {
		this.predicate = predicate;
	}
	
	public P getPredicate() {
		return predicate;
	}
	
	public JsonObject get() {
		JsonObject json = new JsonObject();
		json.addProperty("condition", predicate.getClass().getAnnotation(PredicateData.class).id());
		PrefNameJsonElement[] predicateJson = predicate.makeJson();
		for(PrefNameJsonElement element : predicateJson) {
			element.addTo(json);
		}
		return json;
	}

}
