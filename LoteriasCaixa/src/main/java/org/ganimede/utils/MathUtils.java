package org.ganimede.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public static BigDecimal fatorial(long n) {
        if (n == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = new BigDecimal(1);
        while (n > 0) {
            result = result.multiply(new BigDecimal(n--));
        }
        return result;
    }

    public static long csimples(long n, long p) {
        BigDecimal p1 = fatorial(n);
        BigDecimal p2 = fatorial(p);

        if (n == 0 || p == 0) {
            return 1L;
        }

        if (p1.equals(p2)) {
            return 1L;
        }

        BigDecimal p3 = fatorial(n - p);

        return p1.divide(p2.multiply(p3), RoundingMode.HALF_UP).longValue();
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

    // public static long calcularChances(int qtDezenas, int nuPrognosticos, int
    // nuAcertos) {
    // long result = MathUtils.csimples(qtDezenas, nuPrognosticos).longValue();
    // if (nuPrognosticos > nuAcertos) {
    // result = result
    // / (MathUtils.csimples(nuPrognosticos, nuAcertos).longValue() *
    // MathUtils.csimples(
    // qtDezenas - nuPrognosticos, nuPrognosticos - nuAcertos).longValue());
    // }
    // return result;
    // }
    //
    // public static long calcularChancesMultiplasApostas(int qtDezenas, int
    // maiorFaixaPremiavel, int nuPrognosticos,
    // int nuAcertos) {
    // long result = MathUtils.csimples(qtDezenas,
    // maiorFaixaPremiavel).longValue();
    // if (nuPrognosticos > nuAcertos) {
    // result = result
    // / (MathUtils.csimples(nuPrognosticos, nuAcertos).longValue() *
    // MathUtils.csimples(
    // qtDezenas - nuPrognosticos, nuPrognosticos - nuAcertos).longValue());
    // }
    // return result;
    // }

    public static long calcularChances(long nuDezenas, long nuAcertosPremiacao, long nuPrognosticos, long nuAcertos) {

        long p1 = MathUtils.csimples(nuDezenas, nuAcertosPremiacao);
        long p2 = MathUtils.csimples(nuPrognosticos, nuAcertos);
        long p3 = (p2 * MathUtils.csimples(nuDezenas - nuPrognosticos, nuAcertosPremiacao - nuAcertos));

        return p1 / p3;
    }

    /**
     * 
     * @param quantidade
     * @return
     */
    public static List<Integer[]> gerarPrognostico(Integer quantidade, Integer qtDezenas) {
        List<Integer[]> result = new ArrayList<Integer[]>();

        while (result.size() < quantidade) {

            for (int i = 0; i < quantidade; i++) {
                result.add(gerarPrognostico(qtDezenas));
            }
        }

        return result.subList(0, quantidade);
    }

    public static Integer[] gerarPrognostico(int qtDezenas) {
        List<Integer> tmp = new ArrayList<Integer>();

        while (tmp.size() < qtDezenas) {
            Integer p1 = RndUtils.nextInt(1, 60);
            if (!tmp.contains(p1)) {
                tmp.add(p1);
            }
        }

        Integer[] result = new Integer[qtDezenas];
        tmp.subList(0, qtDezenas).toArray(result);

        return result;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        // System.out.println("'" + format(5, 10) + "'");

        // System.out.println("--");
        // System.out.println("-- QUINA");
        // System.out.println("--");
        // for (int i = 5; i <= 7; i++) {
        // System.out.println("Nro. Prognosticos por aposta: " + i);
        // System.out.println("Quina\t" + calcularChances(80, i, 5, 5));
        // System.out.println("Quadra\t" + calcularChances(80, i, 5, 4));
        // System.out.println("Terno\t" + calcularChances(80, i, 5, 3));
        // System.out.println();
        // }
        // System.out.println();

        // System.out.println("--");
        // System.out.println("-- MEGASENA");
        // System.out.println("7151980   44981  1038");
        // System.out.println("--");
        // for (int i = 6; i <= 15; i++) {
        // // for (int i = 7; i <= 7; i++) {
        // System.out.print(i);
        // System.out.print("\t\t" + calcularChances(60, 6, i, 6));
        // System.out.print("\t\t" + calcularChances(60, 6, i, 5));
        // System.out.print("\t\t" + calcularChances(60, 6, i, 4));
        // System.out.println();
        // }
        // System.out.println();

        // System.out.println("--");
        // System.out.println("-- DUPLASENA");
        // System.out.println("--");
        // for (int i = 6; i <= 15; i++) {
        // System.out.println("Nro. Prognosticos por aposta: " + i);
        // System.out.println("Sena\t" + calcularChances(50, i, 6, 6));
        // System.out.println("Quina\t" + calcularChances(50, i, 6, 5));
        // System.out.println("Quadra\t" + calcularChances(50, i, 6, 4));
        // System.out.println();
        // }
        // System.out.println();

        System.out.println(calcularChances(80, 5, 10, 2));

    }

    private static String format(long p, int size) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < (size - String.valueOf(p).length())) {
            sb.append(new char[] { ' ' }, 0, 1);
        }

        sb.append(String.valueOf(p).toCharArray(), 0, String.valueOf(p).length());

        return sb.toString();
    }
}
