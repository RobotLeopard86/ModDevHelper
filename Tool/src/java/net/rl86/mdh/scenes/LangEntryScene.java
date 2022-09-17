package net.rl86.mdh.scenes;

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
import net.rl86.mdh.Main;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

public class LangEntryScene extends BaseScene {

	private File langFile;
	private JsonObject obj;
	
	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Localization Entry";
		
		Text header = new Text();
		header.setText("Localization");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Text file = new Text();
		file.setFont(CommonUtilities.getFont(FontType.TEXT));
		file.setText("Selected File: ???");
		file.setWrappingWidth(scene.getWidth());
		
		TextField keyBox = new TextField();
		keyBox.setFont(CommonUtilities.getFont(FontType.TEXT));
		keyBox.setPromptText("Enter key (example: item.minecraft.diamond)...");
		keyBox.setVisible(false);
		
		TextField nameBox = new TextField();
		nameBox.setFont(CommonUtilities.getFont(FontType.TEXT));
		nameBox.setPromptText("Enter name...");
		nameBox.setVisible(false);
		
		Button addKey = new Button();
		addKey.setFont(CommonUtilities.getFont(FontType.TEXT));
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
		write.setFont(CommonUtilities.getFont(FontType.TEXT));
		write.setText("Write Changes");
		write.setOnAction(e -> {
			Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to write changes to the file?", ButtonType.YES, ButtonType.NO);
			if(confirm.showAndWait().get() == ButtonType.YES) {
				try {
					PrintWriter writer = new PrintWriter(langFile);
					writer.print(Main.getGson().toJson(obj));
					writer.flush();
					writer.close();
					Main.switchScene(MenuScene.class.getName());
					success("Successfully wrote entries!");
				} catch (FileNotFoundException exception) {
					exception.printStackTrace();
					error("File to write to could not be found! :(\nFile: " + langFile.getAbsolutePath());
				}
			}
		});
		write.setVisible(false);
		
		Button chooseFile = new Button();
		chooseFile.setFont(CommonUtilities.getFont(FontType.TEXT));
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
				error("IO stream read/write error :(");
			} catch (JsonSyntaxException exception) {
				exception.printStackTrace();
				error("JSON was malformed and therefore could not be parsed! :(");
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
				error("Language file could not be found! :(\nFile: " + langFile.getAbsolutePath());
			} catch (NullPointerException exception) {
				//Error swallowed as it is raised when the user cancels selection.
			}
			addKey.setVisible(!exceptionRaised);
			write.setVisible(!exceptionRaised);
			keyBox.setVisible(!exceptionRaised);
			nameBox.setVisible(!exceptionRaised);
			file.setText(exceptionRaised ? "Selected File: ???" : ("Selected File: " + langFile.getAbsolutePath()));
		});
		
		root.getChildren().addAll(header, chooseFile, file, keyBox, nameBox, addKey, write);
	}

}
