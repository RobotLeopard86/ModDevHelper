package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:killed_by_player")
@UsableIn({LootType.ENTITY})
public class KillerPlayerPredicate extends AbstractLootPredicate {

	public KillerPlayerPredicate(String name) {
		super(name);
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Killed By Player Predicate");

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
