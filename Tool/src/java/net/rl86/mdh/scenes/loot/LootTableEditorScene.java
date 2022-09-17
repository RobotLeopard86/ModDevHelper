package net.rl86.mdh.scenes.loot;

import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.text.Text;
import net.rl86.mdh.Main;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
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
		
		Tab myTab = new Tab();
		myTab.setText("Loot Table");
		myTab.setClosable(false);
		
		Button btn = new Button("SIZE?");
		btn.setFont(CommonUtilities.getFont(FontType.TEXT));
		btn.setOnAction(e -> {
			System.out.println("The scene is " + scene.getWidth() + "x" + scene.getHeight() + ". The stage is " + Main.getStage().getWidth() + "x" + Main.getStage().getHeight());
		});
		
		tabs.getTabs().add(myTab);
		tabs.getSelectionModel().select(myTab);
		
		root.getChildren().addAll(header, btn, tabs);
	}

	@Override
	protected void setDimensions() {
		CustomSize size = this.getClass().getAnnotation(CustomSize.class);
		windowWidth = size.width();
		windowHeight = size.height();
	}

}
