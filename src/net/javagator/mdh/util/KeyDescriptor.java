package net.javagator.mdh.util;

public class KeyDescriptor {
	
	private char key;
	private String val;
	
	public KeyDescriptor(char key, String val) {
		this.key = key;
		this.val = val;
	}

	public char getKey() {
		return key;
	}

	public String getVal() {
		return val;
	}
}
