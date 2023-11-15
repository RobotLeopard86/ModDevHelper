package net.rl86.mdh.loot.misc;

import static net.rl86.mdh.loot.util.LootMemberType.MemberType.TABLE;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.LootMemberType;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@LootMemberType(TABLE)
public class LootTable extends LootMember {

	private LootType type;

	private ArrayList<Pool> pools;

	public LootTable(LootType type) {
		super("Loot Table");
		this.type = type;
		pools = new ArrayList<>();
	}

	public void addPool(Pool pool) {
		pools.add(pool);
	}

	public void deletePool(int idx) {
		pools.remove(idx);
	}

	public Pool getPool(int idx) {
		return pools.get(idx);
	}

	@Override
	public JsonObject generateJson() {
		JsonObject obj = new JsonObject();

		obj.addProperty("type", type.getInGameID());

		JsonArray poolsInJson = new JsonArray();
		for(Pool p : pools) {
			poolsInJson.add(p.generateJson());
		}

		obj.add("pools", poolsInJson);

		return obj;
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Loot Table");

		Text typeDisplay = new Text();
		typeDisplay.setFont(CommonUtilities.getFont(FontType.TEXT));
		typeDisplay.setText("Loot Table Type: " + type.toString());

		root.getChildren().addAll(header, typeDisplay);
	}

	@Override
	public LootMember[] getChildren() {
		return pools.toArray(new LootMember[0]);
	}
	
	public LootType getType() {
		return type;
	}

}
