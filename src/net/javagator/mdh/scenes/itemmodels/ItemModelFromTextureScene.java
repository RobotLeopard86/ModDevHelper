package net.javagator.mdh.scenes.itemmodels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class ItemModelFromTextureScene extends SceneRetriever {
	
	private File textureFile;
	private File outDir;
	
	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Item Model From Texture Tool";
		
		Text header = new Text();
		header.setText("Item Models");
		header.setFont(Main.headerFont);
		
		Text texture = new Text();
		texture.setFont(Main.textFont);
		texture.setText("Selected Texture: ???");
		texture.setWrappingWidth(scene.getWidth());
		
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
			json.addProperty("parent", "minecraft:item/generated");
			JsonObject textureObject = new JsonObject();
			textureObject.addProperty("layer0", modid.getText() + ":item/" + textureFile.getName().substring(0, textureFile.getName().length() - 4));
			json.add("textures", textureObject);
			
			File modelFile = new File(outDir.getAbsolutePath() + "/" + textureFile.getName().substring(0, textureFile.getName().length() - 4) + ".json");
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
				Main.switchScene("menu");
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
		
		Button textureChooser = new Button();
		textureChooser.setFont(Main.textFont);
		textureChooser.setText("Choose Texture");
		textureChooser.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose a texture:");
			fc.getExtensionFilters().add(new ExtensionFilter("PNG Images", "*.png"));
			textureFile = fc.showOpenDialog(Main.getStage());
			boolean invalid = true;
			try {
				BufferedImage bimg;
				bimg = ImageIO.read(textureFile);
				int width = bimg.getWidth();
				int height = bimg.getHeight();
				if(width != 16 || height != 16) {
					Main.error("Selected texture file is too small or large. It must be 16x16.");
				} else {
					invalid = false;
				}
			} catch (IOException exception) {
				exception.printStackTrace();
				Main.error("Couldn't read from texture file! :(\nFile: " + textureFile.getAbsolutePath());
			}
			outputDirChooser.setVisible(!invalid);
			output.setVisible(!invalid);
			output.setText("Selected Output Folder: ???");
			texture.setText(invalid ? "Selected Texture: ???" : "Selected Texture: " + textureFile.getAbsolutePath());
			generate.setVisible(false);
			modid.setVisible(false);
		});
		
		root.getChildren().addAll(header, exit, textureChooser, texture, outputDirChooser, output, modid, generate);
	}

}
