package net.rl86.mdh.baseclasses;

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
import net.rl86.mdh.Main;
import net.rl86.mdh.scenes.MenuScene;
import net.rl86.mdh.scenes.RecipesScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

public class CookingRecipeScene extends BaseScene {
	
	protected String title;
	protected String headerText;
	protected String recipeType;

	@Override
	public void buildScene() {
		sceneTitle = title;
		
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText(headerText);
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(RecipesScene.class.getName());
		});
		
		Text idExample = new Text();
		idExample.setFont(CommonUtilities.getFont(FontType.TEXT));
		idExample.setText("Example of an item ID:\nminecraft:stone");
		
		TextField ingredientField = new TextField();
		ingredientField.setFont(CommonUtilities.getFont(FontType.TEXT));
		ingredientField.setPromptText("Enter ingredient item ID here...");
		
		TextField resultField = new TextField();
		resultField.setFont(CommonUtilities.getFont(FontType.TEXT));
		resultField.setPromptText("Enter result item ID here...");
		
		TextField xpField = new TextField();
		xpField.setFont(CommonUtilities.getFont(FontType.TEXT));
		xpField.setPromptText("Enter XP amount here...");
		
		TextField timeField = new TextField();
		timeField.setFont(CommonUtilities.getFont(FontType.TEXT));
		timeField.setPromptText("Enter cooking time (in ticks) here...");
		
		Button finish = new Button();
		finish.setFont(CommonUtilities.getFont(FontType.TEXT));
		finish.setText("Generate Recipe");
		finish.setOnAction(e -> {
			
			int xp, time;
			try {
				xp = Integer.parseInt(xpField.getText());
				time = Integer.parseInt(timeField.getText());
			} catch (NumberFormatException exception) {
				exception.printStackTrace();
				error("Both the XP and cooking time fields must be whole numbers! :(");
				return;
			}
			if(xp < 0 || time < 0) {
				error("Both the XP and cooking time fields must contain positive numbers! :(");
				return;
			}
			
			JsonObject jsonRoot = new JsonObject();
			jsonRoot.addProperty("type", "minecraft:" + recipeType);
			
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
			
			ingredientField.setText("");
			resultField.setText("");
			xpField.setText("");
			timeField.setText("");
			
			Main.switchScene(MenuScene.class.getName());
			success("Successfully wrote to recipe file!");
		});
		
		root.getChildren().addAll(header, exit, idExample, ingredientField, resultField, xpField, timeField, finish);
	}

}
