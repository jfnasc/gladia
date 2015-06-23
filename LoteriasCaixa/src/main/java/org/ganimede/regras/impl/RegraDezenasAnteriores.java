package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

public class RegraDezenasAnteriores extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraDezenasAnteriores.class);

    private int qtConcursos;
    private String tpConcurso;

    public RegraDezenasAnteriores(String tpConcurso, int qtConcursos) {
        this.tpConcurso = tpConcurso;
        this.qtConcursos = qtConcursos;
    }

    @Override
    public void aplicar(List<Integer[]> apostas) {

        if (apostas == null || apostas.size() == 0) {
            return;
        }

        float total = Float.valueOf(apostas.size());

        List<Integer> dezenasAIgnorar = new ArrayList<>();

        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(this.tpConcurso);

        for (int i = ultimoConcurso.getNuConcurso(); i > ultimoConcurso.getNuConcurso() - qtConcursos; i--) {
            LOGGER.debug(String.format("%s Concurso [%s]", this.tpConcurso, i));
            Concurso concurso = getConcursoDAO().recuperarConcurso(i, this.tpConcurso, 1);
            for (Sorteio sorteio : concurso.getSorteios()) {
                Collections.sort(sorteio.getDezenas());
                dezenasAIgnorar.addAll(sorteio.getDezenas());
            }
        }

        Collections.sort(dezenasAIgnorar);
        LOGGER.debug(String.format("Dezenas sorteadas nos %s ultimos concursos: [%s]", qtConcursos, dezenasAIgnorar));

        try {

            List<Integer[]> aRemover = new ArrayList<Integer[]>();

            // senas
            for (Integer[] aposta : apostas) {
                for (Integer dezena : aposta) {
                    if (dezenasAIgnorar.contains(dezena)) {
                        aRemover.add(aposta);
                        break;
                    }
                }
            }

            apostas.removeAll(aRemover);

            LOGGER.debug((Float.valueOf(aRemover.size()) / total * 100));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RegraDezenasAnteriores regra = new RegraDezenasAnteriores(TiposConcurso.QUINA.sigla, 3);

        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });

        regra.aplicar(p);
    }
}
