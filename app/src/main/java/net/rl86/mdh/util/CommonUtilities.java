package net.rl86.mdh.util;

import java.io.InputStream;

import javafx.scene.image.Image;
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
	
	public static InputStream loadResource(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}

	public static void initializeFonts() {
		headerFont = Font.loadFont(loadResource("fonts/redhatdisplay.ttf"), 36f);
		textFont = Font.loadFont(loadResource("fonts/redhattext.ttf"), 18f);
	}

	public static void initializeImages() {
		errMsgImg = new Image(loadResource("images/error.png"), 128, 128, true, true);
		yayMsgImg = new Image(loadResource("images/success.png"), 128, 128, true, true);
		warnMsgImg = new Image(loadResource("images/warning.png"), 128, 128, true, true);
		appIcon = new Image(loadResource("images/appicon.png"), 128, 128, true, true);
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