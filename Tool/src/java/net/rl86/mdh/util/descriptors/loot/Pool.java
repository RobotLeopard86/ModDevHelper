package net.rl86.mdh.util.descriptors.loot;

public class Pool {
	
	private int rolls;

	public Pool(int rolls) {
		if(rolls > 0) {
			this.rolls = rolls;
		} else {
			throw new IllegalArgumentException("Pool must have at least 1 roll.");
		}
	}

}
