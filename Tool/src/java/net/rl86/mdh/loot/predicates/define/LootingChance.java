package net.rl86.mdh.loot.predicates.define;

import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.ENTITY}, id = "minecraft:random_chance_with_looting")
public class LootingChance extends RandomChance {
	
	public float multiplier;

	public LootingChance(float chance, float lootingMultiplier) {
		super(chance);
		multiplier = lootingMultiplier;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		JsonPrimitive chance = new JsonPrimitive(percentage / 100);
		JsonPrimitive mult = new JsonPrimitive(multiplier);
		return new PrefNameJsonElement[]{new PrefNameJsonElement("chance", chance), new PrefNameJsonElement("looting_multiplier", mult)};
	}
	
}
