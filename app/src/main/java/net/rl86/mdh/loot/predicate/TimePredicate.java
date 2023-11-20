package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;
import net.rl86.mdh.util.NumberField;

@IngameIdentifier("minecraft:time_check")
@UsableIn({LootType.ALL})
public class TimePredicate extends AbstractLootPredicate {
	
	private SimpleDoubleProperty exactValue;
	private SimpleDoubleProperty valueMin;
	private SimpleDoubleProperty valueMax;
	private SimpleDoubleProperty modulo;
	private SimpleBooleanProperty useRange;
	private SimpleBooleanProperty usePeriod;

	public TimePredicate() {
		super();
		exactValue = new SimpleDoubleProperty();
		valueMin = new SimpleDoubleProperty();
		valueMax = new SimpleDoubleProperty();
		modulo = new SimpleDoubleProperty();
		useRange = new SimpleBooleanProperty();
		usePeriod = new SimpleBooleanProperty();
	}

	@Override
	protected void generateEditorContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Time Check Predicate");
		
		CheckBox useValueRange = new CheckBox();
		useValueRange.setText("Use Value Range?");
		useValueRange.setFont(CommonUtilities.getFont(FontType.TEXT));
		useValueRange.setSelected(useRange.get());
		useValueRange.selectedProperty().bindBidirectional(useRange);
		
		NumberField exact = new NumberField(exactValue.get());
		exact.setFont(CommonUtilities.getFont(FontType.TEXT));
		exact.setPromptText("Enter exact time value to check for...");
		exact.visibleProperty().bind(useRange.not());
		exact.managedProperty().bind(useRange.not());
		
		NumberField min = new NumberField(exactValue.get());
		min.setFont(CommonUtilities.getFont(FontType.TEXT));
		min.setPromptText("Enter minimum time value...");
		min.visibleProperty().bind(useRange);
		min.managedProperty().bind(useRange);
		
		NumberField max = new NumberField(exactValue.get());
		max.setFont(CommonUtilities.getFont(FontType.TEXT));
		max.setPromptText("Enter maximum time value...");
		max.visibleProperty().bind(useRange);
		max.managedProperty().bind(useRange);
		
		CheckBox period = new CheckBox();
		period.setText("Use Modulus Reducer?");
		period.setFont(CommonUtilities.getFont(FontType.TEXT));
		period.setSelected(usePeriod.get());
		period.selectedProperty().bindBidirectional(usePeriod);
		
		NumberField periodField = new NumberField(modulo.get());
		periodField.setFont(CommonUtilities.getFont(FontType.TEXT));
		periodField.setPromptText("Enter modulus value...");
		periodField.visibleProperty().bind(usePeriod);
		periodField.managedProperty().bind(usePeriod);
		
		root.getChildren().addAll(header, useValueRange, exact, min, max, period, periodField);
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		if(useRange.get()) {
			JsonObject range = new JsonObject();
			range.addProperty("min", valueMin.get());
			range.addProperty("max", valueMax.get());
			root.add("value", range);
		} else {
			root.addProperty("value", exactValue.get());
		}
		if(usePeriod.get()) {
			root.addProperty("period", modulo.get());
		}
		return root;
	}

}
