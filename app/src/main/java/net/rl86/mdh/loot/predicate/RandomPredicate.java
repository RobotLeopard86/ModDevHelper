package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;
import net.rl86.mdh.util.NumberField;

@IngameIdentifier("minecraft:random_chance")
@UsableIn({LootType.ALL})
public class RandomPredicate extends AbstractLootPredicate {
	
	private SimpleDoubleProperty chance;

	public RandomPredicate() {
		super();
		chance = new SimpleDoubleProperty();
	}

	@Override
	protected void generateEditorContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Random Chance Predicate");
		
		NumberField field = new NumberField(chance.get());
		field.setPromptText("Enter chance...");
		field.setText(String.valueOf(chance.get()));
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
