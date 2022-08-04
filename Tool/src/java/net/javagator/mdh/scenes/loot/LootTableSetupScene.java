package net.javagator.mdh.scenes.loot;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.MenuScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class LootTableSetupScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Loot Table Creator";
		
		Text header = new Text();
		header.setText("Loot Tables");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(MenuScene.class.getName());
		});
		
		TextField numPools = new TextField();
		numPools.setFont(CommonUtilities.getFont(FontType.TEXT));
		numPools.setPromptText("Enter number of pools here...");
		
		root.getChildren().addAll(header, exit, numPools);
	}

}
