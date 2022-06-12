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

public class BlastFurnaceScene extends SceneRetriever {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Blast Furnace Recipe Generator";
		
		Text header = new Text();
		header.setFont(Main.headerFont);
		header.setText("Blasting Recipes");
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("recipe");
		});
		
		Text idExample = new Text();
		idExample.setFont(Main.textFont);
		idExample.setText("Example of an item ID:\nminecraft:stone");
		
		TextField ingredientField = new TextField();
		ingredientField.setFont(Main.textFont);
		ingredientField.setPromptText("Enter ingredient item ID here...");
		
		TextField resultField = new TextField();
		resultField.setFont(Main.textFont);
		resultField.setPromptText("Enter result item ID here...");
		
		TextField xpField = new TextField();
		xpField.setFont(Main.textFont);
		xpField.setPromptText("Enter XP amount here...");
		
		TextField timeField = new TextField();
		timeField.setFont(Main.textFont);
		timeField.setPromptText("Enter cooking time (in ticks) here...");
		
		Button finish = new Button();
		finish.setFont(Main.textFont);
		finish.setText("Generate Recipe");
		finish.setOnAction(e -> {
			
			int xp, time;
			try {
				xp = Integer.parseInt(xpField.getText());
				time = Integer.parseInt(timeField.getText());
			} catch (NumberFormatException exception) {
				exception.printStackTrace();
				Main.error("Both the XP and cooking time fields must be whole numbers! :(");
				return;
			}
			if(xp < 0 || time < 0) {
				Main.error("Both the XP and cooking time fields must contain positive numbers! :(");
				return;
			}
			
			JsonObject jsonRoot = new JsonObject();
			jsonRoot.addProperty("type", "minecraft:blasting");
			
			JsonObject item = new JsonObject();
			item.addProperty("item", ingredientField.getText());
			jsonRoot.add("ingredient", item);
			
			jsonRoot.addProperty("result", resultField.getText());
			jsonRoot.addProperty("experience", xp);
			jsonRoot.addProperty("cookingtime", time);
			
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
			
			ingredientField.setText("");
			resultField.setText("");
			xpField.setText("");
			timeField.setText("");
			
			Main.switchScene("menu");
			Main.success("Successfully wrote to recipe file!");
		});
		
		root.getChildren().addAll(header, exit, idExample, ingredientField, resultField, xpField, timeField, finish);
	}

}
