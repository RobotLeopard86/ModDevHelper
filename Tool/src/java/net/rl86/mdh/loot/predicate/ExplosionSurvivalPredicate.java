package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UnusableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:survives_explosion")
@UnusableIn({LootType.BLOCK})
public class ExplosionSurvivalPredicate extends AbstractLootPredicate {

	public ExplosionSurvivalPredicate(String name) {
		super(name);
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Survived Explosion Predicate");

		Text display = new Text();
		display.setFont(CommonUtilities.getFont(FontType.TEXT));
		display.setText("No configurable properties exist for this type.");

		root.getChildren().addAll(header, display);
	}

	@Override
	protected JsonObject generateJsonData() {
		return new JsonObject();
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

}
