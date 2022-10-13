package net.rl86.mdh.scenes.blockstates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.rl86.mdh.Main;
import net.rl86.mdh.scenes.MenuScene;
import net.rl86.mdh.util.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.descriptors.ConditionDescriptor;
import net.rl86.mdh.util.descriptors.ModelDescriptor;

public class BlockstateFieldEditorScene extends BaseScene {

	private static HashMap<String,String[]> data = new HashMap<>();
	private static String[] keys;
	private static Text remaining;
	private static int completed;
	private static ConditionDescriptor[] combos;
	private static ModelDescriptor[] models;

	private static File mdl;

	private static Text conditions;
	private static Slider x;
	private static Slider y;
	private static Slider z;
	private static Text xDisplay;
	private static Text yDisplay;
	private static Text zDisplay;
	private static Text filePath;

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Blockstate File Creator";

		Text header = new Text();
		header.setText("Blockstates");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));

		remaining = new Text();
		remaining.setFont(CommonUtilities.getFont(FontType.TEXT));

		conditions = new Text();
		conditions.setFont(CommonUtilities.getFont(FontType.TEXT));
		conditions.setText("Condition:");

		filePath = new Text();
		filePath.setFont(CommonUtilities.getFont(FontType.TEXT));
		filePath.setText("Selected Model: ???");
		filePath.setWrappingWidth(scene.getWidth());

		Button chooseModel = new Button();
		chooseModel.setFont(CommonUtilities.getFont(FontType.TEXT));
		chooseModel.setText("Choose Model File");
		chooseModel.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose a model file:");
			fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			mdl = fc.showOpenDialog(Main.getStage());
			filePath.setText("Selected Model: " + mdl.getAbsolutePath());
		});

		HBox xPane = new HBox();
		xPane.setSpacing(10d);

		HBox yPane = new HBox();
		yPane.setSpacing(10d);

		HBox zPane = new HBox();
		zPane.setSpacing(10d);

		xDisplay = new Text();
		xDisplay.setFont(CommonUtilities.getFont(FontType.TEXT));
		xDisplay.setText("X Rotation: 0");

		yDisplay = new Text();
		yDisplay.setFont(CommonUtilities.getFont(FontType.TEXT));
		yDisplay.setText("Y Rotation: 0");

		zDisplay = new Text();
		zDisplay.setFont(CommonUtilities.getFont(FontType.TEXT));
		zDisplay.setText("Z Rotation: 0");

		x = new Slider();
		x.setMax(359);
		x.setMin(0);
		x.setPrefWidth(450);
		x.valueProperty().addListener(e -> {
			xDisplay.setText("X Rotation: " + (int)x.getValue());
		});

		y = new Slider();
		y.setMax(359);
		y.setMin(0);
		y.setPrefWidth(450);
		y.valueProperty().addListener(e -> {
			yDisplay.setText("Y Rotation: " + (int)y.getValue());
		});

		z = new Slider();
		z.setMax(359);
		z.setMin(0);
		z.setPrefWidth(450);
		z.valueProperty().addListener(e -> {
			zDisplay.setText("Z Rotation: " + (int)z.getValue());
		});

		xPane.getChildren().addAll(xDisplay, x);
		yPane.getChildren().addAll(yDisplay, y);
		zPane.getChildren().addAll(zDisplay, z);

		Button next = new Button();
		next.setFont(CommonUtilities.getFont(FontType.TEXT));
		next.setText("Continue");
		next.setOnAction(e -> {
			Alert msg = new Alert(AlertType.CONFIRMATION, "Are you sure this is the configuration you want? This cannot be changed later.", ButtonType.YES, ButtonType.NO);
			if(msg.showAndWait().get() == ButtonType.YES) {
				if(mdl == null) {
					error("You have not selected a model! :(");
					return;
				}
				models[completed] = new ModelDescriptor(mdl, (int)x.getValue(), (int)y.getValue(), (int)z.getValue());
				updateView();
			}
		});

		root.getChildren().addAll(header, remaining, conditions, chooseModel, filePath, xPane, yPane, zPane, next);
		root.getChildren().remove(back);
	}

	public static void setData(HashMap<String,String[]> input, String[] keylist) {
		data.clear();
		data.putAll(input);
		keys = keylist;
		completed = -1;
		combos = combinations(calculateTotalOutcomes());

		updateView();
	}

	private static void updateView() {
		completed++;
		if(completed >= combos.length) {
			JsonObject jsonRoot = new JsonObject();
			JsonObject variants = new JsonObject();

			TextInputDialog modIdDialog = new TextInputDialog();
			modIdDialog.getEditor().setFont(CommonUtilities.getFont(FontType.TEXT));
			modIdDialog.getEditor().setPromptText("Enter mod ID...");
			modIdDialog.setHeaderText("Enter mod ID...");
			modIdDialog.setGraphic(null);
			modIdDialog.setTitle("Input Request");
			modIdDialog.setOnCloseRequest(event -> {
				boolean shouldConsume = true;

				if(modIdDialog.getResult() != "" && modIdDialog.getResult() != null && modIdDialog.getEditor().getText() != "") {
					shouldConsume = false;
				} else {
					error("You must enter a mod ID! :(");
				}

				if(shouldConsume) {
					event.consume();
				}
			});

			String modID = modIdDialog.showAndWait().get();

			for(int i = 0; i < combos.length; i++) {
				variants.add(combos[i].generateJsonLabel(), models[i].constructJson(modID));
			}
			jsonRoot.add("variants", variants);

			String json = Main.getGson().toJson(jsonRoot);

			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			File fullPath = fc.showSaveDialog(Main.getStage());

			if(!fullPath.exists()) {
				try {
					fullPath.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					error("Couldn't create file as it already exists! Please try again. :(\nFile: " + fullPath.getAbsolutePath());
					return;
				}
			}

			try {
				PrintWriter writer = new PrintWriter(fullPath);
				writer.print(json);
				writer.flush();
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				error("Couldn't find file to write to! :(\nFile: " + fullPath.getAbsolutePath());
			}
			Main.switchScene(MenuScene.class.getName());
			success("Successfully wrote to blockstate file!");
			return;
		}
		remaining.setText(completed + " of " + calculateTotalOutcomes() + " Completed");
		conditions.setText(combos[completed].toString());
		mdl = null;
		x.setValue(0);
		y.setValue(0);
		z.setValue(0);
		xDisplay.setText("X Rotation: 0");
		yDisplay.setText("Y Rotation: 0");
		zDisplay.setText("Z Rotation: 0");
		filePath.setText("Selected Model: ???");
	}

	private static int calculateTotalOutcomes() {
		int outcomes = 1;

		for (String key : keys) {
			outcomes *= data.get(key).length;
		}

		return outcomes;
	}

	private static ConditionDescriptor[] combinations(int numcombos) {
		ConditionDescriptor[] combinations = new ConditionDescriptor[numcombos];
		models = new ModelDescriptor[numcombos];

		if(numcombos > 1) {
			ArrayList<String[]> arrays = new ArrayList<>();
			for (String key : keys) {
				arrays.add(data.get(key));
			}

			for (String[] array : arrays) {
				for(int j = 0; j < array.length; j++) {
					array[j] = array[j] + ",";
				}
			}

			ArrayList<String> output = new ArrayList<>();

			generatePermutations(arrays, output, 0, "");

			for(int i = 0; i < output.size(); i++) {
				String[] splits = output.get(i).split(",");
				String[] conds = data.keySet().toArray(new String[0]);
				combinations[i] = new ConditionDescriptor(conds, splits);
			}
		} else {
			combinations[0] = new ConditionDescriptor();
		}

		return combinations;
	}

	private static void generatePermutations(ArrayList<String[]> lists, ArrayList<String> result, int depth, String current) {
	    if (depth == lists.size()) {
	        result.add(current);
	        return;
	    }

	    for (int i = 0; i < lists.get(depth).length; i++) {
	        generatePermutations(lists, result, depth + 1, current + lists.get(depth)[i]);
	    }
	}

}