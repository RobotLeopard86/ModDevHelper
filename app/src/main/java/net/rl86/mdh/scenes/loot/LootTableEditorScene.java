package net.rl86.mdh.scenes.loot;

import javafx.geometry.Orientation;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
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
	
	public static LootTable table;
	
	private static HBox viewport;

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Loot Table Creator";

		Text header = new Text();
		header.setText("Loot Table Editor");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		viewport = new HBox(5);
		viewport.getChildren().addAll(new VBox(), new VBox());
		
		Separator editorDivider = new Separator(Orientation.HORIZONTAL);

		root.getChildren().addAll(header, editorDivider, viewport);
	}

	@Override
	public void updatePageOnSceneLoad() {
		table = new LootTable(tableTypeDataReceiver);
		
		refreshTree();
	}
	
	public static void refreshTree() {
		TreeItem<LootMember> treeRoot = new TreeItem<>();
		treeRoot.setValue(table);
		
		assembleTree(treeRoot);
		
		TreeView<LootMember> treeview = new TreeView<>();
		treeview.setRoot(treeRoot);
		treeview.setMinHeight(575);
		treeview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		treeview.setOnMouseClicked(e -> {
			TreeItem<LootMember> member = treeview.getSelectionModel().getSelectedItem();
			viewport.getChildren().set(1, (member == null ? new VBox() : member.getValue().generateView()));
		});
		
		expandTreeView(treeRoot);
		
		viewport.getChildren().set(0, treeview);
	}

	@Override
	protected void setDimensions() {
		windowWidth = 1280;
		windowHeight = 720;
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
	
	private static void expandTreeView(TreeItem<?> item){
	    if(item != null && !item.isLeaf()){
	        item.setExpanded(true);
	        for(TreeItem<?> child:item.getChildren()){
	            expandTreeView(child);
	        }
	    }
	}

}
