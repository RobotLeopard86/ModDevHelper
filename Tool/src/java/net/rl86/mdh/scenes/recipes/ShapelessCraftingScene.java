package net.rl86.mdh.scenes.recipes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.rl86.mdh.Main;
import net.rl86.mdh.scenes.MenuScene;
import net.rl86.mdh.scenes.RecipesScene;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

public class ShapelessCraftingScene extends BaseScene {

	protected int windowWidth = 500;
	protected int windowHeight = 775;

	@Override
	public void buildScene() {
		returnToScene = RecipesScene.class.getName();
		sceneTitle = "Mod Development Helper | Shapeless Crafting Recipe Generator";

		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Shapeless Crafting Recipes");

		Text idExample = new Text();
		idExample.setFont(CommonUtilities.getFont(FontType.TEXT));
		idExample.setText("Example of an item ID:\nminecraft:stone");

		TextField s1 = new TextField();
		s1.setFont(CommonUtilities.getFont(FontType.TEXT));
		s1.setPromptText("Enter 1st item ID...");

		TextField s2 = new TextField();
		s2.setFont(CommonUtilities.getFont(FontType.TEXT));
		s2.setPromptText("Enter 2nd item ID...");
		TextField s3 = new TextField();
		s3.setFont(CommonUtilities.getFont(FontType.TEXT));
		s3.setPromptText("Enter 3rd item ID...");
		s3.setDisable(true);
		TextField s4 = new TextField();
		s4.setFont(CommonUtilities.getFont(FontType.TEXT));
		s4.setPromptText("Enter 4th item ID...");
		s4.setDisable(true);
		TextField s5 = new TextField();
		s5.setFont(CommonUtilities.getFont(FontType.TEXT));
		s5.setPromptText("Enter 5th item ID...");
		s5.setDisable(true);
		TextField s6 = new TextField();
		s6.setFont(CommonUtilities.getFont(FontType.TEXT));
		s6.setPromptText("Enter 6th item ID...");
		s6.setDisable(true);
		TextField s7 = new TextField();
		s7.setFont(CommonUtilities.getFont(FontType.TEXT));
		s7.setPromptText("Enter 7th item ID...");
		s7.setDisable(true);
		TextField s8 = new TextField();
		s8.setFont(CommonUtilities.getFont(FontType.TEXT));
		s8.setPromptText("Enter 8th item ID...");
		s8.setDisable(true);
		TextField s9 = new TextField();
		s9.setFont(CommonUtilities.getFont(FontType.TEXT));
		s9.setPromptText("Enter 9th item ID...");
		s9.setDisable(true);

		TextField result = new TextField();
		result.setFont(CommonUtilities.getFont(FontType.TEXT));
		result.setPromptText("Enter result item ID...");

		Text numItemsLbl = new Text();
		numItemsLbl.setFont(CommonUtilities.getFont(FontType.TEXT));
		numItemsLbl.setText("Number of Items: 1");

		Slider numItemsBar = new Slider();
		numItemsBar.setMin(1d);
		numItemsBar.setMax(9d);
		numItemsBar.setPrefWidth(scene.getWidth() - 150);
		numItemsBar.valueProperty().addListener(e -> {
			numItemsLbl.setText("Number of Items: " + (int)numItemsBar.getValue());
			switch((int)numItemsBar.getValue()) {
			case 1:
				s2.setDisable(true);
				s3.setDisable(true);
				s4.setDisable(true);
				s5.setDisable(true);
				s6.setDisable(true);
				s7.setDisable(true);
				s8.setDisable(true);
				s9.setDisable(true);
			case 2:
				s2.setDisable(false);
				s3.setDisable(true);
				s4.setDisable(true);
				s5.setDisable(true);
				s6.setDisable(true);
				s7.setDisable(true);
				s8.setDisable(true);
				s9.setDisable(true);
				break;
			case 3:
				s2.setDisable(false);
				s3.setDisable(false);
				s4.setDisable(true);
				s5.setDisable(true);
				s6.setDisable(true);
				s7.setDisable(true);
				s8.setDisable(true);
				s9.setDisable(true);
				break;
			case 4:
				s2.setDisable(false);
				s3.setDisable(false);
				s4.setDisable(false);
				s5.setDisable(true);
				s6.setDisable(true);
				s7.setDisable(true);
				s8.setDisable(true);
				s9.setDisable(true);
				break;
			case 5:
				s2.setDisable(false);
				s3.setDisable(false);
				s4.setDisable(false);
				s5.setDisable(false);
				s6.setDisable(true);
				s7.setDisable(true);
				s8.setDisable(true);
				s9.setDisable(true);
				break;
			case 6:
				s2.setDisable(false);
				s3.setDisable(false);
				s4.setDisable(false);
				s5.setDisable(false);
				s6.setDisable(false);
				s7.setDisable(true);
				s8.setDisable(true);
				s9.setDisable(true);
				break;
			case 7:
				s2.setDisable(false);
				s3.setDisable(false);
				s4.setDisable(false);
				s5.setDisable(false);
				s6.setDisable(false);
				s7.setDisable(false);
				s8.setDisable(true);
				s9.setDisable(true);
				break;
			case 8:
				s2.setDisable(false);
				s3.setDisable(false);
				s4.setDisable(false);
				s5.setDisable(false);
				s6.setDisable(false);
				s7.setDisable(false);
				s8.setDisable(false);
				s9.setDisable(true);
				break;
			case 9:
				s2.setDisable(false);
				s3.setDisable(false);
				s4.setDisable(false);
				s5.setDisable(false);
				s6.setDisable(false);
				s7.setDisable(false);
				s8.setDisable(false);
				s9.setDisable(false);
				break;
			}
		});

		Button generate = new Button();
		generate.setFont(CommonUtilities.getFont(FontType.TEXT));
		generate.setText("Generate Recipe");
		generate.setOnAction(e -> {
			Alert msg = new Alert(AlertType.CONFIRMATION, "Are you sure this is the recipe you want?", ButtonType.YES, ButtonType.NO);
			if(msg.showAndWait().get() == ButtonType.YES) {
				TextField[] ingredientFields = new TextField[(int)numItemsBar.getValue() + 1];

				switch(ingredientFields.length) {
				case 9:
					ingredientFields[8] = s9;
				case 8:
					ingredientFields[7] = s8;
				case 7:
					ingredientFields[6] = s7;
				case 6:
					ingredientFields[5] = s6;
				case 5:
					ingredientFields[4] = s5;
				case 4:
					ingredientFields[3] = s4;
				case 3:
					ingredientFields[2] = s3;
				case 2:
					ingredientFields[1] = s2;
					ingredientFields[0] = s1;
				}
				ingredientFields[ingredientFields.length - 1] = result;

				if(!checkIfValid(ingredientFields)) {
					error("You must fill in all selected ingredient fields and the result field! :(");
					return;
				}

				JsonObject jsonRoot = new JsonObject();
				jsonRoot.addProperty("type", "minecraft:crafting_shapeless");

				JsonArray items = new JsonArray();
				for(TextField field : ingredientFields) {
					JsonObject obj = new JsonObject();
					obj.addProperty("item", field.getText());
					items.add(obj);
				}

				JsonObject resultItem = new JsonObject();
				resultItem.addProperty("item", result.getText());

				jsonRoot.add("ingredients", items);
				jsonRoot.add("result", resultItem);

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

				for(TextField field : ingredientFields) {
					field.setText("");
				}

				result.setText("");

				Main.switchScene(MenuScene.class.getName());
				success("Successfully wrote to recipe file!");
			}
		});

		HBox numItems = new HBox();
		numItems.setSpacing(15d);
		numItems.getChildren().addAll(numItemsLbl, numItemsBar);

		root.getChildren().addAll(header, idExample, numItems, s1, s2, s3,s4, s5, s6, s7, s8, s9, result, generate);
	}

	private static boolean checkIfValid(TextField[] fields) {
		boolean valid = true;

		for(TextField field : fields) {
			if(field.getText().equalsIgnoreCase("")) {
				valid = false;
				break;
			}
		}

		return valid;
	}
}
