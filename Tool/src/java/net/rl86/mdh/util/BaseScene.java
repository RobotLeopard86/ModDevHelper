package net.rl86.mdh.util;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.rl86.mdh.Main;
import net.rl86.mdh.scenes.MenuScene;
import net.rl86.mdh.util.CommonUtilities.FontType;

public abstract class BaseScene {
	
	protected int windowWidth = 500;
	protected int windowHeight = 600;
	
	protected Scene scene;
	protected VBox root;
	
	public String sceneTitle;

	protected String returnToScene;
	protected boolean warnOnExit = true;
	
	protected Button back;
	
	public abstract void buildScene();
	
	protected void setDimensions() {
		//lol do nothing
	}
	
	public Scene getScene() {
		return scene;
	};
	
	public BaseScene() {
		root = new VBox();
		root.setSpacing(15);
		
		setDimensions();
		
		scene = new Scene(root, windowWidth, windowHeight);
		returnToScene = MenuScene.class.getName();
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Back");
		exit.setOnAction(e -> {
			if(warnOnExit) {
				if(warning("Are you sure you want to exit? You will lose all data!") != ButtonType.YES) return;
			}
			Main.switchScene(returnToScene);
		});
		root.getChildren().add(exit);
		
		back = exit;
		
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
	
	protected static ButtonType warning(String description) {
		Alert msg = new Alert(AlertType.WARNING, description, ButtonType.YES, ButtonType.NO);
		msg.setTitle("Warning");
		ImageView graphic = new ImageView();
		graphic.setImage(CommonUtilities.getWarningImg());
		msg.setGraphic(graphic);
		msg.setHeaderText("Warning!");
		return msg.showAndWait().get();
	};
	
	public Button getBackBtn() {
		return back;
	}
}