package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.utils.Combinations;
import org.ganimede.utils.RndUtils;
import org.ganimede.utils.StringUtils;

public class Fechamentos {

    public static void main(String[] args) {
        int n = 10;

        Integer[] base = new Integer[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }

        //
        // calcular(base, 6, 5);

        List<Integer[]> p = calcular(base, 3, 5);
        for (Integer[] d : p) {
            System.out.println(StringUtils.print(d));
        }
    }

    public static List<Integer[]> calcular(Integer[] base, int nuDezenasVariaveis, int nuPrognosticos) {

        if (nuPrognosticos < nuDezenasVariaveis) {
            throw new RuntimeException("Erro! o numero de dezenas variaveis não pode ser maior que"
                    + " o de prognosticos desejados");
        }

        List<Integer[]> apostas = new ArrayList<>();

        List<Integer[]> combinacoesBase = new ArrayList<>();
        List<Integer[]> combinacoesComplementares = new ArrayList<>();

        calcularCombinacoes(combinacoesBase, base, nuDezenasVariaveis);

        for (Integer[] combinacao : combinacoesBase) {
            calcularCombinacoes(combinacoesComplementares, remover(base, combinacao), nuPrognosticos
                    - nuDezenasVariaveis);
        }

        for (Integer[] combinacao : combinacoesBase) {
            // alguma já atende a essa combinacao ?
            if (!existeCombinacao(apostas, combinacao)) {
                for (Integer[] complementar : combinacoesComplementares) {
                    Integer[] aposta = combinar(combinacao, complementar);
                    if (estaContido(aposta, combinacao)) {
                        apostas.add(aposta);
                        break;
                    }
                }
            }
        }

        for (Integer[] aposta : apostas) {
            Arrays.sort(aposta);
        }

        System.out.println(String.format("Combinacoes base: %s | Combinacoes compl: %s | Nro.  apostas: %s",
                combinacoesBase.size(), combinacoesComplementares.size(), apostas.size()));

        return apostas;
    }

    public static void calcularCombinacoes(List<Integer[]> combinacoes, Integer[] base, int nuDezenasFixas) {
        combinacoes.addAll(Combinations.calc(base, nuDezenasFixas));
    }

    public static Integer[] remover(Integer[] base, Integer[] lista) {
        List<Integer> tmp = new ArrayList<>();
        tmp.addAll(Arrays.asList(base));
        tmp.removeAll(Arrays.asList(lista));

        Integer[] result = new Integer[tmp.size()];
        tmp.toArray(result);

        return result;
    }

    public static Integer[] combinar(Integer[] base, Integer[] combinacao) {
        List<Integer> tmp = new ArrayList<>();
        tmp.addAll(Arrays.asList(base));
        tmp.addAll(Arrays.asList(combinacao));

        Integer[] result = new Integer[tmp.size()];
        tmp.toArray(result);

        return result;
    }

    public static boolean estaContido(Integer[] base, Integer[] combinacao) {
        List<Integer> tmp = new ArrayList<>();
        tmp.addAll(Arrays.asList(base));

        for (Integer dezena : combinacao) {
            if (!tmp.contains(dezena)) {
                return false;
            }
        }

        return true;
    }

    public static boolean existeCombinacao(List<Integer[]> apostas, Integer[] combinacao) {
        for (Integer[] aposta : apostas) {
            if (estaContido(aposta, combinacao)) {
                return true;
            }
        }
        return false;
    }

    public static Integer[] simularResultado(int nuDezenas, int nuConjunto) {
        List<Integer> tmp = new ArrayList<Integer>();

        while (tmp.size() < nuDezenas) {
            Integer p1 = RndUtils.nextInt(1, nuConjunto);
            if (!tmp.contains(p1)) {
                tmp.add(p1);
            }
        }

        return toArray(tmp);
    }

    public static Integer[] toArray(List<Integer> combinacao) {
        Integer[] result = new Integer[combinacao.size()];
        combinacao.toArray(result);

        return result;

    }

    public static int conferir(Integer[] resultado, Integer[] aposta) {
        int count = 0;

        for (Integer dezena : aposta) {
            if (Arrays.asList(resultado).contains(dezena)) {
                count++;
            }
        }

        return count;
    }

}
