package net.rl86.mdh.loot.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.rl86.mdh.loot.predicate.AbstractLootPredicate;
import net.rl86.mdh.loot.predicate.AlternativesPredicate;
import net.rl86.mdh.loot.predicate.BlockStatePredicate;
import net.rl86.mdh.scenes.loot.LootTableEditorScene;
import net.rl86.mdh.util.CommonUtilities;

public class LootDialogs {
	
	public static enum PredicateType {
		Alternative("Alternatives"),
		BlockState("Block State"),
		DamageSource("Damage Source"),
		EntityProps("Entity Properties"),
		EntityScores("Entity Scores"),
		Inverse("Inverse"),
		KilledByPlayer("Killed By Player"),
		Location("Entity Location"),
		ToolMatch("Tool Match"),
		Random("Random Chance"),
		RandomLooting("Random Chance With Looting"),
		Reference("Reference"),
		SurviveExplosion("Survived Explosion"),
		TableBonus("Enchantment Bonus"),
		Time("Time Check"),
		Value("Value Check"),
		Weather("Weather Check");
		
		
		private String displayName;

		private PredicateType(String name) {
			displayName = name;
		}

		@Override
		public String toString() {
			return displayName;
		}
	}

	private LootDialogs() {}
	
	public static Dialog<LootMember> generateComponentsDialog(){
		VBox layout = new VBox();
		layout.setSpacing(10d);
		
		TreeItem<LootMember> treeRoot = new TreeItem<>();
		treeRoot.setValue(LootTableEditorScene.table);
		
		assembleTree(treeRoot);
		
		TreeView<LootMember> treeview = new TreeView<>();
		treeview.setRoot(treeRoot);
		treeview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		layout.getChildren().add(treeview);
		
		DialogPane pane = new DialogPane();
		pane.setContent(layout);
		pane.setMinWidth(800);
		pane.setMinHeight(500);
		pane.getButtonTypes().addAll(ButtonType.OK);
		
		Dialog<LootMember> dialog = new Dialog<>();
		dialog.setTitle("Select Component");
		dialog.setGraphic(null);
		dialog.setHeaderText("Select Component");
		dialog.setDialogPane(pane);
		dialog.setOnCloseRequest(e -> {
			boolean shouldConsume = true;
			
			if(treeview.getSelectionModel().getSelectedItem() != null) {
				dialog.setResult(treeview.getSelectionModel().getSelectedItem().getValue());
				shouldConsume = false;
			} else {
				error("You must select a value!");
			}
			
			if(shouldConsume) {
				e.consume();
			}
		});
		
		return dialog;
	}
	
	public static AbstractLootPredicate addPredicate() {
		PredicateType type = addPredicateDialog().showAndWait().get();
		
		String name = getItemName();
		
		switch(type) {
		case Alternative:
			return new AlternativesPredicate(name);
		case BlockState:
			return new BlockStatePredicate(name);
		case DamageSource:
			break;
		case EntityProps:
			break;
		case EntityScores:
			break;
		case Inverse:
			break;
		case KilledByPlayer:
			break;
		case Location:
			break;
		case Random:
			break;
		case RandomLooting:
			break;
		case Reference:
			break;
		case SurviveExplosion:
			break;
		case TableBonus:
			break;
		case Time:
			break;
		case ToolMatch:
			break;
		case Value:
			break;
		case Weather:
			break;
		default:
			break;
		}
		
		return null;
	}
	
	private static String getItemName() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input Request");
		dialog.setGraphic(null);
		dialog.setHeaderText("Input Request");
		dialog.getEditor().setPromptText("Enter name...");
		dialog.setOnCloseRequest(e -> {
			if(dialog.getEditor().getText() == "") {
				e.consume();
				error("You must specify a value!");
			}
		});
		
		return dialog.showAndWait().get();
	}
	
	private static ChoiceDialog<PredicateType> addPredicateDialog(){
		ChoiceDialog<PredicateType> dialog = new ChoiceDialog<>();
		dialog.setTitle("Add Predicate");
		dialog.setGraphic(null);
		dialog.setHeaderText("Add Predicate");
		dialog.getItems().addAll(PredicateType.values());
		dialog.setSelectedItem(PredicateType.Alternative);
		dialog.setOnCloseRequest(event -> {
			boolean shouldConsume = true;

			if(dialog.getResult() != null) {
				shouldConsume = false;
			} else {
				error("You must select an option! :(");
			}

			if(shouldConsume) {
				event.consume();
			}
		});
		
		return dialog;
	}
	
	private static void assembleTree(TreeItem<LootMember> root) {
		if(root.getValue().getChildren() == null) {
			return;
		}
		for(LootMember member : root.getValue().getChildren()) {
			TreeItem<LootMember> item = new TreeItem<>();
			item.setValue(member);
			root.getChildren().add(item);
			assembleTree(item);
		}
	}
	
	private static void error(String description) {
		Alert msg = new Alert(AlertType.ERROR, description, ButtonType.OK);
		msg.setTitle("Error");
		ImageView graphic = new ImageView();
		graphic.setImage(CommonUtilities.getErrorImg());
		msg.setGraphic(graphic);
		msg.show();
	}

}