package net.rl86.mdh.util.loot;

public class Pool {
	
	public int rolls;

	public Pool(int rolls) {
		if(rolls > 0) {
			this.rolls = rolls;
		} else {
			throw new IllegalArgumentException("Pool must have at least 1 roll.");
		}
	}

}