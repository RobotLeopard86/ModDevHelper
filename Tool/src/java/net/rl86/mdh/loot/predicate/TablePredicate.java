package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

@IngameIdentifier("minecraft:table_bonus")
@UsableIn({LootType.BLOCK, LootType.ENTITY})
public class TablePredicate extends AbstractLootPredicate {
	
	private SimpleStringProperty rloc;
	private ObservableList<Integer> chances;

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
		enchantment.setText(rloc.get());
		enchantment.textProperty().bindBidirectional(rloc);

		ListView<Integer> list = new ListView<>();
		list.setItems(chances);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.setEditable(true);
		
		root.getChildren().addAll(header, enchantment, list);
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
