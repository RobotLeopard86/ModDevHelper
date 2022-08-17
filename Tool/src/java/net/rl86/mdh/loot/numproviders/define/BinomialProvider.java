package net.rl86.mdh.loot.numproviders.define;

import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.numproviders.INumProvider;
import net.rl86.mdh.loot.numproviders.NumberProviderData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;

@NumberProviderData(id = "minecraft:binomial")
public class BinomialProvider implements INumProvider {
	
	private int trials;
	private float chance;

	public BinomialProvider(int numTrials, float successChancePerTrial) {
		this.trials = numTrials;
		this.chance = successChancePerTrial;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		return new PrefNameJsonElement[] {new PrefNameJsonElement("n", new JsonPrimitive(trials)),
		new PrefNameJsonElement("p", new JsonPrimitive(chance))};
	}

}
