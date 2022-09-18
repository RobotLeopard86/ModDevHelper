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
import net.rl86.mdh.util.CustomSize;

@CustomSize(width = 1280, height = 720)
public class LootTableEditorScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Loot Table Creator";
		
		Text header = new Text();
		header.setText("Loot Table Editor");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		TabPane tabs = new TabPane();
		tabs.setSide(Side.TOP);
		tabs.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		tabs.setTabDragPolicy(TabDragPolicy.FIXED);
		
		tabs.getTabs().add(new LootTable(LootType.CHEST).generateTab());
		
		root.getChildren().addAll(header, tabs);
	}

	@Override
	protected void setDimensions() {
		CustomSize size = this.getClass().getAnnotation(CustomSize.class);
		windowWidth = size.width();
		windowHeight = size.height();
	}

}
