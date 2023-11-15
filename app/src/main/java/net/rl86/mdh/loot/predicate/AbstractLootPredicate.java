package net.rl86.mdh.loot.predicate;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.LootMemberType;
import net.rl86.mdh.loot.util.LootMemberType.MemberType;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

@LootMemberType(MemberType.PREDICATE)
public abstract class AbstractLootPredicate extends LootMember {
	
	private Tab tab;

	public AbstractLootPredicate(String name) {
		super(name);
		tab = null;
	}

	@Override
	public final JsonObject generateJson() {
		JsonObject root = new JsonObject();
		root.addProperty("condition", this.getClass().getAnnotation(IngameIdentifier.class).value());
		JsonObject data = generateJsonData();
		Set<Entry<String, JsonElement>> set = data.entrySet();
		for(Entry<String, JsonElement> entry : set) {
			root.add(entry.getKey(), entry.getValue());
		}
		return root;
	}
	
	@Override
	public final Tab generateTab() {
		if(tab == null) {
			Tab generated = super.generateTab();
			
			Button make = new Button();
			make.setFont(CommonUtilities.getFont(FontType.TEXT));
			make.setText("Make Me");
			make.setOnAction(e -> {
				System.out.println(generateJson().toString());
			});
			
			((VBox) generated.getContent()).getChildren().add(make);
			
			tab = generated;
		}
		
		return tab;
	}

	protected abstract void generateTabContent();
	
	public abstract LootMember[] getChildren();

	protected abstract JsonObject generateJsonData();

}
