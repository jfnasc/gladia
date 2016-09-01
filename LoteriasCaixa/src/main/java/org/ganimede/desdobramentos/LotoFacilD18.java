package org.ganimede.desdobramentos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ganimede.GerarCombinacoesBase;
import org.ganimede.GerarCombinacoesLotoFacil;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.utils.Combinations;

public class LotoFacilD18 extends GerarCombinacoesLotoFacil {

    public LotoFacilD18(Integer qtDezenasSorteioAnterior, Integer qtDezenasBase) {
        super(qtDezenasSorteioAnterior, qtDezenasBase);
        // TODO Auto-generated constructor stub
    }

    private static GerarCombinacoesBase gb = new LotoFacilD18(9, 18);

    public static void main(String[] args) {

        List<Integer[]> p = Combinations.comb(6, 5);
        for (Integer[] n : p) {
            System.out.println(Arrays.toString(n));
        }
        System.out.println(p.size());
        System.out.println("R$ " + (p.size() * 2));

        // List<Integer[]> prognosticos = gerarPrognosticos(21);
        // for (Integer[] p : prognosticos) {
        // System.out.println(Arrays.toString(p));
        // }
        //
        // System.out.println(prognosticos.size());
        // System.out.println("R$ " + (prognosticos.size() * 2));
        //
        // verificarAcertos(TiposConcurso.LOTO_FACIL, prognosticos, 13);
    }

    public static List<Integer[]> gerarPrognosticos(int factor) {
        List<Integer[]> prognosticos = new ArrayList<Integer[]>();

        List<Integer[]> base = gb.gerarPrognosticos(1, factor);
        // List<Integer[]> base = new ArrayList<Integer[]>();
        // Integer[] tmp = new Integer[factor];
        // for (int i = 1; i <= (factor); i++) {
        // tmp[i - 1] = i;
        // }
        // base.add(tmp);

        Map<Integer, List<Integer>> grupos = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < factor / 3; i++) {
            grupos.put(i, Arrays.asList(base.get(0)).subList(i * 3, (i + 1) * 3));
        }

        List<Integer[]> combinacoes = Combinations.comb(factor / 3, 5);

        for (Integer[] combinacao : combinacoes) {

            List<Integer> tmp = new ArrayList<Integer>();

            for (Integer key : combinacao) {
                tmp.addAll(grupos.get(key));
            }

            Integer[] p = new Integer[15];
            tmp.toArray(p);

            Arrays.sort(p);

            prognosticos.add(p);
        }

        return prognosticos;
    }

    @Override
    public List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();
        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
        regras.add(new org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.LOTO_FACIL, 1, 9));
        // regras.add(new org.ganimede.regras.impl.RegraQuantidadeDezenas(new
        // Integer[] { 1, 2, 3, 5, 8, 13, 21 }, 4, 5));
        // regras.add(new org.ganimede.regras.impl.RegraQuantidadeDezenas(
        // new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19, 23 }, 5, 6));
        return regras;
    }
}
