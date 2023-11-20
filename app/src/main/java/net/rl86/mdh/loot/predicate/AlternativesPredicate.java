package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootDialogs;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:alternative")
@UsableIn(LootType.ALL)
public class AlternativesPredicate extends AbstractLootPredicate {
	
	private ObservableList<AbstractLootPredicate> predicates;

	public AlternativesPredicate() {
		super();
		predicates = FXCollections.observableArrayList();
	}

	@Override
	protected void generateEditorContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Alternatives Predicate");

		ListView<AbstractLootPredicate> list = new ListView<>();
		list.setItems(predicates);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		Button rm = new Button();
		rm.setText("Remove Selected Predicate");
		rm.setFont(CommonUtilities.getFont(FontType.TEXT));
		rm.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
		rm.setOnAction(e -> {
			AbstractLootPredicate sel = list.getSelectionModel().getSelectedItem();
			predicates.remove(sel);
		});
		
		Button add = new Button();
		add.setText("Add Predicate");
		add.setFont(CommonUtilities.getFont(FontType.TEXT));
		add.setOnAction(e -> {
			predicates.add(LootDialogs.addPredicate());
		});

		root.getChildren().addAll(header, list, add, rm);
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		JsonArray children = new JsonArray();
		for(AbstractLootPredicate p : predicates) {
			children.add(p.generateJson());
		}
		root.add("terms", children);
		return root;
	}

	@Override
	public LootMember[] getChildren() {
		return predicates.toArray(new LootMember[0]);
	}

}
