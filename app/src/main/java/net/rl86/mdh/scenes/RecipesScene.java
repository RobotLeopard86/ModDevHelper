package net.rl86.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import net.rl86.mdh.Main;
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
import net.rl86.mdh.util.CommonUtilities.FontType;

public class RecipesScene extends BaseScene {

	@Override
	public void buildScene() {
		warnOnExit = false;
		sceneTitle = "Mod Development Helper | Recipe Generator";

		Text header = new Text();
		header.setText("Recipes");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));

		ToggleGroup options = new ToggleGroup();

		RadioButton cshape = new RadioButton();
		cshape.setFont(CommonUtilities.getFont(FontType.TEXT));
		cshape.setText("Crafting Table - Shaped");
		cshape.setToggleGroup(options);
		cshape.getProperties().put("continue_to", ShapedCraftingScene.class.getName());

		RadioButton csl = new RadioButton();
		csl.setFont(CommonUtilities.getFont(FontType.TEXT));
		csl.setText("Crafting Table - Shapeless");
		csl.setToggleGroup(options);
		csl.getProperties().put("continue_to", ShapelessCraftingScene.class.getName());

		RadioButton blast = new RadioButton();
		blast.setFont(CommonUtilities.getFont(FontType.TEXT));
		blast.setText("Blast Furnace");
		blast.setToggleGroup(options);
		blast.getProperties().put("continue_to", BlastFurnaceScene.class.getName());

		RadioButton camp = new RadioButton();
		camp.setFont(CommonUtilities.getFont(FontType.TEXT));
		camp.setText("Campfire Cooking");
		camp.setToggleGroup(options);
		camp.getProperties().put("continue_to", CampfireCookingScene.class.getName());

		RadioButton smelt = new RadioButton();
		smelt.setFont(CommonUtilities.getFont(FontType.TEXT));
		smelt.setText("Furnace");
		smelt.setToggleGroup(options);
		smelt.getProperties().put("continue_to", SmeltingScene.class.getName());

		RadioButton smith = new RadioButton();
		smith.setFont(CommonUtilities.getFont(FontType.TEXT));
		smith.setText("Smithing Table");
		smith.setToggleGroup(options);
		smith.getProperties().put("continue_to", SmithingScene.class.getName());

		RadioButton smoked = new RadioButton();
		smoked.setFont(CommonUtilities.getFont(FontType.TEXT));
		smoked.setText("Smoker");
		smoked.setToggleGroup(options);
		smoked.getProperties().put("continue_to", SmokerScene.class.getName());

		RadioButton cut = new RadioButton();
		cut.setFont(CommonUtilities.getFont(FontType.TEXT));
		cut.setText("Stonecutter");
		cut.setToggleGroup(options);
		cut.getProperties().put("continue_to", StonecuttingScene.class.getName());

		Button next = new Button();
		next.setFont(CommonUtilities.getFont(FontType.TEXT));
		next.setText("Continue");
		next.setOnAction(e -> {
			Main.switchScene(options.getSelectedToggle().getProperties().get("continue_to").toString());
		});

		options.selectToggle(cshape);

		root.getChildren().addAll(header, cshape, csl, blast, camp, smelt, smith, smoked, cut, next);
	}

}
