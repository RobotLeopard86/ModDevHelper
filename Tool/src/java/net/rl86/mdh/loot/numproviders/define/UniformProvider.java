package net.rl86.mdh.loot.numproviders.define;

import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.numproviders.INumProvider;
import net.rl86.mdh.loot.numproviders.NumberProviderData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;

@NumberProviderData(id = "minecraft:uniform")
public class UniformProvider implements INumProvider {
	
	private Number minimum;
	private Number maximum;

	public UniformProvider(Number min, Number max) {
		this.minimum = min;
		this.maximum = max;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		return new PrefNameJsonElement[] {new PrefNameJsonElement("min", new JsonPrimitive(minimum)),
		new PrefNameJsonElement("max", new JsonPrimitive(maximum))};
	}

}
