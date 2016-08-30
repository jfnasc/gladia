package org.ganimede.desdobramentos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ganimede.GerarCombinacoesBase;
import org.ganimede.GerarCombinacoesLotoFacil;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.utils.Combinations;

public class LotoFacilD18 extends GerarCombinacoesLotoFacil {

    private static GerarCombinacoesBase gb = new LotoFacilD18();

    public static void main(String[] args) {
        List<List<Integer>> prognosticos = gerarPrognosticos(7);

        for (int i = 1; i <= getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.LOTO_FACIL).getNuConcurso(); i++) {
            List<Integer> resultado = getConcursoDAO().recuperarConcurso(1, TiposConcurso.LOTO_FACIL).getSorteios()
                    .get(0).getDezenas();

            System.out.println("Concurso: " + i);
            System.out
                    .println("===============================================================================================");
            verificar(prognosticos, resultado);
            System.out
                    .println("===============================================================================================");
        }
    }

    public static void verificar(List<List<Integer>> prognosticos, List<Integer> resultado) {
        for (List<Integer> p : prognosticos) {
            int count = 0;
            for (Integer dezena : resultado) {
                if (p.contains(dezena)) {
                    count++;
                }
            }
            System.out.println(p + "\t\t acertos:" + count);
            if (count >= 13) {
                System.out.println("Ganhador!!!");
                System.exit(0);
            }
        }
    }

    public static List<List<Integer>> gerarPrognosticos(int factor) {
        List<List<Integer>> prognosticos = new ArrayList<List<Integer>>();
        List<Integer[]> base = gb.prognosticos(1, factor * 3);

        Map<Integer, List<Integer>> grupos = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < factor; i++) {
            grupos.put(i, Arrays.asList(base.get(0)).subList(i * 3, (i + 1) * 3));
        }

        List<Integer[]> combinacoes = Combinations.comb(factor, 5);
        for (Integer[] combinacao : combinacoes) {
            System.out.println(Arrays.toString(combinacao));
            List<Integer> p = new ArrayList<Integer>();
            for (Integer i : combinacao) {
                p.addAll(grupos.get(i));
            }
            Collections.sort(p);
            prognosticos.add(p);
        }
        System.out.println(combinacoes.size() * 2);

        System.out.println(prognosticos);

        return prognosticos;
    }

    @Override
    public List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();
        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
//        regras.add(new org.ganimede.regras.impl.RegraQuantidadeDezenas(new Integer[] { 1, 2, 3, 5, 8, 13, 21 }, 4, 5));
//        regras.add(new org.ganimede.regras.impl.RegraQuantidadeDezenas(
//                new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19, 23 }, 5, 6));
        return regras;
    }
}
