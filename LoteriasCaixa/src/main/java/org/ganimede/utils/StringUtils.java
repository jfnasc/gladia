package org.ganimede.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {

    public static String print(List<Integer> dezenas) {
        Integer[] result = new Integer[dezenas.size()];
        dezenas.toArray(result);
        return print(result);
    }

    public static String print(Integer[] arg0) {
        String s = "";
        for (int i = 0; i < arg0.length; i++) {
            if (arg0[i] < 10) {
                s += ", 0" + arg0[i];
            } else {
                s += ", " + arg0[i];
            }

        }
        System.out.println(s.substring(2));
        return s.substring(2);
    }

    public static Integer[] splitAsIntArray(String arg0) {
        return splitAsIntArray(arg0, false);
    }

    public static Integer[] splitAsIntArray(String arg0, boolean isOrdered) {
        if (arg0 == null) {
            return null;
        }

        String[] str = arg0.split("[,;]");

        return splitAsIntArray(arg0, str.length, isOrdered);
    }

    public static Integer[] splitAsIntArray(String arg0, int size, boolean isOrdered) {
        if (arg0 == null) {
            return null;
        }

        String[] str = arg0.split("[,;]");

        if (size > str.length) {
            size = str.length;
        }

        Integer[] result = new Integer[size];
        for (int i = 0; i < size; i++) {
            result[i] = Integer.valueOf(str[i]);
        }

        if (isOrdered) {
            Arrays.sort(result);
        }

        return result;
    }

    public static List<Integer> splitAsList(String arg0) {
        List<Integer> result = new ArrayList<Integer>();

        if (arg0 == null) {
            return null;
        }

        Integer[] dezenas = splitAsIntArray(arg0);
        for (Integer dezena : dezenas) {
            result.add(dezena);
        }

        return result;
    }

    public static String normalize(String s) {
        //
        // JDK1.5
        // use sun.text.Normalizer.normalize(s, Normalizer.DECOMP, 0);
        //
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
