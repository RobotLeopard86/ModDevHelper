package net.javagator.mdh.scenes.blockstates;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.javagator.mdh.Main;
import net.javagator.mdh.SceneRetriever;

public class BlockstateFieldCreatorScene extends SceneRetriever {

	private boolean addedPropertyName;
	private int numProperties;
	private DialogPane pane;
	private Dialog<ButtonType> dialog;
	private TextField nameBox;
	private TreeItem<String> tree;
	private TreeView<String> treeview;
	private Button add;
	private boolean enterPressedForProperty;
	
	private static HashMap<String,String[]> blockstateData = new HashMap<>();
	
	@Override
	public Scene getScene() {
		blockstateData.clear();
		return scene;
	}
	
	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Blockstate File Creator";
		addedPropertyName = false;
		numProperties = 0;
		
		Text header = new Text();
		header.setText("Blockstates");
		header.setFont(Main.headerFont);
		
		Button exit = new Button();
		exit.setFont(Main.textFont);
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene("menu");
		});
		
		TreeItem<String> treeRoot = new TreeItem<>();
		treeRoot.setValue("Properties:");
		treeRoot.setExpanded(true);
		
		TreeView<String> viewOfEverything = new TreeView<>();
		viewOfEverything.setRoot(treeRoot);
		
		Button addProperty = new Button();
		addProperty.setFont(Main.textFont);
		addProperty.setText("Add New Property");
		addProperty.setOnAction(e -> {
			addedPropertyName = false;
			numProperties = 0;
			Dialog<ButtonType> menu = buildNewPropertyDialog();
			if(menu.showAndWait().get() == ButtonType.FINISH) {
				String[] values = new String[tree.getChildren().size()];
				for(int i = 0; i < values.length; i++){
					TreeItem<String> item = tree.getChildren().get(i);
					values[i] = item.getValue();
				}
				blockstateData.put(tree.getValue(), values);
				treeRoot.getChildren().add(tree);
			}
		});
		
		Button complete = new Button();
		complete.setFont(Main.textFont);
		complete.setText("Finish");
		complete.setOnAction(e -> {
			Alert msg = new Alert(AlertType.CONFIRMATION, "Are you sure you are finished creating blockstates? This cannot be changed once you proceed.", ButtonType.YES, ButtonType.NO);
			if(msg.showAndWait().get() == ButtonType.YES) {
				BlockstateFieldEditorScene.setData(blockstateData);
				Main.switchScene("bs2");
			}
		});
		
		root.getChildren().addAll(header, exit, addProperty, viewOfEverything, complete);
	}
	
	private Dialog<ButtonType> buildNewPropertyDialog() {	
		VBox content = new VBox();
		content.setSpacing(10);
		
		pane = new DialogPane();
		pane.setContent(content);
		pane.setHeaderText("Add Property");
		pane.getButtonTypes().add(ButtonType.CANCEL);
		
		dialog = new Dialog<>();
		dialog.setTitle("Create New Property");
		dialog.setDialogPane(pane);
		dialog.setWidth(700);
		dialog.setHeight(400);
		dialog.setOnCloseRequest(e -> {
			if(enterPressedForProperty) {
				enterPressedForProperty = false;
				e.consume();
			}
		});
		
		nameBox = new TextField();
		nameBox.setPromptText("Enter property name...");
		nameBox.setFont(Main.textFont);
		nameBox.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER && nameBox.isFocused()) {
				enterPressedForProperty = true;
				updateTree();
			}
		});
		
		tree = new TreeItem<>();
		tree.setExpanded(true);
		
		treeview = new TreeView<>();
		treeview.setEditable(false);
		treeview.setVisible(false);
		treeview.setRoot(tree);
		
		add = new Button();
		add.setFont(Main.textFont);
		add.setText("Add Property");
		add.setOnAction(e -> updateTree());
		
		content.getChildren().addAll(nameBox, treeview, add);
		
		return dialog;
	}
	
	private void updateTree() {
		if(nameBox.getText().equalsIgnoreCase("")) {
			Main.error("You have not provided a value! :(");
		} else {
			if(addedPropertyName) {
				numProperties++;
				TreeItem<String> newValue = new TreeItem<>();
				newValue.setValue(nameBox.getText());
				nameBox.setText("");
				if(numProperties == 2) {
					pane.getButtonTypes().add(ButtonType.FINISH);
				}
				tree.getChildren().add(newValue);
			} else {
				addedPropertyName = true;
				add.setText("Add Value");
				nameBox.setPromptText("Enter possible value...");
				tree.setValue(nameBox.getText());
				nameBox.setText("");
				treeview.setVisible(true);
			}
		}
	}

}
