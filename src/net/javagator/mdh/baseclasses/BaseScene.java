package net.javagator.mdh.baseclasses;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.javagator.mdh.util.CommonUtilities;

public abstract class BaseScene {
	
	protected int windowWidth = 500;
	protected int windowHeight = 600;
	
	protected Scene scene;
	protected VBox root;
	
	public String sceneTitle;
	
	public abstract void buildScene();
	
	public Scene getScene() {
		return scene;
	};
	
	public BaseScene() {
		root = new VBox();
		root.setSpacing(15);
		scene = new Scene(root, windowWidth, windowHeight);
		buildScene();
	};
	
	protected static void error(String description) {
		Alert msg = new Alert(AlertType.ERROR, description, ButtonType.OK);
		msg.setTitle("Error");
		ImageView graphic = new ImageView();
		graphic.setImage(CommonUtilities.getErrorImg());
		msg.setGraphic(graphic);
		msg.show();
	};
	
	protected static void success(String description) {
		Alert msg = new Alert(AlertType.INFORMATION, description, ButtonType.OK);
		msg.setTitle("Success!");
		ImageView graphic = new ImageView();
		graphic.setImage(CommonUtilities.getSuccessImg());
		msg.setGraphic(graphic);
		msg.setHeaderText("Success!");
		msg.show();
	};
	
	/*protected static void unsavedData() {
		Alert msg = new Alert(AlertType.WARNING, "You have unsaved data! Are you sure you want to proceed?", ButtonType.YES, ButtonType.NO);
		msg.setTitle("Warning!");
		ImageView graphic = new ImageView();
		graphic.setImage(CommonUtilities.getSuccessImg());
		msg.setGraphic(graphic);
		msg.setHeaderText("You have unsaved data!");
		msg.show();
	};*/
}