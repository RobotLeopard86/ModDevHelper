package net.rl86.mdh;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.rl86.mdh.scenes.CubeModelScene;
import net.rl86.mdh.scenes.ItemModelScene;
import net.rl86.mdh.scenes.LangEntryScene;
import net.rl86.mdh.scenes.MenuScene;
import net.rl86.mdh.scenes.RecipesScene;
import net.rl86.mdh.scenes.blockstates.BlockstateFieldCreatorScene;
import net.rl86.mdh.scenes.blockstates.BlockstateFieldEditorScene;
import net.rl86.mdh.scenes.itemmodels.ItemModelFromBlockModelScene;
import net.rl86.mdh.scenes.itemmodels.ItemModelFromTextureScene;
import net.rl86.mdh.scenes.loot.LootTableSetupScene;
import net.rl86.mdh.scenes.recipes.BlastFurnaceScene;
import net.rl86.mdh.scenes.recipes.CampfireCookingScene;
import net.rl86.mdh.scenes.recipes.ShapedCraftingScene;
import net.rl86.mdh.scenes.recipes.ShapelessCraftingScene;
import net.rl86.mdh.scenes.recipes.SmeltingScene;
import net.rl86.mdh.scenes.recipes.SmithingScene;
import net.rl86.mdh.scenes.recipes.SmokerScene;
import net.rl86.mdh.scenes.recipes.StonecuttingScene;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;

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
		CommonUtilities.initializeImages();
		CommonUtilities.initializeFonts();
		initializeSceneMap();
		stage = primaryStage;
		primaryStage.getIcons().add(CommonUtilities.getIcon());
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
		scenes.put(CubeModelScene.class.getName(), new CubeModelScene());
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
		scenes.put(LootTableSetupScene.class.getName(), new LootTableSetupScene());
	}
	
}