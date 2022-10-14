package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UnusableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:block_state_property")
@UnusableIn({LootType.ENTITY, LootType.CHEST})
public class BlockStatePredicate extends AbstractLootPredicate {

	private SimpleStringProperty id;
	private SimpleBooleanProperty useRangeProperty;
	private SimpleBooleanProperty useProperties;
	private SimpleStringProperty blockstateProperty;
	private SimpleStringProperty blockstateValue;
	private SimpleDoubleProperty valueMin;
	private SimpleDoubleProperty valueMax;

	public BlockStatePredicate(String name) {
		super(name);
		id = new SimpleStringProperty();
		blockstateProperty = new SimpleStringProperty();
		blockstateValue = new SimpleStringProperty();
		valueMin = new SimpleDoubleProperty();
		valueMax = new SimpleDoubleProperty();
		useRangeProperty = new SimpleBooleanProperty();
		useProperties = new SimpleBooleanProperty();
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Block State Predicate");
		
		TextField blockId = new TextField();
		blockId.setFont(CommonUtilities.getFont(FontType.TEXT));
		blockId.setPromptText("Enter block ID...");
		blockId.setText(id.get());
		blockId.textProperty().bindBidirectional(id);
		
		CheckBox useProps = new CheckBox();
		useProps.setText("Require Block Properties?");
		useProps.setFont(CommonUtilities.getFont(FontType.TEXT));
		useProps.setSelected(useProperties.get());
		useProps.selectedProperty().bindBidirectional(useProperties);
		
		VBox props = new VBox();
		props.setSpacing(15d);
		props.visibleProperty().bindBidirectional(useProperties);
		
		TextField property = new TextField();
		property.setFont(CommonUtilities.getFont(FontType.TEXT));
		property.setPromptText("Enter property name...");
		property.setText(blockstateProperty.get());
		property.textProperty().bindBidirectional(blockstateProperty);
		
		CheckBox useRange = new CheckBox();
		useRange.setText("Use Value Range?");
		useRange.setFont(CommonUtilities.getFont(FontType.TEXT));
		useRange.setSelected(useRangeProperty.get());
		useRange.selectedProperty().bindBidirectional(useRangeProperty);
		
		VBox range = new VBox();
		range.setSpacing(15d);
		range.visibleProperty().bindBidirectional(useRangeProperty);
		
		TextField valueNoRange = new TextField();
		valueNoRange.setFont(CommonUtilities.getFont(FontType.TEXT));
		valueNoRange.setPromptText("Enter property value...");
		valueNoRange.setText(blockstateValue.get());
		valueNoRange.visibleProperty().bind(useRangeProperty.not());
		valueNoRange.managedProperty().bind(useRangeProperty.not());
		valueNoRange.textProperty().bindBidirectional(blockstateValue);
		
		TextField rangeMin = new TextField();
		rangeMin.setFont(CommonUtilities.getFont(FontType.TEXT));
		rangeMin.setPromptText("Enter minimum value...");
		rangeMin.setText(blockstateValue.get());
		rangeMin.textProperty().bindBidirectional(valueMin, new NumberStringConverter());
		
		TextField rangeMax = new TextField();
		rangeMax.setFont(CommonUtilities.getFont(FontType.TEXT));
		rangeMax.setPromptText("Enter maximum value...");
		rangeMax.setText(blockstateValue.get());
		rangeMax.textProperty().bindBidirectional(valueMin, new NumberStringConverter());
		
		range.getChildren().addAll(rangeMin, rangeMax);
		props.getChildren().addAll(property, useRange, valueNoRange, range);
		root.getChildren().addAll(header, blockId, useProps, props);
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		root.addProperty("block", id.get());
		if(useProperties.get()) {
			JsonObject props = new JsonObject();
			if(!useRangeProperty.get()) {
				props.addProperty(blockstateProperty.get(), blockstateValue.get());
			} else {
				JsonObject range = new JsonObject();
				range.addProperty("min", valueMin.get());
				range.addProperty("max", valueMax.get());
				props.add(blockstateProperty.get(), range);
			}
			root.add("properties", props);
		}
		return root;
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

}
