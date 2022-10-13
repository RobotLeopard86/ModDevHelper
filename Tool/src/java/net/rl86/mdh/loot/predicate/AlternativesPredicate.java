package net.rl86.mdh.loot.predicate;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;

@IngameIdentifier("minecraft:alternative")
public class AlternativesPredicate extends AbstractLootPredicate {

	private ArrayList<AbstractLootPredicate> predicates;

	public AlternativesPredicate(String name, AbstractLootPredicate... predicates) {
		super(name);
		this.predicates = new ArrayList<>();
		for(AbstractLootPredicate p : predicates) {
			this.predicates.add(p);
		}
	}

	@Override
	protected void generateTabContent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		JsonArray children = new JsonArray();
		for(AbstractLootPredicate p : predicates) {
			children.add(p.generateJson());
		}
		return root;
	}

	@Override
	public LootMember[] getChildren() {
		return predicates.toArray(new LootMember[0]);
	}

}
