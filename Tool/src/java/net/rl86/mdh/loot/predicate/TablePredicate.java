package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:table_bonus")
@UsableIn({LootType.BLOCK, LootType.ENTITY})
public class TablePredicate extends AbstractLootPredicate {
	
	private SimpleStringProperty rloc;
	private ObservableList<Double> chances;

	public TablePredicate(String name) {
		super(name);
		rloc = new SimpleStringProperty();
		chances = FXCollections.observableArrayList();
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Enchantment Bonus Predicate");
		
		TextField enchantment = new TextField();
		enchantment.setFont(CommonUtilities.getFont(FontType.TEXT));
		enchantment.setPromptText("Enter enchantment ID...");
		enchantment.setText(rloc.get());
		enchantment.textProperty().bindBidirectional(rloc);
		
		Text howtoread = new Text();
		howtoread.setFont(CommonUtilities.getFont(FontType.TEXT));
		howtoread.setText("Enchantment levels are read top-to-bottom. Top entry is not having the enchantment, second entry is level 1, and so on.");

		ListView<Double> list = new ListView<>();
		list.setItems(chances);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.setEditable(true);
		list.setCellFactory(TextFieldListCell.forListView(new DoubleStringConverter()));
		
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
			list.edit(list.getSelectionModel().getSelectedIndex());
		});
		
		Button delLevel = new Button();
		delLevel.setFont(CommonUtilities.getFont(FontType.TEXT));
		delLevel.setText("Remove Selected Enchantment Level");
		delLevel.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
		delLevel.setOnAction(e -> {
			chances.remove(list.getSelectionModel().getSelectedIndex());
		});
		
		root.getChildren().addAll(header, enchantment, howtoread, list, addLevel, edit, delLevel);
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
