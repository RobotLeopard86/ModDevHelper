package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootDialogs;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.scenes.loot.LootTableEditorScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:inverted")
@UsableIn({LootType.ALL})
public class InvertPredicate extends AbstractLootPredicate {
	
	private AbstractLootPredicate predicate;
	private SimpleBooleanProperty predicateChosen;

	public InvertPredicate(String name) {
		super(name);
		predicateChosen = new SimpleBooleanProperty();
		predicateChosen.set(false);
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Inverse Predicate");

		Text display = new Text();
		display.setFont(CommonUtilities.getFont(FontType.TEXT));
		display.setText("Current Predicate: <none>");
		
		Button open = new Button();
		open.setText("Edit Predicate");
		open.setFont(CommonUtilities.getFont(FontType.TEXT));
		open.disableProperty().bind(predicateChosen.not());
		open.setOnAction(e -> {
			LootTableEditorScene.tabs.getTabs().add(predicate.generateTab());
		});
		
		Button change = new Button();
		change.setText("Change Predicate");
		change.setFont(CommonUtilities.getFont(FontType.TEXT));
		change.setOnAction(e -> {
			predicate = LootDialogs.addPredicate();
			predicateChosen.set(true);
			display.setText("Current Predicate: " + predicate.toString());
		});

		root.getChildren().addAll(header, display, change, open);
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		root.add("term", predicate.generateJson());
		return root;
	}

	@Override
	public LootMember[] getChildren() {
		return new LootMember[] {predicate};
	}

}
