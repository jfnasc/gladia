package org.gladia;

public class Utils {
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return Boolean.TRUE;
		}

		if (o instanceof String) {
			if (((String) o).trim().length() == 0) {
				return Boolean.TRUE;
			}
		}

		return false;
	}
}
