package net.rl86.mdh.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class CommonUtilities {

	public static enum FontType {HEADER, TEXT}
	public static enum Direction {
		North(0, "north"),
		South(1, "south"),
		West(2, "west"),
		East(3, "east"),
		Top(4, "up"),
		Bottom(5, "down");

		public int idx;
		public String mappedVal;

		private Direction(int index, String mcKeymap) {
			idx = index;
			mappedVal = mcKeymap;
		}
	}
	public static enum LootType {
		BLOCK("Block Drops", "minecraft:block"),
		ENTITY("Entity Drops", "minecraft:entity"),
		CHEST("Chest Loot", "minecraft:chest"),
		ALL;

		private String id;
		private String inGameID;

		private LootType(String name, String ingame) {
			id = name;
			inGameID = ingame;
		}
		
		private LootType() {}

		@Override
		public String toString() {
			return id;
		}

		public String getInGameID() {
			return inGameID;
		}
	}

	private static Font headerFont;
	private static Font textFont;

	private static Image errMsgImg;
	private static Image yayMsgImg;
	private static Image warnMsgImg;
	private static Image appIcon;

	public static Font getFont(FontType type) {
		Font value = null;
		switch(type) {
		case HEADER:
			value = headerFont;
			break;
		case TEXT:
			value = textFont;
			break;
		}
		return value;
	}

	public static void initializeFonts() {
		BufferedInputStream his = null;
		BufferedInputStream tis = null;
		try {
			his = new BufferedInputStream(new URL("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/Tool/src/resources/fonts/redhatdisplay.ttf").openStream());
			tis = new BufferedInputStream(new URL("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/Tool/src/resources/fonts/redhattext.ttf").openStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Alert msg = new Alert(AlertType.ERROR, "Couldn't load font from malformed URL! :(", ButtonType.OK);
			msg.setTitle("Error");
			ImageView graphic = new ImageView();
			graphic.setImage(CommonUtilities.getErrorImg());
			msg.setGraphic(graphic);
			msg.show();
		} catch (IOException e) {
			e.printStackTrace();
			Alert msg = new Alert(AlertType.ERROR, "Couldn't load font due to IO exception! If you are offline, please obtain a connection. :(", ButtonType.OK);
			msg.setTitle("Error");
			ImageView graphic = new ImageView();
			graphic.setImage(CommonUtilities.getErrorImg());
			msg.setGraphic(graphic);
			msg.show();
		}
		headerFont = Font.loadFont(his, 36f);
		textFont = Font.loadFont(tis, 18f);
	}

	public static void initializeImages() {
		errMsgImg = new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/Tool/src/resources/images/error.png", 64, 64, true, true, true);
		yayMsgImg = new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/Tool/src/resources/images/success.png", 64, 64, true, true, true);
		warnMsgImg = new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/Tool/src/resources/images/warning.png", 64, 64, true, true, true);
		appIcon = new Image("https://raw.githubusercontent.com/RobotLeopard86/ModDevHelper/main/Tool/src/resources/images/appicon.png");
	}

	public static Image getErrorImg() {
		return errMsgImg;
	}

	public static Image getSuccessImg() {
		return yayMsgImg;
	}

	public static Image getWarningImg() {
		return warnMsgImg;
	}

	public static Image getIcon() {
		return appIcon;
	}
}