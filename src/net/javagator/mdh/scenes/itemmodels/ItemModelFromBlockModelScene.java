package net.javagator.mdh.scenes.itemmodels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class ItemModelFromBlockModelScene extends SceneRetriever {
	
	private File blockModel;
	private File outDir;
	
	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Item Model Designer";
		
		Text header = new Text();
		header.setText("Item Models");
		header.setFont(Main.headerFont);
		
		Text model = new Text();
		model.setFont(Main.textFont);
		model.setText("Selected Model: ???");
		model.setWrappingWidth(scene.getWidth());
		
		Text output = new Text();
		output.setFont(Main.textFont);
		output.setText("Selected Output Folder: ???");
		output.setWrappingWidth(scene.getWidth());
		output.setVisible(false);
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Back");
		exit.setOnAction(e -> {
			Main.switchScene("items");
		});
		
		TextField modid = new TextField();
		modid.setFont(Main.textFont);
		modid.setPromptText("Enter mod ID...");
		modid.setVisible(false);
		
		Button generate = new Button();
		generate.setFont(Main.textFont);
		generate.setText("Generate Model");
		generate.setOnAction(e -> {
			JsonObject json = new JsonObject();
			json.addProperty("parent", modid.getText() + ":block/" + blockModel.getName().substring(0, blockModel.getName().length() - 5));
			
			File modelFile = new File(outDir.getAbsolutePath() + "/" + blockModel.getName().substring(0, blockModel.getName().length() - 5) + ".json");
			try {
				modelFile.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
				Main.error("Model file could not be created! :(");
				return;
			}
			try {
				PrintWriter pw = new PrintWriter(modelFile);
				pw.print(Main.getGson().toJson(json));
				pw.flush();
				pw.close();
				Main.success("Successfully created model!");
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
				Main.error("Model file could not be found! :(\nFile: " + modelFile.getAbsolutePath());
				return;
			}
		});
		generate.setVisible(false);
		
		Button outputDirChooser = new Button();
		outputDirChooser.setFont(Main.textFont);
		outputDirChooser.setText("Choose Output Folder");
		outputDirChooser.setOnAction(e -> {
			DirectoryChooser dc = new DirectoryChooser();
			dc.setTitle("Choose an output folder:");
			outDir = dc.showDialog(Main.getStage());
			output.setText(outDir.getAbsolutePath());
			generate.setVisible(true);
			modid.setVisible(true);
		});
		outputDirChooser.setVisible(false);
		
		Button modelChooser = new Button();
		modelChooser.setFont(Main.textFont);
		modelChooser.setText("Choose Model");
		modelChooser.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose a model file:");
			fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			blockModel = fc.showOpenDialog(Main.getStage());
			outputDirChooser.setVisible(true);
			output.setVisible(true);
			modid.setVisible(false);
			generate.setVisible(false);
			model.setText("Selected File: " + blockModel.getAbsolutePath());
		});
		
		root.getChildren().addAll(header, exit, modelChooser, model, outputDirChooser, output, modid, generate);
	}

}