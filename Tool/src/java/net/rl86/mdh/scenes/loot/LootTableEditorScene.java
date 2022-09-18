package net.rl86.mdh.scenes.loot;

import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.define.LootTable;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

public class LootTableEditorScene extends BaseScene {
	
	public static LootType tableTypeDataReceiver;
	
	private TabPane tabs;

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Loot Table Creator";
		
		Text header = new Text();
		header.setText("Loot Table Editor");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		tabs = new TabPane();
		tabs.setSide(Side.TOP);
		tabs.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		tabs.setTabDragPolicy(TabDragPolicy.FIXED);
		
		root.getChildren().addAll(header, tabs);
	}

	@Override
	public void updatePageOnSceneLoad() {
		tabs.getTabs().add(new LootTable(tableTypeDataReceiver).generateTab());
	}

	@Override
	protected void setDimensions() {
		windowWidth = 1280;
		windowHeight = 720;
	}

}
