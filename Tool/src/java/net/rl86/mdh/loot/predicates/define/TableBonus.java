package net.rl86.mdh.loot.predicates.define;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK}, id = "minecraft:table_bonus")
public class TableBonus implements IPredicate {
	
	public String enchantment;
	public Number[] table;

	public TableBonus(String enchantment, Number[] table) {
		this.enchantment = enchantment;
		this.table = table;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		JsonPrimitive enchant = new JsonPrimitive(enchantment);
		JsonArray array = new JsonArray(2);
		for(Number num : table) {
			array.add(num);
		}
		return new PrefNameJsonElement[] {new PrefNameJsonElement("enchantment", enchant)
		, new PrefNameJsonElement("chances", array)};
	}

}
