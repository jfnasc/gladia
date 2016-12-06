package br.com.sisfashion.utils;

public class ObjectUtils {

	public static boolean isEmpty(Object o) {

		if (o != null) {
			if (o instanceof String) {
				return ((String) o).trim().length() == 0;
			}
		}

		return false;
	}

	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
}
