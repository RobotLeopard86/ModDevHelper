package net.javagator.mdh.scenes.recipes;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class ShapedCraftingScene extends SceneRetriever {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Shaped Crafting Recipe Generator";
		
		Text header = new Text();
		header.setFont(Main.textFont);
		header.setText("Shaped Crafting Recipes");
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("menu");
		});
		
		ImageView grid = new ImageView();
		grid.setImage(new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/resources/images/crafting_table_grid.png", 100, 100, true, true, true));
		
		root.getChildren().addAll(header, exit, grid);
	}

}
