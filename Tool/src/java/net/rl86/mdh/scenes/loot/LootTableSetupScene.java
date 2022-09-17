package net.rl86.mdh.scenes.loot;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import net.rl86.mdh.Main;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

public class LootTableSetupScene extends BaseScene {

	@Override
	public void buildScene() {
		warnOnExit = false;
		sceneTitle = "Mod Development Helper | Loot Table Creator";
		
		Text header = new Text();
		header.setText("Loot Tables");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Text typeDesc = new Text();
		typeDesc.setFont(CommonUtilities.getFont(FontType.TEXT));
		typeDesc.setText("What purpose is this loot table intended for?");
		
		ChoiceBox<LootType> type = new ChoiceBox<>();
		type.getItems().addAll(LootType.BLOCK, LootType.ENTITY, LootType.CHEST);
		type.setValue(LootType.BLOCK);
		type.setPrefSize(windowWidth / 3, windowHeight / 15);
		
		Button make = new Button();
		make.setFont(CommonUtilities.getFont(FontType.TEXT));
		make.setText("Create Loot Table");
		make.setOnAction(e -> {
			Main.switchScene(LootTableEditorScene.class.getName());
		});
		
		root.getChildren().addAll(header, typeDesc, type, make);
	}

}
