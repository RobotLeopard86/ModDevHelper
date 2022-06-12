package net.javagator.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.baseclasses.BaseScene;
import net.javagator.mdh.scenes.blockstates.BlockstateFieldCreatorScene;
import net.javagator.mdh.util.CommonUtilities;
import net.javagator.mdh.util.CommonUtilities.FontType;

public class MenuScene extends BaseScene {

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Home";
		
		Text header = new Text();
		header.setText("Mod Development Helper");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Text todo = new Text();
		todo.setText("Please select an option:");
		todo.setFont(CommonUtilities.getFont(FontType.HEADER));

		Button lang = new Button();
		lang.setFont(CommonUtilities.getFont(FontType.HEADER));
		lang.setText("Localization Entry");
		lang.setOnAction(e -> {
			Main.switchScene(LangEntryScene.class.getName());
		});
		
		Button imodel = new Button();
		imodel.setFont(CommonUtilities.getFont(FontType.HEADER));
		imodel.setText("Item Model Designer");
		imodel.setOnAction(e -> {
			Main.switchScene(ItemModelScene.class.getName());
		});
		
		Button states = new Button();
		states.setFont(CommonUtilities.getFont(FontType.HEADER));
		states.setText("Blockstate File Creator");
		states.setOnAction(e -> {
			Main.switchScene(BlockstateFieldCreatorScene.class.getName());
		});
		
		Button craft = new Button();
		craft.setFont(CommonUtilities.getFont(FontType.HEADER));
		craft.setText("Recipe Generator");
		craft.setOnAction(e -> {
			Main.switchScene(RecipesScene.class.getName());
		});
		
		Button quit = new Button();
		quit.setFont(CommonUtilities.getFont(FontType.HEADER));
		quit.setText("Quit App");
		quit.setOnAction(e -> {
			System.exit(0);
		});
		
		root.getChildren().addAll(header, todo, lang, imodel, states, craft, quit);
	}

}
