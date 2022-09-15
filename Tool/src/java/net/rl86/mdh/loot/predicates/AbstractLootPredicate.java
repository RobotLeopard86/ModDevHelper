package net.rl86.mdh.loot.predicates;

import com.google.gson.JsonObject;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;

public abstract class AbstractLootPredicate {
	
	protected AbstractLootPredicate(String name) {
		this.name = name;
	}
	
	public abstract JsonObject generateJson();
	
	public String name;
	
	protected abstract void addDialogContentToBox(VBox source);
	
	protected abstract void applyChangesToSelf(VBox dialogData);
	
	public final Dialog<Integer> buildDialog(){
		VBox content = new VBox();
		content.setSpacing(15d);
		
		DialogPane pane = new DialogPane();
		pane.setContent(content);
		pane.getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
		
		Dialog<Integer> dialog = new Dialog<>();
		dialog.setTitle("Predicate Editor");
		dialog.setHeaderText("Predicate Editor");
		dialog.setDialogPane(pane);
		
		((Button) pane.lookupButton(ButtonType.APPLY)).setOnAction(e -> {
			applyChangesToSelf(content);
			dialog.setResult(0);
		});
		
		((Button) pane.lookupButton(ButtonType.CANCEL)).setOnAction(e -> {
			dialog.setResult(1);
		});
		
		addDialogContentToBox(content);
		
		return dialog;
	}
	
	@Override
	public String toString() {
		return name + " <" + this.getClass().getAnnotation(ResourceName.class).value() + ">";
	}
	
}
