package net.rl86.mdh.loot.predicates.define;

import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK, LootType.ENTITY, LootType.CHEST}, id = "minecraft:reference")
public class Ref implements IPredicate {
	
	public String loc;

	public Ref(String location) {
		this.loc = location;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		JsonPrimitive primitive = new JsonPrimitive(loc);
		return new PrefNameJsonElement[] {new PrefNameJsonElement("name", primitive)};
	}

}
