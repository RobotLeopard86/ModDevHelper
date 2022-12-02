package net.rl86.mdh.loot.util;

import java.util.ArrayList;
import java.util.Arrays;

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
import net.rl86.mdh.loot.predicate.ExplosionSurvivalPredicate;
import net.rl86.mdh.loot.predicate.InvertPredicate;
import net.rl86.mdh.loot.predicate.KillerPlayerPredicate;
import net.rl86.mdh.loot.predicate.RandomLootingPredicate;
import net.rl86.mdh.loot.predicate.RandomPredicate;
import net.rl86.mdh.loot.predicate.RefPredicate;
import net.rl86.mdh.loot.predicate.TablePredicate;
import net.rl86.mdh.loot.predicate.TimePredicate;
import net.rl86.mdh.loot.predicate.WeatherPredicate;
import net.rl86.mdh.scenes.loot.LootTableEditorScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.LootType;

public class LootDialogs {
	
	public static enum PredicateType {
		Alternative("Alternatives", AlternativesPredicate.class),
		BlockState("Block State", BlockStatePredicate.class),
		DamageSource("Damage Source", null),
		EntityProps("Entity Properties", null),
		EntityScores("Entity Scores", null),
		Inverse("Inverse", InvertPredicate.class),
		KilledByPlayer("Killed By Player", KillerPlayerPredicate.class),
		Location("Entity Location", null),
		ToolMatch("Tool Match", null),
		Random("Random Chance", RandomPredicate.class),
		RandomLooting("Random Chance With Looting", RandomLootingPredicate.class),
		Reference("Reference", RefPredicate.class),
		SurviveExplosion("Survived Explosion", ExplosionSurvivalPredicate.class),
		TableBonus("Enchantment Bonus", TablePredicate.class),
		Time("Time Check", TimePredicate.class),
		Weather("Weather Check", WeatherPredicate.class);
		
		
		private String displayName;
		private Class<? extends AbstractLootPredicate> clazz;

		private PredicateType(String name, Class<? extends AbstractLootPredicate> clazz) {
			displayName = name;
			this.clazz = clazz;
		}

		@Override
		public String toString() {
			return displayName;
		}
		
		public Class<? extends AbstractLootPredicate> getClazz() {
			return clazz;
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
			return new InvertPredicate(name);
		case KilledByPlayer:
			return new KillerPlayerPredicate(name);
		case Location:
			break;
		case Random:
			return new RandomPredicate(name);
		case RandomLooting:
			return new RandomLootingPredicate(name);
		case Reference:
			return new RefPredicate(name);
		case SurviveExplosion:
			return new ExplosionSurvivalPredicate(name);
		case TableBonus:
			return new TablePredicate(name);
		case Time:
			return new TimePredicate(name);
		case ToolMatch:
			break;
		case Weather:
			return new WeatherPredicate(name);
		default:
			break;
		}
		
		return null;
	}
	
	private static String getItemName() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input Request");
		dialog.setGraphic(null);
		dialog.setHeaderText("Enter Name");
		dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
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
		
		ArrayList<PredicateType> types = new ArrayList<>(Arrays.asList(PredicateType.values()));
		for(PredicateType pt : PredicateType.values()) {
			boolean notOk = true;
			UsableIn usable = null;
			
			if(pt.clazz == null) {
				notOk = true;
				types.remove(pt);
				continue;
			}
			
			if(pt.clazz.isAnnotationPresent(UsableIn.class)) {
				usable = pt.clazz.getAnnotation(UsableIn.class);
			}
			
			if(usable == null) {
				notOk = true;
			} else {
				ArrayList<LootType> usableIn = new ArrayList<>(Arrays.asList(usable.value()));
				notOk = !usableIn.contains(LootTableEditorScene.table.getType());
				
				if(usableIn.contains(LootType.ALL)) {
					notOk = false;
				}
			}
			
			if(notOk) {
				types.remove(pt);
			}
		}
		
		dialog.getItems().addAll(types);
		dialog.setSelectedItem(PredicateType.Alternative);
		dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
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