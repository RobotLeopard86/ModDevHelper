package net.rl86.mdh.loot.predicate;

import com.google.gson.JsonObject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import net.rl86.mdh.loot.util.IngameIdentifier;
import net.rl86.mdh.loot.util.LootMember;
import net.rl86.mdh.loot.util.UsableIn;
import net.rl86.mdh.util.CommonUtilities;
import net.rl86.mdh.util.CommonUtilities.FontType;
import net.rl86.mdh.util.CommonUtilities.LootType;

@IngameIdentifier("minecraft:weather_check")
@UsableIn({LootType.ALL})
public class WeatherPredicate extends AbstractLootPredicate {
	
	private SimpleBooleanProperty checkRain;
	private SimpleBooleanProperty checkThunder;

	public WeatherPredicate(String name) {
		super(name);
		checkRain = new SimpleBooleanProperty();
		checkThunder = new SimpleBooleanProperty();
	}

	@Override
	protected void generateTabContent() {
		Text header = new Text();
		header.setFont(CommonUtilities.getFont(FontType.HEADER));
		header.setText("Weather Check Predicate");
		
		CheckBox rain = new CheckBox();
		rain.setFont(CommonUtilities.getFont(FontType.TEXT));
		rain.setText("Is Raining?");
		rain.setSelected(checkRain.get());
		rain.selectedProperty().bindBidirectional(checkRain);
		
		CheckBox forcedThunder = new CheckBox();
		forcedThunder.setFont(CommonUtilities.getFont(FontType.TEXT));
		forcedThunder.setText("Is Thundering?");
		forcedThunder.setSelected(true);
		forcedThunder.setDisable(true);
		forcedThunder.visibleProperty().bind(checkRain);
		forcedThunder.managedProperty().bind(checkRain);
		
		CheckBox thunder = new CheckBox();
		thunder.setFont(CommonUtilities.getFont(FontType.TEXT));
		thunder.setText("Is Thundering?");
		thunder.setSelected(checkRain.get());
		thunder.selectedProperty().bindBidirectional(checkThunder);
		thunder.visibleProperty().bind(checkRain.not());
		thunder.managedProperty().bind(checkRain.not());
		
		root.getChildren().addAll(header, rain, forcedThunder, thunder);
	}

	@Override
	public LootMember[] getChildren() {
		return null;
	}

	@Override
	protected JsonObject generateJsonData() {
		JsonObject root = new JsonObject();
		root.addProperty("raining", checkRain.get());
		root.addProperty("thundering", checkThunder.get());
		return root;
	}

}
