package net.rl86.mdh.loot.define;

import static net.rl86.mdh.loot.util.LootMemberType.MemberType.POOL;

import com.google.gson.JsonObject;

import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.LootMemberType;

@LootMemberType(POOL)
public class Pool extends LootMember {
	
	public int rolls;

	public Pool(String name, int rolls) {
		super(name);
		if(rolls > 0) {
			this.rolls = rolls;
		} else {
			throw new IllegalArgumentException("Pool must have at least 1 roll.");
		}
	}

	@Override
	public JsonObject generateJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateTabContent() {
		
	}

}
