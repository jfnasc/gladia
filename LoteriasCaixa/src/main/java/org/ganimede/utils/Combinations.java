package org.ganimede.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinations {

    public static void main(String[] args) {
        Integer[] base = new Integer[] { 6, 7, 8, 9, 10 };

        List<Integer[]> a = Combinations.comb(base, 3);
        for (Integer[] i : a) {
            System.out.println(Arrays.toString(i));
        }
    }

    public static List<Integer[]> comb(Integer[] m, Integer n) {
        List<Integer[]> result = comb(m.length, n);
        for (Integer[] aposta : result) {
            for (int i = 0; i < aposta.length; i++) {
                aposta[i] = m[aposta[i]];
            }
        }
        return result;
    }

    public static List<Integer[]> comb(Integer m, Integer n) {
        List<Integer[]> result = new ArrayList<>();

        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }

        int index = -1;

        while ((index = nextIndex(m, n, a)) != -1) {
            result.add(Arrays.copyOf(a, a.length));
            move(index, a);
        }

        result.add(Arrays.copyOf(a, a.length));

        return result;
    }

    private static void move(int index, Integer[] a) {
        a[index] = a[index] + 1;
        if (index < a.length - 1) {
            for (int i = index; i < a.length - 1; i++) {
                a[i + 1] = a[i] + 1;
            }
        }
    }

    private static int nextIndex(int m, int n, Integer[] a) {
        for (int i = n - 1; i > -1; i--) {
            if (a[i] < (m - (a.length - i))) {
                return i;
            }
        }
        return -1;
    }

}