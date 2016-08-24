package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

public class RegraDezenasAnteriores extends RegraBase implements Regra {

    private int maxDezenas = 0;

    private List<Integer> dezenasAIgnorar = new ArrayList<>();

    public RegraDezenasAnteriores(TiposConcurso tpConcurso, int qtConcursos) {
        this(tpConcurso, qtConcursos, 0);
    }

    public RegraDezenasAnteriores(TiposConcurso tpConcurso, int qtConcursos, int maxDezenas) {

        this.maxDezenas = maxDezenas;

        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(tpConcurso);

        for (int i = ultimoConcurso.getNuConcurso(); i > ultimoConcurso.getNuConcurso() - qtConcursos; i--) {
            getLogger().debug(String.format("%s Concurso [%s]", tpConcurso, i));
            Concurso concurso = getConcursoDAO().recuperarConcurso(i, tpConcurso);
            for (Sorteio sorteio : concurso.getSorteios()) {
                Collections.sort(sorteio.getDezenas());
                dezenasAIgnorar.addAll(sorteio.getDezenas());
            }
        }

        Collections.sort(dezenasAIgnorar);
        getLogger().debug(
                String.format("Dezenas sorteadas nos %s ultimos" + " concursos: [%s]", qtConcursos, dezenasAIgnorar));
    }

    @Override
    public boolean validar(Integer[] aposta) {

        int contador = 0;

        for (Integer dezena : aposta) {

            if (dezenasAIgnorar.contains(dezena)) {

                contador++;

                if (this.maxDezenas == 0 || contador >= maxDezenas) {
                    return false;
                }
            }

        }
        return true;
    }

    public static void main(String[] args) {
        RegraDezenasAnteriores regra = new RegraDezenasAnteriores(TiposConcurso.QUINA, 3);

        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });

        regra.aplicar(p);
    }

}
