package net.rl86.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import net.rl86.mdh.Main;
import net.rl86.mdh.scenes.blockstates.BlockstateFieldCreatorScene;
import net.rl86.mdh.scenes.loot.LootTableSetupScene;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

public class MenuScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Home";
		
		Text header = new Text();
		header.setText("Mod Development Helper");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Text todo = new Text();
		todo.setText("Please select an option:");
		todo.setFont(CommonUtilities.getFont(FontType.TEXT));

		Button lang = new Button();
		lang.setFont(CommonUtilities.getFont(FontType.TEXT));
		lang.setText("Localization Entry");
		lang.setOnAction(e -> {
			Main.switchScene(LangEntryScene.class.getName());
		});
		
		Button imodel = new Button();
		imodel.setFont(CommonUtilities.getFont(FontType.TEXT));
		imodel.setText("Item Model Designer");
		imodel.setOnAction(e -> {
			Main.switchScene(ItemModelScene.class.getName());
		});
		
		Button cmodel = new Button();
		cmodel.setFont(CommonUtilities.getFont(FontType.TEXT));
		cmodel.setText("Cube Block Model Creator");
		cmodel.setOnAction(e -> {
			Main.switchScene(CubeModelScene.class.getName());
		});
		
		Button states = new Button();
		states.setFont(CommonUtilities.getFont(FontType.TEXT));
		states.setText("Blockstate File Creator");
		states.setOnAction(e -> {
			Main.switchScene(BlockstateFieldCreatorScene.class.getName());
		});
		
		Button craft = new Button();
		craft.setFont(CommonUtilities.getFont(FontType.TEXT));
		craft.setText("Recipe Generator");
		craft.setOnAction(e -> {
			Main.switchScene(RecipesScene.class.getName());
		});
		
		Button loot = new Button();
		loot.setFont(CommonUtilities.getFont(FontType.TEXT));
		loot.setText("Loot Table Creator");
		loot.setOnAction(e -> {
			Main.switchScene(LootTableSetupScene.class.getName());
		});
		
		Button quit = new Button();
		quit.setFont(CommonUtilities.getFont(FontType.TEXT));
		quit.setText("Quit App");
		quit.setOnAction(e -> {
			Main.getStage().fireEvent(new WindowEvent(Main.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		
		root.getChildren().addAll(header, todo, lang, imodel, cmodel, states, craft, loot, quit);
		root.getChildren().remove(back);
	}

}
