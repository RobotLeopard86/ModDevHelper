package net.rl86.mdh.loot;

import java.util.ArrayList;

import net.rl86.mdh.util.CommonUtilities.LootType;

public class LootTable {
	
	private LootType type;
	
	private ArrayList<Pool> pools;

	public LootTable(LootType type) {
		this.type = type;
		pools = new ArrayList<>();
	}
	
	public void addPool(Pool pool) {
		pools.add(pool);
	}
	
	public void deletePool(int idx) {
		pools.remove(idx);
	}
	
	public Pool getPool(int idx) {
		return pools.get(idx);
	}

}
