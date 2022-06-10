package net.javagator.mdh.scenes.blockstates;

import java.util.HashMap;

import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class BlockstateFieldEditorScene extends SceneRetriever {
	
	private static HashMap<String,String[]> data = new HashMap<>();
	private static Text remaining;

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Blockstate File Creator";
		
		Text header = new Text();
		header.setText("Blockstates");
		header.setFont(Main.headerFont);
		
		remaining = new Text();
		remaining.setFont(Main.textFont);
		
		root.getChildren().addAll(header, remaining);
	}
	
	public static void setData(HashMap<String,String[]> input) {
		data.clear();
		data.putAll(input);
		remaining.setText("0 of " + (data.isEmpty() ? "1" : calculateTotalOutcomes()) + " Complete");
	}
	
	private static int calculateTotalOutcomes() {
		int outcomes = 0;
		
		return outcomes;
	}

}