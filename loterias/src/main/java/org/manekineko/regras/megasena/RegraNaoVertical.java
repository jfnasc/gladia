/**
 * 
 */
package org.manekineko.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.manekineko.regras.Regra;
import org.manekineko.regras.RegraBase;

/**
 * @author josen
 * 
 */
public class RegraNaoVertical extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraNaoVertical.class);

    public void aplicar(List<Integer[]> p) {

        if (p == null || p.size() == 0) {
            return;
        }

        float total = Float.valueOf(p.size());

        List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

        for (int i = 0; i < p.size(); i++) {
            for (int k = 0; k < p.get(i).length - 1; k++) {
                if ((p.get(i)[k] + 10) == (p.get(i)[k + 1])) {
                    toRemoveList.add(p.get(i));
                    break;
                }
            }
        }

        p.removeAll(toRemoveList);

        LOGGER.debug("RegraNaoVertical: " + (Float.valueOf(toRemoveList.size()) / total * 100));
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 01,11,21,31,41,51 });

        Regra regra = new RegraNaoVertical();
        regra.aplicar(p);
    }
}
