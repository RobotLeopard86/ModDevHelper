package net.rl86.mdh.loot.predicates.define;

import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK, LootType.ENTITY, LootType.CHEST}, id = "minecraft:random_chance")
public class RandomChance implements IPredicate {
	
	public float percentage;

	public RandomChance(float chance) {
		this.percentage = chance;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		JsonPrimitive json = new JsonPrimitive(percentage / 100);
		return new PrefNameJsonElement[]{new PrefNameJsonElement("chance", json)};
	}

}
