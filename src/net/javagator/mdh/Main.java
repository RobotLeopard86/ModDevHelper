package net.javagator.mdh;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.javagator.mdh.scenes.ItemModelScene;
import net.javagator.mdh.scenes.LangEntryScene;
import net.javagator.mdh.scenes.MenuScene;
import net.javagator.mdh.scenes.blockstates.BlockstateFieldCreatorScene;
import net.javagator.mdh.scenes.blockstates.BlockstateFieldEditorScene;
import net.javagator.mdh.scenes.itemmodels.ItemModelFromBlockModelScene;
import net.javagator.mdh.scenes.itemmodels.ItemModelFromTextureScene;

public class Main extends Application {
	
	private static HashMap<String, SceneRetriever> scenes = new HashMap<>();
	private static String defaultScene = "menu";
	
	public static Font headerFont;
	public static Font textFont;
	
	private static Image errMsgImg;
	private static Image yayMsgImg;
	
	private static Stage stage;
	
	public static Stage getStage() {
		return stage;
	}
	
	private static Gson gson;
	
	public static Gson getGson() {
		return gson;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeFonts();
		initializeMessageImages();
		initializeSceneMap();
		primaryStage.setScene(scenes.get(defaultScene).getScene());
		primaryStage.setTitle(scenes.get(defaultScene).sceneTitle);
		primaryStage.show();
		stage = primaryStage;
	}
	
	public static void switchScene(String name) {
		stage.setScene(scenes.get(name).getScene());
		stage.setTitle(scenes.get(name).sceneTitle);
	}

	public static void main(String[] args) {
		gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		launch(args);
	}
	
	private void initializeSceneMap() {
		scenes.put("menu", new MenuScene());
		scenes.put("lang", new LangEntryScene());
		scenes.put("items", new ItemModelScene());
		scenes.put("itexture", new ItemModelFromTextureScene());
		scenes.put("iblock", new ItemModelFromBlockModelScene());
		scenes.put("bs1", new BlockstateFieldCreatorScene());
		scenes.put("bs2", new BlockstateFieldEditorScene());
	}
	
	private void initializeMessageImages() {
		errMsgImg = new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/resources/images/error.png", 64, 64, true, true, true);
		yayMsgImg = new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/resources/images/success.png", 64, 64, true, true, true);
	}
	
	private void initializeFonts() {
		BufferedInputStream his = null;
		BufferedInputStream tis = null;
		try {
			his = new BufferedInputStream(new URL("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/resources/fonts/redhatdisplay.ttf").openStream());
			tis = new BufferedInputStream(new URL("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/resources/fonts/redhattext.ttf").openStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			error("Couldn't load font from malformed URL! :(");
		} catch (IOException e) {
			e.printStackTrace();
			error("Couldn't load due to IO exception! If you are offline, please obtain a connection. :(");
		}
		headerFont = Font.loadFont(his, 36f);
		textFont = Font.loadFont(tis, 18f);
	}
	
	public static void error(String description) {
		Alert msg = new Alert(AlertType.ERROR, description, ButtonType.OK);
		msg.setTitle("Error");
		ImageView graphic = new ImageView();
		graphic.setImage(errMsgImg);
		msg.setGraphic(graphic);
		msg.show();
	}
	
	public static void success(String description) {
		Alert msg = new Alert(AlertType.INFORMATION, description, ButtonType.OK);
		msg.setTitle("Success!");
		ImageView graphic = new ImageView();
		graphic.setImage(yayMsgImg);
		msg.setGraphic(graphic);
		msg.setHeaderText("Success!");
		msg.show();
	}

}