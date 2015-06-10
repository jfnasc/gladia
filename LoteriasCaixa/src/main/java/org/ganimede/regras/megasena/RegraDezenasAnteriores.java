package org.ganimede.regras.megasena;

import java.util.ArrayList;
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

    public RegraDezenasAnteriores(int qtConcursos) {
        this.qtConcursos = qtConcursos;
    }

    @Override
    public void aplicar(List<Integer[]> apostas) {

        if (apostas == null || apostas.size() == 0) {
            return;
        }

        float total = Float.valueOf(apostas.size());

        List<Integer> dezenasAIgnorar = new ArrayList<>();

        Concurso concurso = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.MEGA_SENA.sigla);

        for (Sorteio sorteio : concurso.getSorteios()){
            dezenasAIgnorar.addAll(sorteio.getDezenas());    
        }
        
        for (int i = (concurso.getNuConcurso() - (qtConcursos - 1)); i < concurso.getNuConcurso(); i++) {
            concurso = getConcursoDAO().recuperarConcurso(i, TiposConcurso.MEGA_SENA.sigla, 1);
            for (Sorteio sorteio : concurso.getSorteios()){
                dezenasAIgnorar.addAll(sorteio.getDezenas());    
            }
        }

        LOGGER.debug(String.format("RegraSorteiosAnteriores: Dezenas a ignorar (sairam nos %s ultimos sorteios) [%s]",
                qtConcursos, dezenasAIgnorar));

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

            LOGGER.debug("RegraSorteiosAnteriores: " + (Float.valueOf(aRemover.size()) / total * 100));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RegraDezenasAnteriores regra = new RegraDezenasAnteriores(2);

        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });

        regra.aplicar(p);
    }
}
