package net.javagator.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.loot.ChestLootScene;
import net.javagator.mdh.scenes.loot.EntityLootScene;
import net.javagator.mdh.scenes.loot.block.BlockLootScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class LootTablesMenuScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Loot Tables";
		
		Text header = new Text();
		header.setText("Loot Tables");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		ToggleGroup options = new ToggleGroup();
		
		RadioButton block = new RadioButton();
		block.setFont(CommonUtilities.getFont(FontType.TEXT));
		block.setText("Blocks");
		block.setToggleGroup(options);
		block.getProperties().put("continue_to", BlockLootScene.class.getName());
		
		RadioButton entity = new RadioButton();
		entity.setFont(CommonUtilities.getFont(FontType.TEXT));
		entity.setText("Entities");
		entity.setToggleGroup(options);
		entity.getProperties().put("continue_to", EntityLootScene.class.getName());
		
		RadioButton chest = new RadioButton();
		chest.setFont(CommonUtilities.getFont(FontType.TEXT));
		chest.setText("Loot Chests");
		chest.setToggleGroup(options);
		chest.getProperties().put("continue_to", ChestLootScene.class.getName());
		
		Button next = new Button();
		next.setFont(CommonUtilities.getFont(FontType.TEXT));
		next.setText("Continue");
		next.setOnAction(e -> {
			Main.switchScene(options.getSelectedToggle().getProperties().get("continue_to").toString());
		});
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(MenuScene.class.getName());
		});
		
		options.selectToggle(block);
		
		root.getChildren().addAll(header, exit, block, entity, chest, next);
	}

}
