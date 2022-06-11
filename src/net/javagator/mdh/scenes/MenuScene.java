package net.javagator.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class MenuScene extends SceneRetriever {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Home";
		
		Text header = new Text();
		header.setText("Mod Development Helper");
		header.setFont(Main.headerFont);
		
		Text todo = new Text();
		todo.setText("Please select an option:");
		todo.setFont(Main.textFont);

		Button lang = new Button();
		lang.setFont(Main.textFont);
		lang.setText("Localization Entry");
		lang.setOnAction(e -> {
			Main.switchScene("lang");
		});
		
		Button imodel = new Button();
		imodel.setFont(Main.textFont);
		imodel.setText("Item Model Designer");
		imodel.setOnAction(e -> {
			Main.switchScene("items");
		});
		
		Button states = new Button();
		states.setFont(Main.textFont);
		states.setText("Blockstate File Creator");
		states.setOnAction(e -> {
			Main.switchScene("bs1");
		});
		
		Button craft = new Button();
		craft.setFont(Main.textFont);
		craft.setText("Recipe Generator");
		craft.setOnAction(e -> {
			Main.switchScene("recipe");
		});
		
		Button quit = new Button();
		quit.setFont(Main.textFont);
		quit.setText("Quit App");
		quit.setOnAction(e -> {
			System.exit(0);
		});
		
		root.getChildren().addAll(header, todo, lang, imodel, states, craft, quit);
	}

}
