package org.gladia;

public class Utils {
	public static final String extrairNomeArquivo(String url) {
		if (isEmpty(url)) {
			return null;
		}

		return url.substring(url.lastIndexOf("/") + 1, url.length());
	}

	public static final String completarNumeracao(int numero, int qtDigitos) {
		String result = String.valueOf(numero);

		while (result.length() < qtDigitos) {
			result = "0" + result;
		}

		return result;
	}

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
