package net.javagator.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.itemmodels.ItemModelFromBlockModelScene;
import net.javagator.mdh.scenes.itemmodels.ItemModelFromTextureScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class ItemModelScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Item Model Designer";
		
		Text header = new Text();
		header.setText("Item Models");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		ToggleGroup options = new ToggleGroup();
		
		RadioButton texture = new RadioButton();
		texture.setFont(CommonUtilities.getFont(FontType.TEXT));
		texture.setText("Texture");
		texture.setToggleGroup(options);
		
		RadioButton model = new RadioButton();
		model.setFont(CommonUtilities.getFont(FontType.TEXT));
		model.setText("Block Model");
		model.setToggleGroup(options);
		
		Button next = new Button();
		next.setFont(CommonUtilities.getFont(FontType.TEXT));
		next.setText("Continue");
		next.setOnAction(e -> {
			Main.switchScene(options.getSelectedToggle().equals((Toggle)texture) ? ItemModelFromTextureScene.class.getName() : ItemModelFromBlockModelScene.class.getName());
		});
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(MenuScene.class.getName());
		});
		
		options.selectToggle(texture);
		
		root.getChildren().addAll(header, exit, texture, model, next);
	}

}
