package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;
import net.rl86.mdh.util.NumberInputDialog;

@IngameIdentifier("minecraft:table_bonus")
@UsableIn({LootType.BLOCK, LootType.ENTITY})
public class TablePredicate extends AbstractLootPredicate {
	
	private SimpleStringProperty rloc;
	private ObservableList<Double> chances;
	private ObservableList<String> presentation;

	public TablePredicate() {
		super();
		rloc = new SimpleStringProperty();
		chances = FXCollections.observableArrayList();
		presentation = FXCollections.observableArrayList();
		
		chances.addListener(new ListChangeListener<Double>() {
			@Override
			public void onChanged(Change<? extends Double> change) {
				presentation.clear();
				for(int i = 0; i < chances.size(); i++) {
					presentation.add(i == 0 ? "Without Enchantment: " + chances.get(i) : "Level " + i + ": " + chances.get(i));
				}
			}
		});
	}

	@Override
	protected void generateEditorContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Enchantment Bonus Predicate");
		
		TextField enchantment = new TextField();
		enchantment.setFont(CommonUtilities.getFont(FontType.TEXT));
		enchantment.setPromptText("Enter enchantment ID...");
		enchantment.setText(rloc.get());
		enchantment.textProperty().bindBidirectional(rloc);
		
		ListView<String> list = new ListView<>();
		list.setItems(presentation);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		Button addLevel = new Button();
		addLevel.setFont(CommonUtilities.getFont(FontType.TEXT));
		addLevel.setText("Add Enchantment Level");
		addLevel.setOnAction(e -> {
			chances.add(0d);
		});
		
		Button edit = new Button();
		edit.setFont(CommonUtilities.getFont(FontType.TEXT));
		edit.setText("Edit Selected Enchantment Level");
		edit.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
		edit.setOnAction(e -> {
			NumberInputDialog numIn = new NumberInputDialog();
			numIn.setGraphic(null);
			numIn.setHeaderText("Enter New Value");
			numIn.setTitle("Input Request");
			numIn.getEditor().setPromptText("Enter new value...");
			Double newVal = numIn.showAndWait().get();
			chances.set(list.getSelectionModel().getSelectedIndex(), newVal);
		});
		
		Button delLevel = new Button();
		delLevel.setFont(CommonUtilities.getFont(FontType.TEXT));
		delLevel.setText("Remove Selected Enchantment Level");
		delLevel.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
		delLevel.setOnAction(e -> {
			chances.remove(list.getSelectionModel().getSelectedIndex());
		});
		
		root.getChildren().addAll(header, enchantment, list, addLevel, edit, delLevel);
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		root.addProperty("enchantment", rloc.get());
		JsonArray table = new JsonArray();
		chances.forEach(e -> {
			table.add(e.doubleValue());
		});
		root.add("chances", table);
		return root;
	}

}
