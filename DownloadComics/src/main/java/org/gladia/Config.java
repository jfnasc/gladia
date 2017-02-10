package org.gladia;

import java.util.ResourceBundle;

public class Config {
	private static ResourceBundle rb;

	static {
		rb = ResourceBundle.getBundle("config");
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static final String getString(String key) {
		try {
			return rb.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static final Integer getInteger(String key) {
		try {
			return Integer.valueOf(rb.getString(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(Config.getString("url.home"));
	}
}
