package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.ganimede.utils.RndUtils;

public class Prognosticos implements Iterator<Integer[]> {

    private Iterator<Integer[]> cache = null;

    public Prognosticos(Integer nuMaiorDezena, Integer qtDezenas, Integer qtPrognosticos) {
        List<Integer[]> p = new ArrayList<>();
        while (p.size() < qtPrognosticos) {
            p.add(RndUtils.nextInts(nuMaiorDezena, qtDezenas));
        }
        cache = p.iterator();
    }

    @Override
    public boolean hasNext() {
        return cache.hasNext();
    }

    @Override
    public Integer[] next() {
        return cache.next();
    }

    @Override
    public void remove() {
        cache.remove();
    }

    public static void main(String[] args) {
        Prognosticos p = new Prognosticos(80, 5, 100);
        while (p.hasNext()) {
            Integer[] integers = (Integer[]) p.next();
            System.out.println(Arrays.toString(integers));
        }
    }

}
