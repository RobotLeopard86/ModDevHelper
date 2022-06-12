package net.javagator.mdh.scenes.recipes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.RecipesScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class SmithingScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Smithing Table Recipe Generator";
		
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Smithing Recipes");
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(RecipesScene.class.getName());
		});
		
		Text idExample = new Text();
		idExample.setFont(CommonUtilities.getFont(FontType.TEXT));
		idExample.setText("Example of an item ID:\nminecraft:stone");
		
		TextField baseField = new TextField();
		baseField.setFont(CommonUtilities.getFont(FontType.TEXT));
		baseField.setPromptText("Enter base item ID here...");
		
		TextField additionField = new TextField();
		additionField.setFont(CommonUtilities.getFont(FontType.TEXT));
		additionField.setPromptText("Enter additional item ID here...");
		
		TextField resultField = new TextField();
		resultField.setFont(CommonUtilities.getFont(FontType.TEXT));
		resultField.setPromptText("Enter result item ID here...");
		
		Button finish = new Button();
		finish.setFont(CommonUtilities.getFont(FontType.TEXT));
		finish.setText("Generate Recipe");
		finish.setOnAction(e -> {
			JsonObject jsonRoot = new JsonObject();
			jsonRoot.addProperty("type", "minecraft:smithing");
			
			JsonObject item = new JsonObject();
			item.addProperty("item", baseField.getText());
			jsonRoot.add("base", item);
			
			JsonObject add = new JsonObject();
			add.addProperty("item", additionField.getText());
			jsonRoot.add("addition", add);
			
			jsonRoot.addProperty("result", resultField.getText());
			
			String json = Main.getGson().toJson(jsonRoot);
			
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			File fullPath = fc.showSaveDialog(Main.getStage());
			if(!fullPath.exists()) {
				try {
					fullPath.createNewFile();
				} catch (IOException exception) {
					exception.printStackTrace();
					error("Couldn't create file as it already exists! Please try again. :(\nFile: " + fullPath.getAbsolutePath());
					return;
				}
			}
			
			try {
				PrintWriter writer = new PrintWriter(fullPath);
				writer.print(json);
				writer.flush();
				writer.close();
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
				error("Couldn't find file to write to! :(\nFile: " + fullPath.getAbsolutePath());
			}
			
			baseField.setText("");
			additionField.setText("");
			resultField.setText("");
			
			Main.switchScene(MenuScene.class.getName());
			success("Successfully wrote to recipe file!");
		});
		
		root.getChildren().addAll(header, exit, idExample, baseField, additionField, resultField, finish);
	}

}
