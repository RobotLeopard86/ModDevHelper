package net.javagator.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class RecipesScene extends SceneRetriever {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Recipe Generator";
		
		Text header = new Text();
		header.setText("Recipes");
		header.setFont(Main.headerFont);
		
		ToggleGroup options = new ToggleGroup();
		
		RadioButton cshape = new RadioButton();
		cshape.setFont(Main.textFont);
		cshape.setText("Crafting Table - Shaped");
		cshape.setToggleGroup(options);
		cshape.getProperties().put("continue_to", "shaped");
		
		RadioButton csl = new RadioButton();
		csl.setFont(Main.textFont);
		csl.setText("Crafting Table - Shapeless");
		csl.setToggleGroup(options);
		csl.getProperties().put("continue_to", "sl");
		
		RadioButton blast = new RadioButton();
		blast.setFont(Main.textFont);
		blast.setText("Blast Furnace");
		blast.setToggleGroup(options);
		blast.getProperties().put("continue_to", "blaster");
		
		RadioButton camp = new RadioButton();
		camp.setFont(Main.textFont);
		camp.setText("Campfire Cooking");
		camp.setToggleGroup(options);
		camp.getProperties().put("continue_to", "campfire");
		
		RadioButton smelt = new RadioButton();
		smelt.setFont(Main.textFont);
		smelt.setText("Furnace");
		smelt.setToggleGroup(options);
		smelt.getProperties().put("continue_to", "smelting");
		
		RadioButton smith = new RadioButton();
		smith.setFont(Main.textFont);
		smith.setText("Smithing Table");
		smith.setToggleGroup(options);
		smith.getProperties().put("continue_to", "st");
		
		RadioButton smoked = new RadioButton();
		smoked.setFont(Main.textFont);
		smoked.setText("Smoker");
		smoked.setToggleGroup(options);
		smoked.getProperties().put("continue_to", "smoker");
		
		RadioButton cut = new RadioButton();
		cut.setFont(Main.textFont);
		cut.setText("Stonecutter");
		cut.setToggleGroup(options);
		cut.getProperties().put("continue_to", "scut");
		
		Button next = new Button();
		next.setFont(Main.textFont);
		next.setText("Continue");
		next.setOnAction(e -> {
			Main.switchScene(options.getSelectedToggle().getProperties().get("continue_to").toString());
		});
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("menu");
		});
		
		options.selectToggle(cshape);
		
		root.getChildren().addAll(header, exit, cshape, csl, blast, camp, smelt, smith, smoked, cut, next);
	}

}
