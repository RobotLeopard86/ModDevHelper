package net.javagator.mdh.scenes.loot.block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.MenuScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class SingleItemBlockLootScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Block Loot Tables";
		
		Text header = new Text();
		header.setText("Single Item");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Back");
		exit.setOnAction(e -> {
			Main.switchScene(BlockLootScene.class.getName());
		});
		
		TextField itemField = new TextField();
		itemField.setFont(CommonUtilities.getFont(FontType.TEXT));
		itemField.setPromptText("Enter dropped item ID...");
		
		CheckBox applySilkTouch = new CheckBox();
		applySilkTouch.setFont(CommonUtilities.getFont(FontType.TEXT));
		applySilkTouch.setText("Apply Silk Touch?");
		
		CheckBox applyFortune = new CheckBox();
		applyFortune.setFont(CommonUtilities.getFont(FontType.TEXT));
		applyFortune.setText("Apply Fortune?");
		
		CheckBox applyExplosion = new CheckBox();
		applyExplosion.setFont(CommonUtilities.getFont(FontType.TEXT));
		applyExplosion.setText("Apply Explosion Survival Requirement (Recommended)?");
		applyExplosion.setSelected(true);
		
		Button done = new Button();
		done.setFont(CommonUtilities.getFont(FontType.TEXT));
		done.setText("Finish");
		done.setOnAction(e -> {
			if(itemField.getText().equalsIgnoreCase("")) {
				error("You must fill in the item field! :(");
				return;
			}
			
			if(!applySilkTouch.isSelected() && !applyFortune.isSelected() && !applyExplosion.isSelected()) {
				if(warning("You have not selected the explosion survival requirement! This is recommended and is typically only disabled with items such as ores and leaves. Are you sure this is what you want?") == ButtonType.NO) {
					return;
				}
			}
			
			if((applySilkTouch.isSelected() || applyFortune.isSelected()) && applyExplosion.isSelected()) {
				if(warning("You have selected the explosion survival requirement along with either silk touch or fortune functionality. This is not recommended and is not seen as a function in vanilla Minecraft. Are you sure this is what you want?") == ButtonType.NO) {
					return;
				}
			}
			
			JsonObject jsonRoot = new JsonObject();
			jsonRoot.addProperty("type", "minecraft:block");
			
			JsonArray pools = new JsonArray();
			JsonObject pool = new JsonObject();
			pool.addProperty("rolls", 1);
			pool.addProperty("bonus_rolls", 0);
			
			JsonArray conditions = new JsonArray();
			
			if(applyExplosion.isSelected()) {
				JsonObject condition = new JsonObject();
				condition.addProperty("condition", "minecraft:survives_explosion");
				conditions.add(condition);
			}
			
			if(applySilkTouch.isSelected() && !applyFortune.isSelected()) {
				JsonObject condition = new JsonObject();
				condition.addProperty("condition", "minecraft:match_tool");
				
				JsonObject predicate = new JsonObject();
				
				JsonArray enchants = new JsonArray();
				
				JsonObject st = new JsonObject();
				st.addProperty("enchantment", "minecraft:silk_touch");
				
				JsonObject levels = new JsonObject();
				levels.addProperty("min", 1);
				st.add("levels", levels);
				
				enchants.add(st);
				
				predicate.add("enchantments", enchants);
				
				condition.add("predicate", predicate);
				
				conditions.add(condition);
			}
			
			if(applySilkTouch.isSelected() && applyFortune.isSelected()) {
				JsonArray entries = new JsonArray();
				
				JsonObject entry = new JsonObject();
				entry.addProperty("type", "minecraft:alternatives");
				
				JsonArray children = new JsonArray();
				
				JsonObject child1 = new JsonObject();
				child1.addProperty("type", "minecraft:item");
				
				JsonObject condition = new JsonObject();
				condition.addProperty("condition", "minecraft:match_tool");
				JsonObject predicate = new JsonObject();	
				JsonArray enchants = new JsonArray();
				JsonObject st = new JsonObject();
				st.addProperty("enchantment", "minecraft:silk_touch");		
				JsonObject levels = new JsonObject();
				levels.addProperty("min", 1);
				st.add("levels", levels);	
				enchants.add(st);		
				predicate.add("enchantments", enchants);	
				condition.add("predicate", predicate);
				
				JsonArray childConditions = new JsonArray();
				childConditions.add(condition);
				
				child1.add("conditions", childConditions);
				child1.addProperty("name", itemField.getText());
				
				children.add(child1);
				
				JsonObject child2 = new JsonObject();
				child2.addProperty("type", "minecraft:item");
				
				JsonArray funcs = new JsonArray();
				
				JsonObject fortune = new JsonObject();	
				fortune.addProperty("enchantment", "minecraft:fortune");
				fortune.addProperty("formula", "minecraft:ore_drops");
				fortune.addProperty("function", "minecraft:apply_bonus");
				
				JsonObject decay = new JsonObject();
				decay.addProperty("function", "minecraft:explosion_decay");
				
				funcs.add(fortune);
				funcs.add(decay);
				
				child2.add("functions", funcs);
				child2.addProperty("name", itemField.getText());
				
				children.add(child1);
				children.add(child2);
				
				entry.add("children", children);
				
				entries.add(entry);
				pool.add("entries", entries);
			} else if(!applySilkTouch.isSelected() && applyFortune.isSelected()) {		
				JsonArray entries = new JsonArray();
				
				JsonObject entry = new JsonObject();
				entry.addProperty("type", "minecraft:item");
				
				JsonArray funcs = new JsonArray();
				
				JsonObject fortune = new JsonObject();	
				fortune.addProperty("enchantment", "minecraft:fortune");
				fortune.addProperty("formula", "minecraft:ore_drops");
				fortune.addProperty("function", "minecraft:apply_bonus");
				
				JsonObject decay = new JsonObject();
				decay.addProperty("function", "minecraft:explosion_decay");
				
				funcs.add(fortune);
				funcs.add(decay);
				
				entry.add("functions", funcs);
				entry.addProperty("name", itemField.getText());
				
				entries.add(entry);
				pool.add("entries", entries);
			} else {
				JsonArray entries = new JsonArray();
				
				JsonObject entry = new JsonObject();
				entry.addProperty("type", "minecraft:item");
				entry.addProperty("name", itemField.getText());
				
				entries.add(entry);
				pool.add("entries", entries);
			}
			
			if(!conditions.isEmpty()) pool.add("conditions", conditions);
			
			pools.add(pool);
			jsonRoot.add("pools", pools);
			
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
			
			itemField.setText("");
			applySilkTouch.setSelected(false);
			applyFortune.setSelected(false);
			applyExplosion.setSelected(true);
			
			Main.switchScene(MenuScene.class.getName());
			success("Successfully wrote to loot table file!");
		});
		
		root.getChildren().addAll(header, exit, itemField, applySilkTouch, applyFortune, applyExplosion, done);
	}

}
