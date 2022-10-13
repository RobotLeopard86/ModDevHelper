package net.rl86.mdh.scenes.loot;

import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.misc.LootTable;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

public class LootTableEditorScene extends BaseScene {

	public static LootType tableTypeDataReceiver;
	
	private LootTable table;

	private TabPane tabs;

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Loot Table Creator";

		Text header = new Text();
		header.setText("Loot Table Editor");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Button components = new Button();
		components.setText("Edit Component");
		components.setFont(CommonUtilities.getFont(FontType.TEXT));
		components.setOnAction(e -> {
			Dialog<LootMember> choice = generateComponentsDialog();
			LootMember chosen = choice.showAndWait().get();
			tabs.getTabs().add(chosen.generateTab());
		});

		tabs = new TabPane();
		tabs.setSide(Side.TOP);
		tabs.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		tabs.setTabDragPolicy(TabDragPolicy.FIXED);

		root.getChildren().addAll(header, components, tabs);
	}

	@Override
	public void updatePageOnSceneLoad() {
		table = new LootTable(tableTypeDataReceiver);
		tabs.getTabs().add(table.generateTab());
	}

	@Override
	protected void setDimensions() {
		windowWidth = 1280;
		windowHeight = 720;
	}
	
	private Dialog<LootMember> generateComponentsDialog(){
		VBox layout = new VBox();
		layout.setSpacing(10d);
		
		TreeItem<LootMember> treeRoot = new TreeItem<>();
		treeRoot.setValue(table);
		
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
	
	private void assembleTree(TreeItem<LootMember> root) {
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

}
