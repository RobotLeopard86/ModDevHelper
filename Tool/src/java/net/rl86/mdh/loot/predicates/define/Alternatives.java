package net.rl86.mdh.loot.predicates.define;

import com.google.gson.JsonArray;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.LootPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK, LootType.ENTITY, LootType.CHEST}, id = "minecraft:alternative")
public class Alternatives implements IPredicate {
	
	public LootPredicate<?>[] terms;

	public Alternatives(LootPredicate<?>[] terms) {
		this.terms = terms;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		JsonArray array = new JsonArray();
		for(int i = 0; i < terms.length; i++) {
			PrefNameJsonElement[] childJson = terms[i].getPredicate().makeJson();
			for(int j = 0; j < childJson.length; j++) {
				childJson[j].addTo(array);
			}
		}
		return new PrefNameJsonElement[]{new PrefNameJsonElement("terms", array)};
	}

}
