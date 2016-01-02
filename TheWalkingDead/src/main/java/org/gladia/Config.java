package org.gladia;

import java.util.ResourceBundle;

public class Config {
	private static ResourceBundle rb;

	static {
		rb = ResourceBundle.getBundle("config");
	}

	public static final String getString(String key) {
		try {
	        return rb.getString(key);
        } catch (Exception e) {
	        e.printStackTrace();
        }
		return null;
	}

	public static void main(String[] args) {
		System.out.println(Config.getString("url.home"));
	}
}
