/**
 * 
 */
package org.ganimede.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;
import org.ganimede.utils.StringUtils;

/**
 * @author josen
 * 
 */
public class RegraAtraso extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraAtraso.class);

    private int qtConcursos;

    public RegraAtraso(int qtConcursos) {
        this.qtConcursos = qtConcursos;
    }

    @Override
    public void aplicar(List<Integer[]> apostas) {

        if (apostas == null || apostas.size() == 0) {
            return;
        }

        Float total = Float.valueOf(apostas.size());

        try {

            List<Integer[]> aRemover = new ArrayList<Integer[]>();

            // senas
            for (Integer[] aposta : apostas) {

                int count = 0;

                for (Integer dezena : aposta) {
                    if (getResultadoDAO().isDezenaEmAtrasoMinimo(TiposConcurso.MEGA_SENA.sigla, 1, dezena,
                            this.qtConcursos)) {
                        count++;
                    }
                }

                if (count < 2) {
                    aRemover.add(aposta);
                }
            }

            apostas.removeAll(aRemover);

            LOGGER.debug("RegraAtraso: " + (Float.valueOf(aRemover.size()) / total * 100));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 1, 2, 3, 4, 5, 6 });
        p.add(new Integer[] { 8, 9, 1, 2, 3, 4 });
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });

        Regra regra = new RegraAtraso(8);
        regra.aplicar(p);

        for (Integer[] p1 : p) {
            StringUtils.print(p1);
        }

    }
}
