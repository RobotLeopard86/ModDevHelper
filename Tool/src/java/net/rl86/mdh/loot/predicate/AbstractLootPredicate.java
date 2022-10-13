package net.rl86.mdh.loot.predicate;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.LootMemberType;
import net.rl86.mdh.loot.util.LootMemberType.MemberType;

@LootMemberType(MemberType.PREDICATE)
public abstract class AbstractLootPredicate extends LootMember {

	public AbstractLootPredicate(String name) {
		super(name);
	}

	@Override
	public final JsonObject generateJson() {
		JsonObject root = new JsonObject();
		root.addProperty("condition", this.getClass().getAnnotation(IngameIdentifier.class).value());
		JsonObject data = generateJsonData();
		Set<Entry<String, JsonElement>> set = data.entrySet();
		for(Entry<String, JsonElement> entry : set) {
			root.add(entry.getKey(), entry.getValue());
		}
		return root;
	}

	
	protected abstract void generateTabContent();
	
	public abstract LootMember[] getChildren();

	protected abstract JsonObject generateJsonData();
;
}
