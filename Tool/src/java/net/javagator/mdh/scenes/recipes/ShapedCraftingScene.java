package net.javagator.mdh.scenes.recipes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.MenuScene;
import net.javagator.mdh.scenes.RecipesScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;
import net.javagator.mdh.util.KeyDescriptor;

public class ShapedCraftingScene extends BaseScene {
	
	protected int windowWidth = 600;
	protected int windowHeight = 615;

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Shaped Crafting Recipe Generator";
		
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Shaped Crafting Recipes");
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(RecipesScene.class.getName());
		});
		
		HBox s1 = manufactureSlot(1);
		HBox s2 = manufactureSlot(2);
		HBox s3 = manufactureSlot(3);
		HBox s4 = manufactureSlot(4);
		HBox s5 = manufactureSlot(5);
		HBox s6 = manufactureSlot(6);
		HBox s7 = manufactureSlot(7);
		HBox s8 = manufactureSlot(8);
		HBox s9 = manufactureSlot(9);
		
		HBox result = new HBox();
		result.setSpacing(15d);
		
		Text label = new Text();
		label.setFont(CommonUtilities.getFont(FontType.TEXT));
		label.setText("Result Item: ");
		
		TextField id = new TextField();
		id.setFont(CommonUtilities.getFont(FontType.TEXT));
		id.setPromptText("Enter item ID...");
		
		result.getChildren().addAll(label, id);
		
		VBox s1to5 = new VBox();
		s1to5.setSpacing(10d);
		s1to5.getChildren().addAll(s1, s2, s3, s4, s5);
		
		VBox s6to9 = new VBox();
		s6to9.setSpacing(10d);
		s6to9.getChildren().addAll(s6, s7, s8, s9, result);
		
		HBox slots = new HBox();
		slots.setSpacing(10d);
		slots.getChildren().addAll(s1to5, s6to9);
		
		ImageView grid = new ImageView();
		grid.setImage(new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/resources/images/crafting_table_grid.png", 200, 200, true, true, true));
		
		Text idExample = new Text();
		idExample.setFont(CommonUtilities.getFont(FontType.TEXT));
		idExample.setText("Example of an item ID:\nminecraft:stone");
		
		HBox topHBox = new HBox();
		topHBox.setSpacing(15d);
		topHBox.getChildren().addAll(grid, idExample);
		
		Button generate = new Button();
		generate.setFont(CommonUtilities.getFont(FontType.TEXT));
		generate.setText("Generate Recipe");
		generate.setOnAction(e -> {
			Alert msg = new Alert(AlertType.CONFIRMATION, "Are you sure this is the recipe you want?", ButtonType.YES, ButtonType.NO);
			if(msg.showAndWait().get() == ButtonType.YES) {
				JsonObject jsonRoot = new JsonObject();
				jsonRoot.addProperty("type", "minecraft:crafting_shaped");
				
				HBox[] allSlots = {s1, s2, s3, s4, s5, s6, s7, s8, s9};
				KeyDescriptor[] keys = genItemsAndKeys(allSlots);
				
				JsonObject legend = new JsonObject();
				for(KeyDescriptor kd : keys) {
					JsonObject key = new JsonObject();
					key.addProperty("item", kd.getVal());
					legend.add(((Character)kd.getKey()).toString(), key);
				}
				if(legend.has(" ")) legend.remove(" ");
				
				JsonArray pattern = new JsonArray();
				HashMap<String, Character> keymap = generateKeyMap(keys);
				for(int i = 1; i < 10; i += 3) {
					String row = "";
					for(int j = 0; j < 3; j++) {
						String itemID = ((TextField)allSlots[(i + j) - 1].getChildren().get(1)).getText();
						char key;
						try {
							key = keymap.get(itemID);
						} catch(NullPointerException npe) {
							key = ' ';
						}
						row = row + key;
					}
					pattern.add(row);
				}
				
				JsonObject last = new JsonObject();
				last.addProperty("item", id.getText());
				
				jsonRoot.add("key", legend);
				jsonRoot.add("pattern", pattern);
				jsonRoot.add("result", last);
				
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
				
				for(HBox box : allSlots) {
					((TextField)box.getChildren().get(1)).setText("");
				}
				
				id.setText("");
				
				Main.switchScene(MenuScene.class.getName());
				success("Successfully wrote to recipe file!");
			}
		});
		
		root.getChildren().addAll(header, exit, topHBox, slots, generate);
	}
	
	private static HBox manufactureSlot(int num) {
		HBox main = new HBox();
		main.setSpacing(15d);
		
		Text label = new Text();
		label.setFont(CommonUtilities.getFont(FontType.TEXT));
		label.setText("Slot " + num + ": ");
		
		TextField id = new TextField();
		id.setFont(CommonUtilities.getFont(FontType.TEXT));
		id.setPromptText("Enter item ID...");
		
		main.getChildren().addAll(label, id);
		
		return main;
	}
	
	private static boolean kdListContainsValue(ArrayList<KeyDescriptor> list, String value) {
		boolean doesContain = false;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getVal().equalsIgnoreCase(value)) {
				doesContain = true;
				break;
			}
		}
		return doesContain;
	}
	
	private static KeyDescriptor[] genItemsAndKeys(HBox[] slots) {
		ArrayList<KeyDescriptor> result = new ArrayList<>();
		char[] keys = {'#', '%', '$', '@', '/', '?', '&', '<', '>'};
		
		int keyID = 0;
		
		for(int i = 0; i < slots.length; i++) {
			String value = ((TextField)slots[i].getChildren().get(1)).getText();
			
			if(!kdListContainsValue(result, value) && !value.equalsIgnoreCase("")) {
				result.add(new KeyDescriptor(keys[keyID], value));
				keyID++;
			}
		}
		
		return result.toArray(new KeyDescriptor[0]);
	}
	
	private static HashMap<String, Character> generateKeyMap(KeyDescriptor[] descriptors){
		HashMap<String, Character> map = new HashMap<>();
		for(int i = 0; i < descriptors.length; i++) {
			map.put(descriptors[i].getVal(), descriptors[i].getKey());
		}
		return map;
	}

}
