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
import net.javagator.mdh.SceneRetriever;

public class SmithingScene extends SceneRetriever {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Smithing Table Recipe Generator";
		
		Text header = new Text();
		header.setFont(Main.headerFont);
		header.setText("Smithing Recipes");
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("recipe");
		});
		
		Text idExample = new Text();
		idExample.setFont(Main.textFont);
		idExample.setText("Example of an item ID:\nminecraft:stone");
		
		TextField baseField = new TextField();
		baseField.setFont(Main.textFont);
		baseField.setPromptText("Enter base item ID here...");
		
		TextField additionField = new TextField();
		additionField.setFont(Main.textFont);
		additionField.setPromptText("Enter additional item ID here...");
		
		TextField resultField = new TextField();
		resultField.setFont(Main.textFont);
		resultField.setPromptText("Enter result item ID here...");
		
		Button finish = new Button();
		finish.setFont(Main.textFont);
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
					Main.error("Couldn't create file as it already exists! Please try again. :(\nFile: " + fullPath.getAbsolutePath());
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
				Main.error("Couldn't find file to write to! :(\nFile: " + fullPath.getAbsolutePath());
			}
			
			baseField.setText("");
			additionField.setText("");
			resultField.setText("");
			
			Main.switchScene("menu");
			Main.success("Successfully wrote to recipe file!");
		});
		
		root.getChildren().addAll(header, exit, idExample, baseField, additionField, resultField, finish);
	}

}
