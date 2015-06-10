package org.ganimede.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;
import org.ganimede.utils.MathUtils;

public class RegraSorteiosAnteriores extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraSorteiosAnteriores.class);

    @Override
    public void aplicar(List<Integer[]> p) {

        if (p == null || p.size() == 0) {
            return;
        }

        float total = Float.valueOf(p.size());

        try {

            List<Integer[]> aRemover = new ArrayList<Integer[]>();

            // senas
            for (int i = 0; i < p.size(); i++) {
                String hash = MathUtils.hash(p.get(i));
                if (getResultadoDAO().existeSorteioIgual(TiposConcurso.MEGA_SENA.sigla, hash)) {
                    aRemover.add(p.get(i));
                }
            }

            p.removeAll(aRemover);

            LOGGER.debug("RegraSorteiosAnteriores: " + (Float.valueOf(aRemover.size()) / total * 100));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });

        RegraSorteiosAnteriores rs = new RegraSorteiosAnteriores();
        rs.aplicar(p);
    }
}
