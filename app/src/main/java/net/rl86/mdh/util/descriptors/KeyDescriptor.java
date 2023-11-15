package net.rl86.mdh.util.descriptors;

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
