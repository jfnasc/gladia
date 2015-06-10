/**
 * 
 */
package org.ganimede.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

/**
 * @author josen
 * 
 */
public class RegraParesImpares extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraParesImpares.class);

    /*
     * (non-Javadoc)
     * 
     * @see daikoku.Regra#aplicar(java.util.List)
     */
    @Override
    public void aplicar(List<Integer[]> apostas) {

        if (apostas == null || apostas.size() == 0) {
            return;
        }

        float total = Float.valueOf(apostas.size());

        List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

        for (int i = 0; i < apostas.size(); i++) {
            int countPares = 0;

            for (int k = 0; k < apostas.get(i).length; k++) {
                if ((apostas.get(i)[k] % 2) == 0) {
                    ++countPares;
                }
            }

            if (countPares < (apostas.size() / 2)) {
                toRemoveList.add(apostas.get(i));
            }
        }

        apostas.removeAll(toRemoveList);

        LOGGER.debug("RegraParesImpares: " + (Float.valueOf(toRemoveList.size()) / total * 100));
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 2, 2, 2, 2, 2, 2 });
        p.add(new Integer[] { 8, 9, 1, 2, 3, 4 });

        Regra regra = new RegraParesImpares();
        regra.aplicar(p);
    }
}
