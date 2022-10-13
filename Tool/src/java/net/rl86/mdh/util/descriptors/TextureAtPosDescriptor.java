package net.rl86.mdh.util.descriptors;

import com.google.gson.JsonObject;

import net.rl86.mdh.util.CommonUtilities.Direction;

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

	public String getResourceLocation() {
		return resourceLocation;
	}

	public Direction getDir() {
		return dir;
	}
}