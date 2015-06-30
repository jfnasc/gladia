package org.ganimede.utils;

import java.util.Arrays;
import java.util.Random;

public class RndUtils {

    /**
	 * 
	 */
    static Random rnd = new Random(System.currentTimeMillis());

    /**
     * 
     * @return
     */
    public static int nextInt() {
        return rnd.nextInt();
    }

    /**
     * 
     * @param n
     * @return
     */
    public static int nextInt(int n) {
        return rnd.nextInt(n);
    }

    /**
     * 
     * @param n
     * @param m
     * @return
     */
    public static int nextInt(int n, int m) {
        if (m < n) {
            throw new IllegalArgumentException("m must be greater than n");
        }

        int result = 0;
        while ((result = nextInt(m)) < n) {
            // vamos ate achar o numero certo :)
        }

        return result;
    }

    public static Integer[] nextInts(int maiorDezena, int qtDezenas) {
        Integer[] result = new Integer[qtDezenas];
        for (int i = 0; i < qtDezenas; i++) {
            while (result[i] == null) {
                int p = nextInt(maiorDezena);
                if (!Arrays.asList(result).contains(p)) {
                    result[i] = p;
                }
            }
        }
        return result;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println(RndUtils.nextInt(60));
        // System.out.println(RndUtils.nextInt(1, 10));
        int[] p = new int[] { 18, 20, 25, 23, 10, 11, 24, 14, 6, 2, 13, 9, 5, 16, 3 };
        Integer[] pos = RndUtils.nextInts(15, 9);
        for (int i = 0; i < pos.length; i++) {
            System.out.print(p[pos[i]] + ",");
        }
    }
}
