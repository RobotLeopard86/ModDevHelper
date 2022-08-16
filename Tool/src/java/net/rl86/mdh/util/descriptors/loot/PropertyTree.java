package net.rl86.mdh.util.descriptors.loot;

import java.util.ArrayList;
import java.util.HashMap;

import net.rl86.mdh.util.descriptors.loot.LootEnumerations.LootPropertyType;

public class PropertyTree {
	
	private ArrayList<String> numberProperties;
	private ArrayList<String> stringProperties;
	private ArrayList<String> booleanProperties;
	private ArrayList<String> arrayProperties;
	private HashMap<String, PropertyTree> objectProperties;
	private ArrayList<Class<? extends DependentPropertyType>> dependencies;

	public PropertyTree() {
		numberProperties = new ArrayList<>();
		stringProperties = new ArrayList<>();
		booleanProperties = new ArrayList<>();
		arrayProperties = new ArrayList<>();
		objectProperties = new HashMap<>();
		dependencies = new ArrayList<>();
	}
	
	public PropertyTree addProperty(String id, LootPropertyType type) {
		switch(type) {
		case NUMBER:
			numberProperties.add(id);
			break;
		case STRING:
			stringProperties.add(id);
			break;
		case BOOLEAN:
			booleanProperties.add(id);
			break;
		case ARRAY:
			arrayProperties.add(id);
			break;
		case OBJECT:
			throw new IllegalArgumentException("Adding object requires a format to be provided!");
		}
		return this;
	}
	
	public PropertyTree addProperty(String id, PropertyTree format) {
		objectProperties.put(id, format);
		return this;
	}
	
	public PropertyTree addDependentlyNamedProperty(String context, LootPropertyType type) {
		switch(type) {
		case NUMBER:
			numberProperties.add("<replace>" + context);
			break;
		case STRING:
			stringProperties.add("<replace>" + context);
			break;
		case BOOLEAN:
			booleanProperties.add("<replace>" + context);
			break;
		case ARRAY:
			arrayProperties.add("<replace>" + context);
			break;
		case OBJECT:
			throw new IllegalArgumentException("Adding object requires a format to be provided!");
		}
		return this;
	}
	
	public PropertyTree addDependentlyNamedProperty(String context, PropertyTree format) {
		objectProperties.put("<replace>" + context, format);
		return this;
	}
	
	public PropertyTree addDependentMarker(Class<? extends DependentPropertyType> type) {
		dependencies.add(type);
		return this;
	}
}
