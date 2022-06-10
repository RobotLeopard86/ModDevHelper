package net.javagator.mdh;

import java.io.InputStream;
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
	
	public static Font headerFont/* = Font.loadFont("file:resources/fonts/redhatdisplay.ttf", 36)*/;
	public static Font textFont/* = Font.loadFont("file:resources/fonts/redhattext.ttf", 18)*/;
	
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
		gson = new GsonBuilder().setPrettyPrinting().create();
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
	
	private void initializeFonts() {
		InputStream his = this.getClass().getResourceAsStream("fonts/redhatdisplay.ttf");
		headerFont = Font.loadFont(his, 36f);
		InputStream tis = this.getClass().getResourceAsStream("fonts/redhattext.ttf");
		textFont = Font.loadFont(tis, 36f);
	}
	
	public static void error(String description) {
		Alert msg = new Alert(AlertType.ERROR, description, ButtonType.OK);
		msg.setTitle("Error");
		ImageView graphic = new ImageView();
		graphic.setImage(new Image("file:resources/images/error.png", 64, 64, true, true, true));
		msg.setGraphic(graphic);
		msg.show();
	}
	
	public static void success(String description) {
		Alert msg = new Alert(AlertType.INFORMATION, description, ButtonType.OK);
		msg.setTitle("Success!");
		ImageView graphic = new ImageView();
		graphic.setImage(new Image("file:resources/images/success.png", 64, 64, true, true, true));
		msg.setGraphic(graphic);
		msg.setHeaderText("Success!");
		msg.show();
	}

}