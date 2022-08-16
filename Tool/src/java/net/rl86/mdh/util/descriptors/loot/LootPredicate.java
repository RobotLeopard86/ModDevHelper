package net.rl86.mdh.util.descriptors.loot;

import net.rl86.mdh.util.descriptors.loot.LootEnumerations.LootPropertyType;
import net.rl86.mdh.util.descriptors.loot.LootEnumerations.PredicateType;

public class LootPredicate {
	
	public static final PropertyTree format = new PropertyTree().addProperty("condition", LootPropertyType.STRING).addDependentMarker(PredicateType.class);

	public LootPredicate() {
		// TODO Auto-generated constructor stub
	}

}
