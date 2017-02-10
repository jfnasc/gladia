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

        // Integer[] base = new Integer[]{03, 07, 12, 16, 18, 20, 26, 31, 32,
        // 37, 41, 43, 45, 52, 54};

        List<Integer[]> p = calcular(base, 3, 5);
        StringUtils.print(p);
    }

    public static List<Integer[]> calcular(Integer[] base, int nuDezenasVariaveis, int nuPrognosticos) {

        if (nuPrognosticos < nuDezenasVariaveis) {
            throw new RuntimeException("Erro! o numero de dezenas variaveis não pode ser maior que"
                    + " o de prognosticos desejados");
        }

        List<Integer[]> apostas = new ArrayList<>();

        List<Integer[]> combinacoesBase = calcularCombinacoes(base, nuDezenasVariaveis);
        for (Integer[] cb : combinacoesBase) {
            System.out.println(Arrays.toString(cb));
        }

        for (Integer[] combinacao : combinacoesBase) {
            List<Integer[]> combinacoesComplementares = calcularCombinacoes(remover(base, combinacao), nuPrognosticos
                    - nuDezenasVariaveis);

            // alguma já atende a essa combinacao ?
            for (Integer[] complementar : combinacoesComplementares) {
                Integer[] aposta = combinar(combinacao, complementar);
                if (!existeCombinacao(apostas, combinacao) && !existeCombinacao(apostas, aposta)) {
                    apostas.add(aposta);
                    break;
                }
            }
        }

        validar(apostas);

        validarFechamentos(combinacoesBase, apostas);

        System.out.println(String.format("Combinacoes base: %s | Nro.  apostas: %s", combinacoesBase.size(),
                apostas.size()));

        return apostas;
    }

    public static void validar(List<Integer[]> apostas) {
        for (Integer[] aposta : apostas) {
            for (Integer dezena : aposta) {
                if (contar(aposta, dezena) > 1) {
                    throw new RuntimeException("Nao pode haver numeros repetidos na aposta!");
                }
            }
        }
    }

    public static void validarFechamentos(List<Integer[]> combinacoesBase, List<Integer[]> apostas) {
        for (Integer[] base : combinacoesBase) {
            boolean contem = false;
            for (Integer[] aposta : apostas) {
                if (estaContido(aposta, base)) {
                    contem = true;
                }
            }
            if (!contem) {
                throw new RuntimeException(String.format("A base [%s] nao esta contemplada!", Arrays.toString(base)));
            }
        }
    }

    public static int contar(Integer[] aposta, Integer dezena) {
        int result = 0;
        for (Integer a : aposta) {
            if (a == dezena) {
                result++;
            }
        }
        return result;
    }

    public static List<Integer[]> calcularCombinacoes(Integer[] base, int nuDezenasFixas) {
        List<Integer[]> combinacoes = new ArrayList<>();
        combinacoes.addAll(Combinations.comb(base, nuDezenasFixas));
        return combinacoes;
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
        for (Integer dezena : combinacao) {
            if (!Arrays.asList(base).contains(dezena)) {
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
