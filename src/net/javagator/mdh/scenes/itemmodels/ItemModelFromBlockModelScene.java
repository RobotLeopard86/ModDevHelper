package net.javagator.mdh.scenes.itemmodels;

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
import net.javagator.mdh.scenes.ItemModelScene;
import net.javagator.mdh.scenes.MenuScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class ItemModelFromBlockModelScene extends BaseScene {
	
	private File blockModel;
	
	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Item Model From Block Model Tool";
		
		Text header = new Text();
		header.setText("Item Models");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Text model = new Text();
		model.setFont(CommonUtilities.getFont(FontType.TEXT));
		model.setText("Selected Model: ???");
		model.setWrappingWidth(scene.getWidth());
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Back");
		exit.setOnAction(e -> {
			Main.switchScene(ItemModelScene.class.getName());
		});
		
		TextField modid = new TextField();
		modid.setFont(CommonUtilities.getFont(FontType.TEXT));
		modid.setPromptText("Enter mod ID...");
		modid.setVisible(false);
		
		Button generate = new Button();
		generate.setFont(CommonUtilities.getFont(FontType.TEXT));
		generate.setText("Generate Model");
		generate.setOnAction(e -> {
			JsonObject json = new JsonObject();
			json.addProperty("parent", modid.getText() + ":block/" + blockModel.getName().substring(0, blockModel.getName().length() - 5));
			
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			File modelFile = fc.showSaveDialog(Main.getStage());
			try {
				modelFile.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
				error("Model file could not be created! :(");
				return;
			}
			try {
				PrintWriter pw = new PrintWriter(modelFile);
				pw.print(Main.getGson().toJson(json));
				pw.flush();
				pw.close();
				Main.switchScene(MenuScene.class.getName());
				success("Successfully created model!");
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
				error("Model file could not be found! :(\nFile: " + modelFile.getAbsolutePath());
				return;
			}
		});
		generate.setVisible(false);
		
		Button modelChooser = new Button();
		modelChooser.setFont(CommonUtilities.getFont(FontType.TEXT));
		modelChooser.setText("Choose Model");
		modelChooser.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose a model file:");
			fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			blockModel = fc.showOpenDialog(Main.getStage());
			modid.setVisible(true);
			generate.setVisible(true);
			model.setText("Selected File: " + blockModel.getAbsolutePath());
		});
		
		root.getChildren().addAll(header, exit, modelChooser, model, modid, generate);
	}

}
