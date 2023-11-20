package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@UsableIn({LootType.BLOCK, LootType.ENTITY})
@IngameIdentifier("minecraft:match_tool")
public class ItemCheckPredicate extends AbstractLootPredicate {

	public ItemCheckPredicate() {
		super();
	}

	@Override
	protected void generateEditorContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Item Check Predicate");
		
		
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

	@Override
	protected JsonObject generateJsonData() {
		return null;
	}

}
