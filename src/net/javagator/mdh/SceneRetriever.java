package net.javagator.mdh;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public abstract class SceneRetriever {
	
	protected Scene scene;
	protected VBox root;
	
	public String sceneTitle;
	
	public abstract void buildScene();
	
	public Scene getScene() {
		return scene;
	};
	
	public SceneRetriever() {
		root = new VBox();
		root.setSpacing(15);
		scene = new Scene(root, 500, 600);
		buildScene();
	}
}