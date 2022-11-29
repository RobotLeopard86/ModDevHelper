package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;
import net.rl86.mdh.util.NumberField;

@IngameIdentifier("minecraft:random_chance_with_looting")
@UsableIn({LootType.ENTITY})
public class RandomLootingPredicate extends RandomPredicate {
	
	private SimpleDoubleProperty multiplier;

	public RandomLootingPredicate(String name) {
		super(name);
		multiplier = new SimpleDoubleProperty();
	}

	@Override
	protected void generateTabContent() {
		super.generateTabContent();
		
		((Text) root.getChildren().get(0)).setText("Random Chance With Looting Predicate");
		
		NumberField inputMultiplier = new NumberField(multiplier.get());
		inputMultiplier.setPromptText("Enter looting multiplier...");
		inputMultiplier.setText(String.valueOf(multiplier.get()));
		inputMultiplier.setFont(CommonUtilities.getFont(FontType.TEXT));

		root.getChildren().addAll(inputMultiplier);
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = super.generateJsonData();
		root.addProperty("looting_multiplier", multiplier.get());
		return root;
	}
	
	

}
