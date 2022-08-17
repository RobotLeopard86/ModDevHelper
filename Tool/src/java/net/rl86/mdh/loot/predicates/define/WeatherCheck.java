package net.rl86.mdh.loot.predicates.define;

import com.google.gson.JsonPrimitive;

import net.rl86.mdh.loot.predicates.IPredicate;
import net.rl86.mdh.loot.predicates.PredicateData;
import net.rl86.mdh.loot.predicates.PrefNameJsonElement;
import net.rl86.mdh.util.CommonUtilities.LootType;

@PredicateData(availableTo = {LootType.BLOCK, LootType.ENTITY, LootType.CHEST}, id = "minecraft:weather_check")
public class WeatherCheck implements IPredicate {
	
	public static enum Weather {
		RAIN("raining"),
		THUNDER("thundering");
		
		private String name;
		
		private Weather(String id) {
			id = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private Weather[] type;

	public WeatherCheck(Weather... weather) {
		if(weather.length > 2) {
			throw new IllegalArgumentException("That is too many arguments!");
		}
		this.type = weather;
	}

	@Override
	public PrefNameJsonElement[] makeJson() {
		if(type.length == 0) {
			return new PrefNameJsonElement[] {new PrefNameJsonElement("raining", new JsonPrimitive(false))
			, new PrefNameJsonElement("thundering", new JsonPrimitive(false))};
		} else if(type.length == 1) {
			if(type[0] == Weather.RAIN) {
				return new PrefNameJsonElement[] {new PrefNameJsonElement("raining", new JsonPrimitive(true))
				, new PrefNameJsonElement("thundering", new JsonPrimitive(false))};
			} else {
				return new PrefNameJsonElement[] {new PrefNameJsonElement("raining", new JsonPrimitive(false))
				, new PrefNameJsonElement("thundering", new JsonPrimitive(true))};
			}
		} else {
			return new PrefNameJsonElement[] {new PrefNameJsonElement("raining", new JsonPrimitive(true))
			, new PrefNameJsonElement("thundering", new JsonPrimitive(true))};
		}
	}

}
