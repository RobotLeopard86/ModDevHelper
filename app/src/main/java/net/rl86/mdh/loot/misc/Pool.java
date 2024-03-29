package net.rl86.mdh.loot.misc;

import static net.rl86.mdh.loot.util.LootMemberType.MemberType.POOL;

import com.google.gson.JsonObject;

import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.LootMemberType;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

@LootMemberType(POOL)
public class Pool extends LootMember {

	public int rolls;

	public Pool(int rolls) {
		super();
		if(rolls > 0) {
			this.rolls = rolls;
		} else {
			throw new IllegalArgumentException("Pool must have at least 1 roll.");
		}
	}

	@Override
	public JsonObject generateJson() {
		return null;
	}

	@Override
	protected void generateEditorContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Pool");
		
		root.getChildren().add(header);
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

}
