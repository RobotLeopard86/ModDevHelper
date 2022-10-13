package net.rl86.mdh.scenes.itemmodels;

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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.rl86.mdh.Main;
import net.rl86.mdh.scenes.ItemModelScene;
import net.rl86.mdh.scenes.MenuScene;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

public class ItemModelFromTextureScene extends BaseScene {

	private File textureFile;

	@Override
	public void buildScene() {
		returnToScene = ItemModelScene.class.getName();
		sceneTitle = "Mod Development Helper | Item Model From Texture Tool";

		Text header = new Text();
		header.setText("Item Models");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));

		Text texture = new Text();
		texture.setFont(CommonUtilities.getFont(FontType.TEXT));
		texture.setText("Selected Texture: ???");
		texture.setWrappingWidth(scene.getWidth());

		TextField modid = new TextField();
		modid.setFont(CommonUtilities.getFont(FontType.TEXT));
		modid.setPromptText("Enter mod ID...");
		modid.setVisible(false);

		Button generate = new Button();
		generate.setFont(CommonUtilities.getFont(FontType.TEXT));
		generate.setText("Generate Model");
		generate.setOnAction(e -> {
			JsonObject json = new JsonObject();
			json.addProperty("parent", "minecraft:item/generated");
			JsonObject textureObject = new JsonObject();
			textureObject.addProperty("layer0", modid.getText() + ":item/" + textureFile.getName().substring(0, textureFile.getName().length() - 4));
			json.add("textures", textureObject);

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

		Button textureChooser = new Button();
		textureChooser.setFont(CommonUtilities.getFont(FontType.TEXT));
		textureChooser.setText("Choose Texture");
		textureChooser.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose a texture:");
			fc.getExtensionFilters().add(new ExtensionFilter("PNG Images", "*.png"));
			textureFile = fc.showOpenDialog(Main.getStage());
			boolean invalid = true;
			if(textureFile != null) {
				try {
					BufferedImage bimg;
					bimg = ImageIO.read(textureFile);
					int width = bimg.getWidth();
					int height = bimg.getHeight();
					if(width != 16 || height != 16) {
						error("Selected texture file is too small or large. It must be 16x16.");
					} else {
						invalid = false;
					}
				} catch (IOException exception) {
					exception.printStackTrace();
					error("Couldn't read from texture file! :(\nFile: " + textureFile.getAbsolutePath());
				}
			}
			texture.setText(invalid ? "Selected Texture: ???" : "Selected Texture: " + textureFile.getAbsolutePath());
			generate.setVisible(!invalid);
			modid.setVisible(!invalid);
		});

		root.getChildren().addAll(header, textureChooser, texture, modid, generate);
	}

}
