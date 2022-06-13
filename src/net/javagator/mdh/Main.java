package net.javagator.mdh;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.ItemModelScene;
import net.javagator.mdh.scenes.LangEntryScene;
import net.javagator.mdh.scenes.MenuScene;
import net.javagator.mdh.scenes.RecipesScene;
import net.javagator.mdh.scenes.blockstates.BlockstateFieldCreatorScene;
import net.javagator.mdh.scenes.blockstates.BlockstateFieldEditorScene;
import net.javagator.mdh.scenes.itemmodels.ItemModelFromBlockModelScene;
import net.javagator.mdh.scenes.itemmodels.ItemModelFromTextureScene;
import net.javagator.mdh.scenes.recipes.BlastFurnaceScene;
import net.javagator.mdh.scenes.recipes.CampfireCookingScene;
import net.javagator.mdh.scenes.recipes.ShapedCraftingScene;
import net.javagator.mdh.scenes.recipes.ShapelessCraftingScene;
import net.javagator.mdh.scenes.recipes.SmeltingScene;
import net.javagator.mdh.scenes.recipes.SmithingScene;
import net.javagator.mdh.scenes.recipes.SmokerScene;
import net.javagator.mdh.scenes.recipes.StonecuttingScene;
import net.javagator.mdh.util.CommonUtilities;

public class Main extends Application {
	
	private static HashMap<String, BaseScene> scenes = new HashMap<>();
	private static String defaultScene = MenuScene.class.getName();
	
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
		CommonUtilities.initializeMessageImages();
		CommonUtilities.initializeFonts();
		initializeSceneMap();
		stage = primaryStage;
		primaryStage.setOnCloseRequest(e -> {
			Alert msg = new Alert(AlertType.WARNING, "You may have unsaved data! Are you sure you want to quit?", ButtonType.YES, ButtonType.NO);
			msg.setTitle("Warning!");
			ImageView graphic = new ImageView();
			graphic.setImage(CommonUtilities.getWarningImg());
			msg.setGraphic(graphic);
			msg.setHeaderText("Warning");
			if(msg.showAndWait().get() == ButtonType.NO) {
				e.consume();
			}
		});
		switchScene(defaultScene);
		primaryStage.show();
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
		scenes.put(MenuScene.class.getName(), new MenuScene());
		scenes.put(LangEntryScene.class.getName(), new LangEntryScene());
		scenes.put(ItemModelScene.class.getName(), new ItemModelScene());
		scenes.put(ItemModelFromTextureScene.class.getName(), new ItemModelFromTextureScene());
		scenes.put(ItemModelFromBlockModelScene.class.getName(), new ItemModelFromBlockModelScene());
		scenes.put(BlockstateFieldCreatorScene.class.getName(), new BlockstateFieldCreatorScene());
		scenes.put(BlockstateFieldEditorScene.class.getName(), new BlockstateFieldEditorScene());
		scenes.put(RecipesScene.class.getName(), new RecipesScene());
		scenes.put(ShapedCraftingScene.class.getName(), new ShapedCraftingScene());
		scenes.put(ShapelessCraftingScene.class.getName(), new ShapelessCraftingScene());
		scenes.put(BlastFurnaceScene.class.getName(), new BlastFurnaceScene());
		scenes.put(CampfireCookingScene.class.getName(), new CampfireCookingScene());
		scenes.put(SmeltingScene.class.getName(), new SmeltingScene());
		scenes.put(SmithingScene.class.getName(), new SmithingScene());
		scenes.put(SmokerScene.class.getName(), new SmokerScene());
		scenes.put(StonecuttingScene.class.getName(), new StonecuttingScene());
	}
	
}