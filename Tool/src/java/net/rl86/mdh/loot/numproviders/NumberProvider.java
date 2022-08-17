package net.rl86.mdh.loot.numproviders;

import com.google.gson.JsonObject;

import net.rl86.mdh.loot.predicates.PrefNameJsonElement;

public class NumberProvider<N extends INumProvider> {

	private N provider;
	
	public NumberProvider(N provider) {
		this.provider = provider;
	}
	
	public N getProvider() {
		return provider;
	}
	
	public JsonObject get() {
		JsonObject json = new JsonObject();
		json.addProperty("type", provider.getClass().getAnnotation(NumberProviderData.class).id());
		PrefNameJsonElement[] providerJson = provider.makeJson();
		for(PrefNameJsonElement element : providerJson) {
			element.addTo(json);
		}
		return json;
	}

}
