package org.ganimede.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinations {

    public static void main(String[] args) {

        // quantos numeros vou escolher?
        int limit = 8;

        List<Integer> b = new ArrayList<Integer>();
        for (int i = 1; i < (limit + 1); i++) {
            b.add(i);
        }

        // combinacoes com quantas dezenas? no minimo 6
        int nuCombinacoes = 6;

        List<Integer[]> combinacoes = Combinations.comb(b, (nuCombinacoes >= 6 ? nuCombinacoes : 6));
        for (Integer[] i : combinacoes) {
            System.out.println(Arrays.toString(i));
        }
        System.out.println(combinacoes.size());

        // quantas dezenas quero garantir acertar?
        // quantos prognosticos para garantir que acertando, por exemplo, 3
        // numeros
        // daqueles que escolhi inicialmente, ao menos um terno está garantido
        // no minimo 1
        int garantia = 2;

        List<Integer[]> resultado = new ArrayList<Integer[]>();

        List<Integer[]> provas = Combinations.comb(b, garantia >= 1 ? garantia : 1);
        for (Integer[] prova : provas) {

            System.out.println(Arrays.toString(prova));

            for (Integer[] combinacao : combinacoes) {

                // se alguma combinacao no resultado final
                // ja contempla a prova, sai
                if (contains(resultado, prova)) {
                    break;
                }

                if (contains(combinacao, prova)) {
                    if (!resultado.contains(combinacao)) {
                        resultado.add(combinacao);
                        break;
                    }
                }
            }
        }

        System.out.println("------------------------------------------");
        for (Integer[] i : resultado) {
            System.out.println(Arrays.toString(i));
        }
        System.out.println(resultado.size());
    }

    public static boolean contains(Integer[] combinacao, Integer[] dezenas) {

        for (Integer dezena : dezenas) {
            if (!Arrays.asList(combinacao).contains(dezena)) {
                return false;
            }
        }

        return true;
    }

    public static boolean contains(List<Integer[]> combinacoes, Integer[] dezenas) {

        if (combinacoes == null || combinacoes.isEmpty() || dezenas == null) {
            return false;
        }

        for (Integer[] combinacao : combinacoes) {
            if (!contains(combinacao, dezenas)){
                return false;
            }
        }

        return true;
    }

    public static List<Integer[]> comb(List<Integer> l, Integer n) {
        Integer[] base = new Integer[] {};
        base = l.toArray(base);
        return comb(base, n);
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

    public static List<Integer[]> comb(Integer qtDezenas, Integer qtTamanho) {
        List<Integer[]> result = new ArrayList<>();

        Integer[] a = new Integer[qtTamanho];
        for (int i = 0; i < qtTamanho; i++) {
            a[i] = i;
        }

        int index = -1;

        while ((index = nextIndex(qtDezenas, qtTamanho, a)) != -1) {
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