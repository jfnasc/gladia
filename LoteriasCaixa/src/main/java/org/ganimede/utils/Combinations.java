package org.ganimede.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinations {
    public static void main(String[] args) {
        Integer[] base = new Integer[10];
        for (int i = 0; i < 10; i++) {
            base[i] = i + 10;
        }
        List<Integer[]> b = Combinations.calc(base, 3);
        for (Integer[] integers : b) {
            System.out.println(Arrays.toString(integers));
        }
    }

    /**
     * 
     * @param n
     * @param m
     * @return
     */
    public static List<Integer[]> calc(int n, int m) {
        List<Integer[]> result = new ArrayList<>();
        Combination c = new Combination(n, m);
        while (c.hasNext()) {
            result.add(c.next());
        }
        return result;
    }
    
    /**
     * 
     * @param base
     * @param m
     * @return
     */
    public static List<Integer[]> calc(Integer[] base, int m) {
        List<Integer[]> result = new ArrayList<>();
        Combination c = new Combination(base.length, m);
        while (c.hasNext()) {
            result.add(parse(base, c.next()));
        }
        return result;
    }
    
    /**
     * 
     * @param base
     * @param indexes
     * @return
     */
    private static Integer[] parse(Integer[] base, Integer[] indexes){
        Integer[] result = new Integer[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            result[i] = base[indexes[i]];
        }
        return result;
    }
}

// ////////////////////////////////////
// Combination
//
// You do not need to write the code below here.
// You just need to be able to USE it.
// ////////////////////////////////////

// The algorithm is from Applied Combinatorics, by Alan Tucker.
// Based on code from koders.com

class Combination {
    private Integer n, r;
    private Integer[] index;
    private boolean hasNext = true;

    public Combination(Integer[] n, Integer r) {
        this.n = n.length;
        this.r = r;
        index = new Integer[r];
        for (int i = 0; i < r; i++) {
            index[i] = i;
        }
    }

    public Combination(Integer n, Integer r) {
        this.n = n;
        this.r = r;
        index = new Integer[r];
        for (int i = 0; i < r; i++) {
            index[i] = i;
        }
    }

    public boolean hasNext() {
        return hasNext;
    }

    // Based on code from KodersCode:
    // The algorithm is from Applied Combinatorics, by Alan Tucker.
    // Move the index forward a notch. The algorithm finds the rightmost
    // index element that can be incremented, increments it, and then
    // changes the elements to the right to each be 1 plus the element on their
    // left.
    //
    // For example, if an index of 5 things taken 3 at a time is at {0 3 4},
    // only the 0 can
    // be incremented without running out of room. The next index is {1, 1+1,
    // 1+2) or
    // {1, 2, 3}. This will be followed by {1, 2, 4}, {1, 3, 4}, and {2, 3, 4}.
    private void moveIndex() {
        int i = rightmostIndexBelowMax();
        if (i >= 0) {
            index[i] = index[i] + 1;
            for (int j = i + 1; j < r; j++) {
                index[j] = index[j - 1] + 1;
            }

        } else {
            hasNext = false;
        }
    }

    public Integer[] next() {
        if (!hasNext) {
            return null;
        }

        Integer[] result = new Integer[r];
        for (int i = 0; i < r; i++) {
            result[i] = index[i];
            moveIndex();
        }

        return result;
    }

    // return int,the index which can be bumped up.
    private int rightmostIndexBelowMax() {
        for (int i = r - 1; i >= 0; i--) {
            if (index[i] < n - r + i) {
                return i;
            }
        }
        return -1;
    }
}