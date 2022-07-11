package net.javagator.mdh.scenes.loot.block;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.LootTablesMenuScene;
import net.javagator.mdh.scenes.loot.EntityLootScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class BlockLootScene extends BaseScene {
	
	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Block Loot Tables";
		
		Text header = new Text();
		header.setText("Block Loot Tables");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(LootTablesMenuScene.class.getName());
		});
		
		ToggleGroup options = new ToggleGroup();
		
		RadioButton item = new RadioButton();
		item.setFont(CommonUtilities.getFont(FontType.TEXT));
		item.setText("Single Item");
		item.setToggleGroup(options);
		item.getProperties().put("continue_to", SingleItemBlockLootScene.class.getName());
		
		RadioButton conditional = new RadioButton();
		conditional.setFont(CommonUtilities.getFont(FontType.TEXT));
		conditional.setText("Conditional Item");
		conditional.setToggleGroup(options);
		conditional.getProperties().put("continue_to", EntityLootScene.class.getName());
		
		options.selectToggle(item);
		
		Button next = new Button();
		next.setFont(CommonUtilities.getFont(FontType.TEXT));
		next.setText("Continue");
		next.setOnAction(e -> {
			Main.switchScene(options.getSelectedToggle().getProperties().get("continue_to").toString());
		});
		
		root.getChildren().addAll(header, exit, item, conditional, next);
	}

}
