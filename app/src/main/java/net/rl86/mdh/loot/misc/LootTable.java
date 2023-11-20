package net.rl86.mdh.loot.misc;

import static net.rl86.mdh.loot.util.LootMemberType.MemberType.TABLE;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.LootMemberType;
import net.rl86.mdh.scenes.loot.LootTableEditorScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@LootMemberType(TABLE)
public class LootTable extends LootMember {

	private LootType type;
	
	private SimpleStringProperty randSeq;

	private ArrayList<Pool> pools;

	public LootTable(LootType type) {
		this.type = type;
		randSeq = new SimpleStringProperty();
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
		obj.addProperty("random_sequence", randSeq.getValue());

		JsonArray poolsInJson = new JsonArray();
		for(Pool p : pools) {
			poolsInJson.add(p.generateJson());
		}

		obj.add("pools", poolsInJson);

		return obj;
	}

	@Override
	protected void generateEditorContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Loot Table");

		Text typeDisplay = new Text();
		typeDisplay.setFont(CommonUtilities.getFont(FontType.TEXT));
		typeDisplay.setText("Loot Table Type: " + type.toString());
		
		Text rsf_inst = new Text();
		rsf_inst.setFont(CommonUtilities.getFont(FontType.TEXT));
		rsf_inst.setText("Random Sequence - Format: (mod id):/" + type.getInGameID().substring(10, type.getInGameID().length()) + "/(loot table name)");
		
		TextField rsf = new TextField();
		rsf.setFont(CommonUtilities.getFont(FontType.TEXT));
		rsf.setPromptText("(mod id):/" + type.getInGameID().substring(10, type.getInGameID().length()) + "/(loot table name)");
		rsf.setMinWidth(400);
		rsf.setText(randSeq.get());
		rsf.textProperty().bindBidirectional(randSeq);
		
		Button addPool = new Button();
		addPool.setText("Add Pool");
		addPool.setFont(CommonUtilities.getFont(FontType.TEXT));
		addPool.setOnAction(e -> {
			addPool(new Pool(1));
			LootTableEditorScene.refreshTree();
		});

		root.getChildren().addAll(header, typeDisplay, rsf_inst, rsf, addPool);
	}

	@Override
	public LootMember[] getChildren() {
		return pools.toArray(new LootMember[0]);
	}
	
	public LootType getType() {
		return type;
	}

}
