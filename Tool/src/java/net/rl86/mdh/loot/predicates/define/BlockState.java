package net.rl86.mdh.loot.predicates.define;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK}, id = "minecraft:block_state_property")
public class BlockState implements IPredicate {
	
	public String blockId;
	public String definitiveState;
	public String stateValue;

	public BlockState(String id, String state, String val) {
		this.blockId = id;
		this.definitiveState = state;
		this.stateValue = val;
	}
	
	public BlockState(String id) {
		this(id, "", "");
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		JsonPrimitive block = new JsonPrimitive(blockId);
		if(!((definitiveState.equalsIgnoreCase("")) || (stateValue.equalsIgnoreCase("")))) {
			JsonPrimitive state = new JsonPrimitive(stateValue);
			JsonObject obj = new JsonObject();
			new PrefNameJsonElement(definitiveState, state).addTo(obj);
			return new PrefNameJsonElement[] {new PrefNameJsonElement("block", block)
			, new PrefNameJsonElement("properties", obj)};
		} else {
			return new PrefNameJsonElement[] {new PrefNameJsonElement("block", block)};
		}
	}

}
