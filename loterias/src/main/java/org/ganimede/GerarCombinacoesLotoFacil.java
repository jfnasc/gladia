/**
 * 
 */
package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ganimede.regras.Regra;
import org.ganimede.utils.CollectionUtils;
import org.ganimede.utils.Combinations;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public class GerarCombinacoesLotoFacil extends GerarCombinacoesBase {

    Integer qtDezenasSorteioAnterior;
    Integer qtDezenasBase;

    public static void main(String[] args) {

        GerarCombinacoesBase gb = new GerarCombinacoesLotoFacil(9, 18);
        gb.gerar();

    }

    public GerarCombinacoesLotoFacil(Integer qtDezenasSorteioAnterior, Integer qtDezenasBase) {
        this.qtDezenasSorteioAnterior = qtDezenasSorteioAnterior;
        this.qtDezenasBase = qtDezenasBase;
    }

    @Override
    public void gerar() {

        List<Integer> base = new ArrayList<Integer>();

        // 1. Obter o último resultado da lotofácil;
        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.LOTO_FACIL);

        System.out.println("------------------------------------------------");
        System.out.println("Ultimas sorteadas");
        System.out.println("------------------------------------------------");

        for (Sorteio sorteio : ultimoConcurso.getSorteios()) {

            System.out.println(sorteio.getDezenas());

            // 2. Entre as dezenas sorteadas escolher 9, aplicando as regras
            List<Integer[]> prognosticos = gerarPrognosticos(1, qtDezenasSorteioAnterior,
                    CollectionUtils.toArray(sorteio.getDezenas()));
            for (Integer[] p : prognosticos) {
                System.out.println(Arrays.toString(p));
                CollectionUtils.addAll(p, base);
            }

            break;
        }

        // 3. Completar a base com 9, 12 ou 15 dezenas que não foram sorteadas
        // (as mais atrasadas, por exemplo), totalizando 18, 21 ou 24 dezenas;
        System.out.println("------------------------------------------------");
        System.out.println("Atrasos");
        System.out.println("------------------------------------------------");
        List<Integer> atrasos = getResultadoDAO().buscarDezenasEmAtraso(TiposConcurso.LOTO_FACIL.sigla, 1);
        System.out.println(atrasos);
        base.addAll(atrasos);

        while (base.size() < qtDezenasBase) {
            Integer n = 1;
            if (!base.contains(n)) {
                base.add(n);
            }
            n++;

            // Integer n = RndUtils.nextInt(1, 25);
            // if (!base.contains(n)) {
            // base.add(n);
            // }
        }

        // 4. Dividir as dezenas base em grupos de 3;
        Map<Integer, List<Integer>> grupos = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < qtDezenasBase / 3; i++) {
            grupos.put(i, base.subList(i * 3, (i + 1) * 3));
        }

        // 5. Montar todas as combinacoes possíves com esses grupos;
        // 6. Para base 18, serão gerados 6 cartões, para base 21, serão 21
        // cartões e para base 24, 56
        List<Integer[]> prognosticos = new ArrayList<Integer[]>();

        List<Integer[]> combinacoes = Combinations.comb(qtDezenasBase / 3, 5);

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

        System.out.println("------------------------------------------------");
        System.out.println("Prognosticos");
        System.out.println("------------------------------------------------");
        for (Integer[] p : prognosticos) {
            System.out.println(Arrays.toString(p));
        }

        System.out.println(prognosticos.size());
        System.out.println("R$ " + (prognosticos.size() * 2));

        verificarAcertos(TiposConcurso.LOTO_FACIL, prognosticos, 13);

    }

    @Override
    public int qtDezenasConcurso() {
        return TiposConcurso.LOTO_FACIL.nuDezenas;
    }

    @Override
    public List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();

        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
        regras.add(new org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.LOTO_FACIL, 1, 9));

        return regras;
    }

}
