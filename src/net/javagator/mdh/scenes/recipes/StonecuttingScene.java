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

public class StonecuttingScene extends SceneRetriever {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Stonecutting Recipe Generator";
		
		Text header = new Text();
		header.setFont(Main.headerFont);
		header.setText("Stonecutting Recipes");
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("recipe");
		});
		
		Text idExample = new Text();
		idExample.setFont(Main.textFont);
		idExample.setText("Example of an item ID:\nminecraft:stone");
		
		TextField itemField = new TextField();
		itemField.setFont(Main.textFont);
		itemField.setPromptText("Enter ingredient item ID here...");
		
		TextField resultField = new TextField();
		resultField.setFont(Main.textFont);
		resultField.setPromptText("Enter result item ID here...");
		
		TextField countField = new TextField();
		countField.setFont(Main.textFont);
		countField.setPromptText("Enter amount of result item here...");
		
		Button finish = new Button();
		finish.setFont(Main.textFont);
		finish.setText("Generate Recipe");
		finish.setOnAction(e -> {
			
			int amount;
			try {
				amount = Integer.parseInt(countField.getText());
			} catch (NumberFormatException exception) {
				exception.printStackTrace();
				Main.error("The amount field must be a whole number! :(");
				return;
			}
			if(amount < 1) {
				Main.error("The amount field must be greater than 1!");
				return;
			}
			
			JsonObject jsonRoot = new JsonObject();
			jsonRoot.addProperty("type", "minecraft:smithing");
			
			JsonObject item = new JsonObject();
			item.addProperty("item", itemField.getText());
			jsonRoot.add("ingredient", item);
			
			jsonRoot.addProperty("result", resultField.getText());
			jsonRoot.addProperty("count", amount);
			
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
			
			itemField.setText("");
			resultField.setText("");
			countField.setText("");
			
			Main.switchScene("menu");
			Main.success("Successfully wrote to recipe file!");
		});
		
		root.getChildren().addAll(header, exit, idExample, itemField, resultField, countField, finish);
	}

}
