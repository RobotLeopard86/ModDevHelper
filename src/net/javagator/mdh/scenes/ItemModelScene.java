package net.javagator.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class ItemModelScene extends SceneRetriever {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Item Model Designer";
		
		Text header = new Text();
		header.setText("Item Models");
		header.setFont(Main.headerFont);
		
		ToggleGroup options = new ToggleGroup();
		
		RadioButton texture = new RadioButton();
		texture.setFont(Main.textFont);
		texture.setText("Texture");
		texture.setToggleGroup(options);
		
		RadioButton model = new RadioButton();
		model.setFont(Main.textFont);
		model.setText("Block Model");
		model.setToggleGroup(options);
		
		Button next = new Button();
		next.setFont(Main.textFont);
		next.setText("Continue");
		next.setOnAction(e -> {
			Main.switchScene(options.getSelectedToggle().equals((Toggle)texture) ? "itexture" : "iblock");
		});
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("menu");
		});
		
		options.selectToggle(texture);
		
		root.getChildren().addAll(header, exit, texture, model, next);
	}

}
