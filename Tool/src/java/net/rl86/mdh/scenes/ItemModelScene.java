package net.rl86.mdh.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import net.rl86.mdh.Main;
import net.rl86.mdh.scenes.itemmodels.ItemModelFromBlockModelScene;
import net.rl86.mdh.scenes.itemmodels.ItemModelFromTextureScene;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;

public class ItemModelScene extends BaseScene {

	@Override
	public void buildScene() {
		warnOnExit = false;
		sceneTitle = "Mod Development Helper | Item Model Designer";

		Text header = new Text();
		header.setText("Item Models");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));

		ToggleGroup options = new ToggleGroup();

		RadioButton texture = new RadioButton();
		texture.setFont(CommonUtilities.getFont(FontType.TEXT));
		texture.setText("Texture");
		texture.setToggleGroup(options);

		RadioButton model = new RadioButton();
		model.setFont(CommonUtilities.getFont(FontType.TEXT));
		model.setText("Block Model");
		model.setToggleGroup(options);

		Button next = new Button();
		next.setFont(CommonUtilities.getFont(FontType.TEXT));
		next.setText("Continue");
		next.setOnAction(e -> {
			Main.switchScene(options.getSelectedToggle().equals(texture) ? ItemModelFromTextureScene.class.getName() : ItemModelFromBlockModelScene.class.getName());
		});

		options.selectToggle(texture);

		root.getChildren().addAll(header, texture, model, next);
	}

}
