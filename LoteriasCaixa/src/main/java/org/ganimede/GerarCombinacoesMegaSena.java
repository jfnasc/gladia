package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ganimede.regras.Regra;
import org.ganimede.utils.StringUtils;

public class GerarCombinacoesMegaSena extends GerarCombinacoesBase {

    private static GerarCombinacoesBase gb = new GerarCombinacoesMegaSena();

    public static void main(String[] args) {

        GerarCombinacoesBase gb = new GerarCombinacoesMegaSena();
        gb.gerar();

    }

    @Override
    public void gerar() {
        List<Integer[]> prognosticos = gb.gerarPrognosticos(3, 6);

        System.out.println("--------------------------");
        System.out.println("Boa Sorte!");
        System.out.println("--------------------------");
        for (Integer[] aposta : prognosticos) {
            Arrays.sort(aposta);
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

    @Override
    public int qtDezenasConcurso() {
        return TiposConcurso.MEGA_SENA.nuDezenas;
    }

    @Override
    public List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();

        regras.add(new org.ganimede.regras.impl.RegraNaoSequencial());
        regras.add(new org.ganimede.regras.impl.RegraSorteiosAnteriores(TiposConcurso.MEGA_SENA));
        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
        regras.add(new org.ganimede.regras.impl.RegraNaoVertical(TiposConcurso.MEGA_SENA));
        // regras.add(new
        // org.ganimede.regras.impl.RegraAtraso(TiposConcurso.MEGA_SENA, 8));
        // regras.add(new
        // org.ganimede.regras.impl.RegraDezenasFrequentes(TiposConcurso.MEGA_SENA,
        // 10));
        // regras.add(new
        // org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.MEGA_SENA,
        // 2));

        return regras;
    }

}
