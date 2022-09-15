package net.rl86.mdh.loot.predicates;

public enum PredicateType {
	ALTERNATIVES("Alternatives (minecraft:alternative)", AlternativesLootPredicate.class);
	
	public Class<?> predicateClass;
	private String display;
	
	private PredicateType(String displayName, Class<?> predicate) {
		predicateClass = predicate;
		display = displayName;
	}
	
	public String toString() {
		return display;
	}
}
