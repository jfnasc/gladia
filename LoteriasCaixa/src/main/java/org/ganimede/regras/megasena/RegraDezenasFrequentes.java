package org.ganimede.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;
import org.ganimede.utils.MathUtils;

public class RegraDezenasFrequentes extends RegraBase implements Regra {

    private int qtConcursos;

    public RegraDezenasFrequentes(int qtConcursos) {
        this.qtConcursos = qtConcursos;
    }

    private Logger LOGGER = Logger.getLogger(RegraDezenasFrequentes.class);

    @Override
    public void aplicar(List<Integer[]> p) {

        if (p == null || p.size() == 0) {
            return;
        }

        float total = Float.valueOf(p.size());

        try {

            List<Integer[]> aRemover = new ArrayList<Integer[]>();

            for (Integer[] aposta : p) {
                
                boolean valido = false;
                
                for (Integer dezena : aposta) {
                    if (getResultadoDAO().isDezenaFrequente(TiposConcurso.MEGA_SENA.sigla, dezena, this.qtConcursos)) {
                        valido = true;
                        break;
                    }
                }

                if (!valido) {
                    aRemover.add(aposta);
                }
            }

            p.removeAll(aRemover);

            LOGGER.debug("RegraDezenasFrequentes: " + (Float.valueOf(aRemover.size()) / total * 100));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });
        p.add(new Integer[] { 60, 59, 58, 57, 56, 55 });

        Regra rs = new RegraDezenasFrequentes(10);
        rs.aplicar(p);
    }
}
