package net.rl86.mdh.loot.predicates;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

@ResourceName("minecraft:alternative")
public class AlternativesLootPredicate extends AbstractLootPredicate {

	public AlternativesLootPredicate(String name) {
		super(name);
		terms = new ArrayList<>();
	}
	
	private ArrayList<AbstractLootPredicate> terms;

	@Override
	public JsonObject generateJson() {
		return null;
	}

	@Override
	protected void addDialogContentToBox(VBox source) {
		Text label = new Text();
		label.setFont(CommonUtilities.getFont(FontType.TEXT));
		label.setText("Alternatives");
		
		ListView<AbstractLootPredicate> list = new ListView<>();
		
		for(AbstractLootPredicate p : terms) {
			list.getItems().add(p);
		}
		
		Button edit = new Button();
		edit.setFont(CommonUtilities.getFont(FontType.TEXT));
		edit.setText("Edit Selected Entry");
		edit.setOnAction(e -> {
			AbstractLootPredicate sel = list.getSelectionModel().getSelectedItem();
			if(sel != null) {
				sel.buildDialog().showAndWait();
			} else {
				new Alert(AlertType.WARNING, "Please select an item first.", ButtonType.YES).showAndWait();
			}
		});
		
		Button newTerm = new Button();
		newTerm.setFont(CommonUtilities.getFont(FontType.TEXT));
		newTerm.setText("Add Predicate");
		newTerm.setOnAction(e -> {
			new Alert(AlertType.WARNING, "This functionality has not yet been implemented.", ButtonType.YES).showAndWait();
		});
		
		Button del = new Button();
		del.setFont(CommonUtilities.getFont(FontType.TEXT));
		del.setText("Delete Selected Entry");
		del.setOnAction(e -> {
			AbstractLootPredicate sel = list.getSelectionModel().getSelectedItem();
			if(sel != null) {
				list.getItems().remove(sel);
			} else {
				new Alert(AlertType.WARNING, "Please select an item first.", ButtonType.YES).showAndWait();
			}
		});
		
		source.getChildren().addAll(label, list, edit, newTerm, del);
	}

	@Override
	protected void applyChangesToSelf(VBox dialogData) {
		
	}

}
