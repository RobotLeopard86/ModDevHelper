package net.rl86.mdh.loot.util;

import com.google.gson.JsonObject;

import javafx.scene.layout.VBox;

public abstract class LootMember {

	protected VBox root;

	public abstract JsonObject generateJson();

	protected abstract void generateEditorContent();

	public VBox generateView() {
		root.getChildren().clear();
		generateEditorContent();
		return root;
	}

	public LootMember() {
		root = new VBox();
		root.setSpacing(15);
	}
	
	public abstract LootMember[] getChildren();
	
	@Override
	public String toString() {
		return this.getClass().getAnnotation(LootMemberType.class).value().toString();
	}

}
