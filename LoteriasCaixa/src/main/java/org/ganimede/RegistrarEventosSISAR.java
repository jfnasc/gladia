package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.utils.RndUtils;
import org.ganimede.utils.StringUtils;

public class RegistrarEventosSISAR {

    public static void main(String[] args) {
        int n = 10;

        Integer[] base = new Integer[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }

        gerar(base, 3, 5);
    }

    public static List<Integer[]> gerar(Integer[] base, int tamanhoBase, int nuPrognosticos) {
        List<Integer[]> apostas = new ArrayList<>();

        List<Integer[]> combinacoesBase = new ArrayList<>();
        List<Integer[]> combinacoesComplementares = new ArrayList<>();

        if (tamanhoBase == 3) {
            calcularCombinacoesBase3(combinacoesBase, base);
        } else if (tamanhoBase == 4) {
            calcularCombinacoesBase4(combinacoesBase, base);
        } else {
            throw new RuntimeException("Nao suportado.");
        }

        calcularCombinacoesBase3(combinacoesBase, base);
        System.out.println("Nro. Combinacoes base: " + combinacoesBase.size());

        for (Integer[] combinacao : combinacoesBase) {
            if (nuPrognosticos - tamanhoBase == 2) {
                calcularCombinacoesComplementares5(combinacoesComplementares, remover(base, combinacao));
            } else if (nuPrognosticos - tamanhoBase == 3) {
                calcularCombinacoesComplementares6(combinacoesComplementares, remover(base, combinacao));
            } else if (nuPrognosticos - tamanhoBase == 4) {
                calcularCombinacoesComplementares7(combinacoesComplementares, remover(base, combinacao));
            }
        }
        System.out.println("Nro. Combinacoes compl.: " + combinacoesComplementares.size());

        for (Integer[] combinacao : combinacoesBase) {
            // alguma já atende essa combinacao
            if (!existeApostaContemplada(apostas, combinacao)) {
                for (Integer[] complementar : combinacoesComplementares) {
                    Integer[] aposta = combinar(combinacao, complementar);
                    if (estaContido(aposta, combinacao)) {
                        apostas.add(aposta);
                        break;
                    }
                }
            }
        }

        System.out.println("Nro.  apostas: " + apostas.size());
        System.out.println();
        for (Integer[] aposta : apostas) {
            Arrays.sort(aposta);
            System.out.println(StringUtils.print(aposta));
        }

        return apostas;
    }

    public static void calcularCombinacoesBase3(List<Integer[]> combinacoes, Integer[] base) {
        int i, j, k = 0;

        for (i = 0; i < base.length; i++) {
            for (j = i + 1; j < base.length; j++) {
                for (k = j + 1; k < base.length; k++) {
                    combinacoes.add(new Integer[] { base[i], base[j], base[k] });
                }
            }
        }
    }

    public static void calcularCombinacoesBase4(List<Integer[]> combinacoes, Integer[] base) {
        int i, j, k, l = 0;

        for (i = 0; i < base.length; i++) {
            for (j = i + 1; j < base.length; j++) {
                for (k = j + 1; k < base.length; k++) {
                    for (l = k + 1; l < base.length; l++) {
                        combinacoes.add(new Integer[] { base[i], base[j], base[k], base[l] });
                    }
                }
            }
        }
    }

    public static void calcularCombinacoesComplementares6(List<Integer[]> combinacoes, Integer[] base) {
        int i, j, k = 0;

        for (i = 0; i < base.length; i++) {
            for (j = i + 1; j < base.length; j++) {
                for (k = j + 1; k < base.length; k++) {
                    combinacoes.add(new Integer[] { base[i], base[j], base[k] });
                }
            }
        }
    }

    public static void calcularCombinacoesComplementares5(List<Integer[]> combinacoes, Integer[] base) {
        int i, j = 0;

        for (i = 0; i < base.length; i++) {
            for (j = i + 1; j < base.length; j++) {
                combinacoes.add(new Integer[] { base[i], base[j] });
            }
        }
    }

    public static void calcularCombinacoesComplementares7(List<Integer[]> combinacoes, Integer[] base) {
        int i, j, k, l = 0;

        for (i = 0; i < base.length; i++) {
            for (j = i + 1; j < base.length; j++) {
                for (k = j + 1; k < base.length; k++) {
                    for (l = k + 1; l < base.length; l++) {
                        combinacoes.add(new Integer[] { base[i], base[j], base[k], base[l] });
                    }
                }
            }
        }
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

    public static boolean existeApostaContemplada(List<Integer[]> apostas, Integer[] combinacao) {
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
