package net.rl86.mdh.scenes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.rl86.mdh.Main;
import net.rl86.mdh.baseclasses.BaseScene;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.TextureAtPosDescriptor;
import net.rl86.mdh.util.CommonUtilities.Direction;
import net.rl86.mdh.util.CommonUtilities.FontType;

public class CubeModelScene extends BaseScene {
	
	private ImageView[] views;
	private ArrayList<Integer> chosenViews;

	@Override
	public void buildScene() {
		sceneTitle = "Mod Development Helper | Cube Model Creator";
		
		Text header = new Text();
		header.setText("Cube Block Models");
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		
		Button exit = new Button();
		exit.setFont(CommonUtilities.getFont(FontType.TEXT));
		exit.setText("Return to Menu");
		exit.setOnAction(e -> {
			Main.switchScene(MenuScene.class.getName());
		});
		
		VBox selections = manufactureSelectionBoxes();
		
		Button picker = new Button();
		picker.setFont(CommonUtilities.getFont(FontType.TEXT));
		picker.setText("Pick Texture");
		picker.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("PNG Images", "*.png"));
			
			File img = fc.showOpenDialog(Main.getStage());
			
			if(img != null) {
				Dialog<ButtonType> dialog = constructApplyTextureDialog();
				
				if(dialog.showAndWait().get() != ButtonType.CANCEL) {
					for(int chosenView : chosenViews) {
						views[chosenView].setImage(new Image("file:" + img.getAbsolutePath(), 64, 64, true, true, false));
						views[chosenView].getProperties().put("name", img.getName().substring(0, img.getName().length() - 4));
					}
				}
			}
		});
		
		Button generate = new Button();
		generate.setFont(CommonUtilities.getFont(FontType.TEXT));
		generate.setText("Generate Model");
		generate.setOnAction(e -> {
			Alert msg = new Alert(AlertType.CONFIRMATION, "Are you sure you have picked the textures you want?", ButtonType.YES, ButtonType.NO);
			if(msg.showAndWait().get() == ButtonType.YES) {
				boolean error = false;
				for(ImageView view : views) {
					if(!view.getProperties().containsKey("name")) {
						error = true;
						break;
					}
				}
				
				if(error) {
					error("All faces must have a texture!");
					return;
				}
				
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
				
				ChoiceDialog<Direction> particleDialog = new ChoiceDialog<>();
				particleDialog.setHeaderText("Which face's texture should be used for the break particle?");
				particleDialog.setTitle("Input Request");
				particleDialog.setGraphic(null);
				particleDialog.getItems().addAll(Direction.Top, Direction.Bottom, Direction.East, Direction.West, Direction.North, Direction.South);
				particleDialog.setSelectedItem(Direction.Top);
				particleDialog.setOnCloseRequest(event -> {
					boolean shouldConsume = true;
					
					if(particleDialog.getResult() != null) {
						shouldConsume = false;
					} else {
						error("You must select an option! :(");
					}
					
					if(shouldConsume) {
						event.consume();
					}
				});
				
				String modId = modIdDialog.showAndWait().get();
				
				Direction particle = particleDialog.showAndWait().get();
				
				if(modId == "") {
					error("You must enter a mod ID!");
					return;
				}
				
				TextureAtPosDescriptor[] textureValues = new TextureAtPosDescriptor[6];
				for(int i = 0; i < views.length; i++) {
					textureValues[i] = new TextureAtPosDescriptor(modId + ":block/" + views[i].getProperties().get("name"), (Direction)views[i].getProperties().get("dir"));
				}
				
				JsonObject jsonRoot = new JsonObject();
				jsonRoot.addProperty("parent", "minecraft:block/cube");
				
				JsonObject textures = new JsonObject();
				for(TextureAtPosDescriptor descriptor : textureValues) {
					descriptor.addToJson(textures);
				}
				
				textures.addProperty("particle", textureValues[particle.idx].getResourceLocation());
				
				jsonRoot.add("textures", textures);
				
				String json = Main.getGson().toJson(jsonRoot);
				
				FileChooser fc = new FileChooser();
				fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
				File fullPath = fc.showSaveDialog(Main.getStage());
				
				if(!fullPath.exists()) {
					try {
						fullPath.createNewFile();
					} catch (IOException exception) {
						exception.printStackTrace();
						error("Couldn't create file as it already exists! Please try again. :(\nFile: " + fullPath.getAbsolutePath());
						return;
					}
				}
				
				try {
					PrintWriter writer = new PrintWriter(fullPath);
					writer.print(json);
					writer.flush();
					writer.close();
				} catch (FileNotFoundException exception) {
					exception.printStackTrace();
					error("Couldn't find file to write to! :(\nFile: " + fullPath.getAbsolutePath());
				}
				
				for(ImageView view : views) {
					view.getProperties().remove("name");
					view.setImage(null);
				}
				
				Main.switchScene(MenuScene.class.getName());
				success("Successfully created model!");
				return;
			}
		});
		
		root.getChildren().addAll(header, exit, selections, picker, generate);
	}
	
	private VBox manufactureSelectionBox(Direction dir) {
		VBox layout = new VBox();
		layout.setSpacing(10d);
		
		Text label = new Text();
		label.setFont(CommonUtilities.getFont(FontType.TEXT));
		label.setText(dir.toString() + " Face Texture:");
		
		ImageView view = new ImageView();
		view.getProperties().put("dir", dir);
		views[dir.idx] = view;
		
		layout.getChildren().addAll(label, view);
		
		return layout;
	}
	
	private HBox manufactureRow(Direction first, Direction second) {
		HBox layout = new HBox();
		layout.setSpacing(15d);
		
		layout.getChildren().addAll(manufactureSelectionBox(first), manufactureSelectionBox(second));
		
		return layout;
	}
	
	private VBox manufactureSelectionBoxes() {
		VBox layout = new VBox();
		layout.setSpacing(10d);
		
		views = new ImageView[6];
		
		layout.getChildren().addAll(manufactureRow(Direction.North, Direction.South), manufactureRow(Direction.East, Direction.West), manufactureRow(Direction.Top, Direction.Bottom));
		
		return layout;
	}
	
	private Dialog<ButtonType> constructApplyTextureDialog(){
		VBox layout = new VBox();
		layout.setSpacing(15d);
		
		CheckBox north = new CheckBox();
		north.setFont(CommonUtilities.getFont(FontType.TEXT));
		north.setText("North");
		
		CheckBox south = new CheckBox();
		south.setFont(CommonUtilities.getFont(FontType.TEXT));
		south.setText("South");
		
		CheckBox east = new CheckBox();
		east.setFont(CommonUtilities.getFont(FontType.TEXT));
		east.setText("East");
		
		CheckBox west = new CheckBox();
		west.setFont(CommonUtilities.getFont(FontType.TEXT));
		west.setText("West");
		
		CheckBox top = new CheckBox();
		top.setFont(CommonUtilities.getFont(FontType.TEXT));
		top.setText("Top");
		
		CheckBox bottom = new CheckBox();
		bottom.setFont(CommonUtilities.getFont(FontType.TEXT));
		bottom.setText("Bottom");
		
		DialogPane pane = new DialogPane();
		pane.setContent(layout);
		pane.getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
		
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Texture Manager");
		dialog.setDialogPane(pane);
		dialog.setHeaderText("Which faces should this texture apply to?");
		dialog.setOnCloseRequest(e -> {
			if(dialog.getResult() != ButtonType.CANCEL) {
				chosenViews = new ArrayList<>();
				if(north.isSelected()) {
					chosenViews.add(Direction.North.idx);
				}
				if(south.isSelected()) {
					chosenViews.add(Direction.South.idx);
				}
				if(east.isSelected()) {
					chosenViews.add(Direction.East.idx);
				}
				if(west.isSelected()) {
					chosenViews.add(Direction.West.idx);
				}
				if(top.isSelected()) {
					chosenViews.add(Direction.Top.idx);
				}
				if(bottom.isSelected()) {
					chosenViews.add(Direction.Bottom.idx);
				}
			}
		});
		
		layout.getChildren().addAll(north, south, east, west, top, bottom);
		
		return dialog;
	}

}
