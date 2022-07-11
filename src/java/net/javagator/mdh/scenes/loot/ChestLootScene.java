package net.javagator.mdh.scenes.loot;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.LootTablesMenuScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class ChestLootScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Chest Loot Tables";
		
		Text header = new Text();
		header.setText("Chest Loot Tables");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(LootTablesMenuScene.class.getName());
		});
		
		
		
		root.getChildren().addAll(header, exit);
	}

}
