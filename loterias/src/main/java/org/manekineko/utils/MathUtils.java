package org.manekineko.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

	/**
	 * 
	 * @param n
	 * @return
	 */
	public static BigDecimal fatorial(int n) {
		if (n == 0) {
			return BigDecimal.ZERO;
		}

		BigDecimal result = new BigDecimal(1);
		while (n > 0) {
			result = result.multiply(new BigDecimal(n--));
		}
		return result;
	}

	public static BigDecimal csimples(int n, int p) {
		if (n == 0 || p == 0) {
			return BigDecimal.ZERO;
		}

		return fatorial(n).divide(fatorial(p).multiply(fatorial(n - p)), RoundingMode.CEILING);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(MathUtils.fatorial(2));
		System.out.println(MathUtils.fatorial(5));
		System.out.println(MathUtils.csimples(5, 3));
		System.out.println(MathUtils.csimples(60, 6));
		System.out.println(MathUtils.csimples(60, 6).divide(MathUtils.csimples(7,6)));
		System.out.println(MathUtils.csimples(15, 9));
		System.out.println(MathUtils.csimples(25, 15).divide(MathUtils.csimples(16,15)));
	}
}
