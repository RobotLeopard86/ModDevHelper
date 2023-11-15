package net.rl86.mdh.loot.util;

import com.google.gson.JsonObject;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public abstract class LootMember {

	protected VBox root;

	protected String name;

	public abstract JsonObject generateJson();

	protected abstract void generateTabContent();

	public Tab generateTab() {
		generateTabContent();
		Tab tab = new Tab();
		tab.setText(toString());
		tab.setContent(root);
		tab.setClosable(true);
		return tab;
	}

	public LootMember(String name) {
		this.name = name;
		root = new VBox();
		root.setSpacing(15);
	}
	
	public abstract LootMember[] getChildren();
	
	@Override
	public String toString() {
		return name + " (" + this.getClass().getAnnotation(LootMemberType.class).value() + ")";
	}

}
