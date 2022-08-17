package net.rl86.mdh.loot.predicates.define;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK}, id = "minecraft:survives_explosion")
public class Survival implements IPredicate {

	public Survival() {}

	@Override
	public PrefNameJsonElement[] makeJson() {
		return new PrefNameJsonElement[] {};
	}

}
