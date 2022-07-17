package net.javagator.mdh.util;

import com.google.gson.JsonObject;

import net.javagator.mdh.util.CommonUtilities.Direction;

public class TextureAtPosDescriptor {
	
	private String resourceLocation;
	private Direction dir;
	
	public TextureAtPosDescriptor(String loc, Direction direction) {
		resourceLocation = loc;
		dir = direction;
	}
	
	public void addToJson(JsonObject json) {
		json.addProperty(dir.mappedVal, resourceLocation);
	}
}