package net.rl86.mdh.loot.predicates.define;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.LootPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK, LootType.ENTITY, LootType.CHEST}, id = "minecraft:inverted")
public class Invert implements IPredicate {
	
	public LootPredicate<?> term;

	public Invert(LootPredicate<?> term) {
		this.term = term;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		return term.getPredicate().makeJson();
	}

}
