package org.ganimede;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ganimede.regras.Regra;
import org.ganimede.utils.StringUtils;

public class GerarCombinacoesMS extends GerarCombinacoesBase {

    private static GerarCombinacoesBase gb = new GerarCombinacoesMS();

    /**
     * 
     * @param args
     */
    public static void main2(String[] args) {
        List<Integer[]> prognosticos = gb.prognosticos(3, 6);

        System.out.println("--------------------------");
        System.out.println("Boa Sorte!");
        System.out.println("--------------------------");
        for (Integer[] aposta : prognosticos) {
            StringUtils.print(aposta);
        }

        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.MEGA_SENA);

        System.out.println("------------------------------------------------");
        System.out.println("Ultimas sorteadas");
        System.out.println("------------------------------------------------");
        for (Sorteio sorteio : ultimoConcurso.getSorteios()) {
            System.out.println(sorteio.getDezenas());
        }

        System.out.println("------------------------------------------------");
        System.out.println("Atrasos (entre 8 e 9 sorteios de atraso)");
        System.out.println("------------------------------------------------");
        List<Integer> atrasos = getResultadoDAO().buscarDezenasEmAtraso(TiposConcurso.MEGA_SENA.sigla, 1, 8);
        Collections.sort(atrasos);
        System.out.println(atrasos);
    }

    public static void main(String[] args) {
        List<List<Integer>> apostas = new ArrayList<List<Integer>>();

        List<Integer[]> prognosticos = gb.prognosticos(1, 10);

        for (Integer[] p : prognosticos) {
            System.out.println("-- BASE");
            StringUtils.print(p);
            System.out.println("--------------------");

            for (int i = 0; i < p.length / 5; i++) {
                fechamento(apostas, p, i * 5, 5);
            }

            for (List<Integer> aposta : apostas) {
                Collections.sort(aposta);
                System.out.println(aposta);
            }
            System.out.println("--------------------");
            System.out.println("Nro. apostas: " + apostas.size());
            System.out.println("Vl.  apostas: " + Float.valueOf(apostas.size()) * 3.50);
        }
    }

    @Override
    int qtDezenas() {
        return TiposConcurso.MEGA_SENA.nuDezenas;
    }

    @Override
    List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();

        regras.add(new org.ganimede.regras.impl.RegraNaoSequencial());
        regras.add(new org.ganimede.regras.impl.RegraSorteiosAnteriores(TiposConcurso.MEGA_SENA));
        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
        regras.add(new org.ganimede.regras.impl.RegraNaoVertical(TiposConcurso.MEGA_SENA));
//        regras.add(new org.ganimede.regras.impl.RegraAtraso(TiposConcurso.MEGA_SENA, 8));
//        regras.add(new org.ganimede.regras.impl.RegraDezenasFrequentes(TiposConcurso.MEGA_SENA, 10));
//        regras.add(new org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.MEGA_SENA, 2));

        return regras;
    }

}
