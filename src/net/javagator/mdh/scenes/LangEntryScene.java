package net.javagator.mdh.scenes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class LangEntryScene extends SceneRetriever {

	private File langFile;
	private JsonObject obj;
	
	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Localization Entry";
		
		Text header = new Text();
		header.setText("Localization");
		header.setFont(Main.headerFont);
		
		Text file = new Text();
		file.setFont(Main.textFont);
		file.setText("Selected File: ???");
		file.setWrappingWidth(scene.getWidth());
		
		TextField keyBox = new TextField();
		keyBox.setFont(Main.textFont);
		keyBox.setPromptText("Enter key (example: item.beyond.fudge)...");
		keyBox.setVisible(false);
		
		TextField nameBox = new TextField();
		nameBox.setFont(Main.textFont);
		nameBox.setPromptText("Enter name...");
		nameBox.setVisible(false);
		
		Button addKey = new Button();
		addKey.setFont(Main.textFont);
		addKey.setText("Add to Localization File");
		addKey.setOnAction(e -> {
			String key = keyBox.getText();
			String name = nameBox.getText();
			obj.addProperty(key, name);
			nameBox.setText("");
			keyBox.setText("");
		});
		addKey.setVisible(false);
		
		Button write = new Button();
		write.setFont(Main.textFont);
		write.setText("Write Changes");
		write.setOnAction(e -> {
			Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to write changes to the file?", ButtonType.YES, ButtonType.NO);
			if(confirm.showAndWait().get() == ButtonType.YES) {
				try {
					PrintWriter writer = new PrintWriter(langFile);
					writer.print(Main.getGson().toJson(obj));
					writer.flush();
					writer.close();
					Main.switchScene("menu");
					Main.success("Successfully wrote entries!");
				} catch (FileNotFoundException exception) {
					exception.printStackTrace();
					Main.error("File to write to could not be found! :(\nFile: " + langFile.getAbsolutePath());
				}
			}
		});
		write.setVisible(false);
		
		Button chooseFile = new Button();
		chooseFile.setFont(Main.textFont);
		chooseFile.setText("Choose Language File");
		chooseFile.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose a language file:");
			fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			langFile = fc.showOpenDialog(Main.getStage());
			boolean exceptionRaised = true;
			try {
				obj = JsonParser.parseReader(new FileReader(langFile)).getAsJsonObject();
				exceptionRaised = false;
			} catch (JsonIOException exception) {
				exception.printStackTrace();
				Main.error("IO stream read/write error :(");
			} catch (JsonSyntaxException exception) {
				exception.printStackTrace();
				Main.error("JSON was malformed and therefore could not be parsed! :(");
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
				Main.error("Language file could not be found! :(\nFile: " + langFile.getAbsolutePath());
			}
			addKey.setVisible(!exceptionRaised);
			write.setVisible(!exceptionRaised);
			keyBox.setVisible(!exceptionRaised);
			nameBox.setVisible(!exceptionRaised);
			file.setText(exceptionRaised ? "Selected File: ???" : ("Selected File: " + langFile.getAbsolutePath()));
		});
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("menu");
		});
		
		root.getChildren().addAll(header, exit, chooseFile, file, keyBox, nameBox, addKey, write);
	}

}
