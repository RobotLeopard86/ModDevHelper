package net.rl86.mdh.loot.predicate;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.LootMemberType;
import net.rl86.mdh.loot.util.LootMemberType.MemberType;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

@LootMemberType(MemberType.PREDICATE)
public abstract class AbstractLootPredicate extends LootMember {

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
	public final VBox generateView() {
		super.generateView();
		
		Button make = new Button();
		make.setFont(CommonUtilities.getFont(FontType.TEXT));
		make.setText("Make Me");
		make.setOnAction(e -> {
			System.out.println(generateJson().toString());
		});
		
		root.getChildren().add(make);
		
		return root;
	}

	protected abstract void generateEditorContent();
	
	public abstract LootMember[] getChildren();

	protected abstract JsonObject generateJsonData();

}
