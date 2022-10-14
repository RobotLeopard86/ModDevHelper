package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import net.rl86.mdh.loot.util.UnusableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@UnusableIn({LootType.BLOCK, LootType.CHEST})
public class RandomLootingPredicate extends RandomPredicate {
	
	private SimpleFloatProperty multiplier;

	public RandomLootingPredicate(String name) {
		super(name);
		multiplier = new SimpleFloatProperty();
	}

	@Override
	protected void generateTabContent() {
		super.generateTabContent();
		
		((Text) root.getChildren().get(0)).setText("Random Chance With Looting Predicate");
		
		TextField inputMultiplier = new TextField();
		inputMultiplier.setPromptText("Enter looting multiplier...");
		inputMultiplier.setText(String.valueOf(multiplier.get()));
		inputMultiplier.textProperty().bindBidirectional(multiplier, new NumberStringConverter());
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
