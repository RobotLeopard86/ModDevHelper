package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;

@IngameIdentifier("minecraft:block_state_property")
public class BlockStatePredicate extends AbstractLootPredicate {

	private String id;
	private String blockstateProperty;
	private JsonPrimitive blockstateValue;
	private double valueMin;
	private double valueMax;

	public BlockStatePredicate(String name, String blockID) {
		super(name);
		this.id = blockID;
	}
	
	public BlockStatePredicate(String name, String blockID, String blockstateName, JsonPrimitive value) {
		super(name);
		this.id = blockID;
		this.blockstateProperty = blockstateName;
		this.blockstateValue = value;
	}
	
	public BlockStatePredicate(String name, String blockID, String blockstateName, double min, double max) {
		super(name);
		this.id = blockID;
		this.blockstateProperty = blockstateName;
		this.valueMin = min;
		this.valueMax = max;
	}

	@Override
	protected void generateTabContent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		
		return root;
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

}
