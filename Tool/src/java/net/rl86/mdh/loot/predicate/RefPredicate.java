package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:reference")
@UsableIn({LootType.ALL})
public class RefPredicate extends AbstractLootPredicate {
	
	private SimpleStringProperty rloc;

	public RefPredicate(String name) {
		super(name);
		rloc = new SimpleStringProperty();
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Reference Predicate");
		
		TextField field = new TextField();
		field.setPromptText("Enter reference location...");
		field.setText(rloc.get());
		field.textProperty().bindBidirectional(rloc);
		field.setFont(CommonUtilities.getFont(FontType.TEXT));

		root.getChildren().addAll(header, field);
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		root.addProperty("name", rloc.get());
		return root;
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

}
