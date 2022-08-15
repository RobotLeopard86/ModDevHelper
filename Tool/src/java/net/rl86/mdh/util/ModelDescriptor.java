package net.rl86.mdh.util;

import java.io.File;

import com.google.gson.JsonObject;

public class ModelDescriptor {
	
	private File model;
	private int rotx;
	private int roty;
	private int rotz;

	public ModelDescriptor(File modelFile, int xaxis, int yaxis, int zaxis) {
		if(xaxis < 0 || xaxis > 359 || yaxis < 0 || yaxis > 359 || zaxis < 0 || zaxis > 359) {
			throw new IllegalArgumentException("Rotation cannot be lower than 0 or higher than 359!");
		} else {
			model = modelFile;
			rotx = xaxis;
			roty = yaxis;
			rotz = zaxis;
		}
	}
	
	public JsonObject constructJson(String modId) {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("model", modId + ":block/" + model.getName().substring(0, model.getName().length() - 5));
		obj.addProperty("x", rotx);
		obj.addProperty("y", roty);
		obj.addProperty("z", rotz);
		
		return obj;
	}

}