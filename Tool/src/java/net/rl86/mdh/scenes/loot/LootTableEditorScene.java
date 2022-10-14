package net.rl86.mdh.scenes.loot;

import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.misc.LootTable;
import net.rl86.mdh.loot.predicate.AlternativesPredicate;
import net.rl86.mdh.loot.util.LootDialogs;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

public class LootTableEditorScene extends BaseScene {

	public static LootType tableTypeDataReceiver;
	
	public static LootTable table;

	public static TabPane tabs;

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
			Dialog<LootMember> choice = LootDialogs.generateComponentsDialog();
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
		tabs.getTabs().add(new AlternativesPredicate("Sussy Baka").generateTab());
	}

	@Override
	protected void setDimensions() {
		windowWidth = 1280;
		windowHeight = 720;
	}

}
