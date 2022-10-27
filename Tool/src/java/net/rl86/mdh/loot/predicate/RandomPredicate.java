package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:random_chance")
@UsableIn({LootType.ALL})
public class RandomPredicate extends AbstractLootPredicate {
	
	private SimpleFloatProperty chance;

	public RandomPredicate(String name) {
		super(name);
		chance = new SimpleFloatProperty();
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Random Chance Predicate");
		
		TextField field = new TextField();
		field.setPromptText("Enter chance...");
		field.setText(String.valueOf(chance.get()));
		field.textProperty().bindBidirectional(chance, new NumberStringConverter());
		field.setFont(CommonUtilities.getFont(FontType.TEXT));

		root.getChildren().addAll(header, field);
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		root.addProperty("chance", chance.get());
		return root;
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

}
