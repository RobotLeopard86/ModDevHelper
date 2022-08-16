package net.rl86.mdh.util.descriptors.loot;

public final class LootEnumerations {

	private LootEnumerations() {}
	
	public static enum LootPropertyType {
		NUMBER,
		STRING,
		BOOLEAN,
		ARRAY,
		OBJECT
	}
	
	public static enum PredicateType implements DependentPropertyType {
		Alternatives("alternative", new PropertyTree().addProperty("terms", LootPredicate.format)),
		BlockState("block_state_property", new PropertyTree[]{
			new PropertyTree().addProperty("block", LootPropertyType.STRING),
			new PropertyTree().addProperty("block", LootPropertyType.STRING).addProperty("properties", 
				new PropertyTree().addDependentlyNamedProperty("Enter blockstate name...", LootPropertyType.STRING)
			),
			new PropertyTree().addProperty("block", LootPropertyType.STRING).addProperty("properties", 
				new PropertyTree().addDependentlyNamedProperty("Enter blockstate name...", 
						new PropertyTree().addProperty("min", LootPropertyType.NUMBER).addProperty("max", LootPropertyType.NUMBER)
				)
			),
		}),
		DamageSourceProperties("damage_source_properties"),
		EntityProperties("entity_properties"),
		EntityScores("entity_scores"),
		Invert("inverted"),
		PlayerKill("killed_by_player"),
		Location("location_check"),
		Tool("match_tool"),
		Chance("random_chance"),
		ChanceWithLooting("random_chance_with_looting"),
		PredicateFile("reference"),
		SurvivesExplosion("survives_explosion"),
		Enchantment("table_bonus"),
		TimeCheck("time_check"),
		ValueCheck("value_check"),
		WeatherCheck("weather_check");
		
		private String id;
		private PropertyTree[] format;
		
		private PredicateType(String name, PropertyTree[] fields) {
			id = name;
			format = fields;
		}
		
		private PredicateType(String name, PropertyTree fields) {
			this(name, new PropertyTree[] {fields});
		}
		
		public String getId() {
			return id;
		}
		
		public PropertyTree[] getFormat() {
			return format;
		}
	}

}
