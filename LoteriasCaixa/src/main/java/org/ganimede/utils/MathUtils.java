package org.ganimede.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static String hash(final Integer[] dezenas) {
        return hash(Arrays.asList(dezenas));
    }

    public static String hash(final List<Integer> dezenas) {
        
        List<Integer> tmp = new ArrayList<>();
        tmp.addAll(dezenas);
        
        Collections.sort(tmp);

        StringBuilder sb = new StringBuilder();

        for (Integer dezena : tmp) {
            sb.append("#" + dezena);
        }

        return md5sum(sb.toString());
    }

    public static String md5sum(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println(MathUtils.fatorial(2));
        // System.out.println(MathUtils.fatorial(5));
        // System.out.println(MathUtils.csimples(5, 3));
        // System.out.println(MathUtils.csimples(60, 6));
        // System.out.println(MathUtils.csimples(60,
        // 6).divide(MathUtils.csimples(7, 6)));
        // System.out.println(MathUtils.csimples(15, 9));
        // System.out.println(MathUtils.csimples(25,
        // 15).divide(MathUtils.csimples(16, 15)));

        System.out.println(MathUtils.hash(new Integer[] { 3, 26, 30, 33, 37, 49 }));
        System.out.println(MathUtils.hash(new Integer[] { 2, 8, 11, 30, 32, 49 }));
    }
}
