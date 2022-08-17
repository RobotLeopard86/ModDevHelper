package net.rl86.mdh.loot.predicates;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ItemPredicate {

	private static class Enchantment {
		
		private String enchantmentId;
		
		private int minLevel;
		private int maxLevel = -314159;

		public Enchantment(String id, int minLevel) {
			this.enchantmentId = id;
			if(minLevel < 1) throw new IllegalArgumentException("Minimum level must be at least 1!");
			this.minLevel = minLevel;
		}
		
		public Enchantment(String id, int minLevel, int maxLevel) {
			this(id, minLevel);
			if(maxLevel < minLevel || maxLevel < 1) throw new IllegalArgumentException("Maximum level must be at least 1 and greater than the minimum level!");
			this.maxLevel = maxLevel;
		}
		
		public JsonObject output() {
			JsonObject jsonRoot = new JsonObject();
			jsonRoot.addProperty("enchantment", enchantmentId);
			
			JsonObject levels = new JsonObject();
			levels.addProperty("min", minLevel);
			if(maxLevel != -314159) levels.addProperty("max", maxLevel);
			
			jsonRoot.add("levels", levels);
			
			return jsonRoot;
		}

	}
	
	private int countMin;
	private int countMax;
	
	private int durabilityMin;
	private int durabilityMax;
	
	private Enchantment[] enchantments;
	
	private String[] acceptableItems;
	
	private String nbt;
	private String potionType;
	private String tag;
	
	private ItemPredicate(int c_min, int c_max, int d_min, int d_max, Enchantment[] enchants, String[] items, String nbt_data, String potion, String tagLoc) {
		countMin = c_min;
		countMax = c_max;
		durabilityMin = d_min;
		durabilityMax = d_max;
		enchantments = enchants;
		acceptableItems = items;
		nbt = nbt_data;
		potionType = potion;
		tag = tagLoc;
	}
	
	public JsonObject makeJson() {
		JsonObject root = new JsonObject();
		
		JsonObject count = new JsonObject();
		count.addProperty("min", countMin);
		if(countMax != -314159) count.addProperty("max", countMax);
		root.add("count", count);
		
		JsonObject d = new JsonObject();
		d.addProperty("min", durabilityMin);
		if(durabilityMax != -314159) count.addProperty("max", durabilityMax);
		root.add("durability", d);
		
		JsonArray enchants = new JsonArray();
		for(Enchantment e : enchantments) {
			enchants.add(e.output());
		}
		root.add("enchantments", enchants);
		
		JsonArray items = new JsonArray();
		for(String s : acceptableItems) {
			items.add(s);
		}
		root.add("items", items);
		
		root.addProperty("nbt", nbt);
		root.addProperty("potion", potionType);
		root.addProperty("tag", tag);
		
		return root;
	}
	
}
