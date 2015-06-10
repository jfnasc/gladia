/**
 * 
 */
package org.ganimede.regras.megasena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

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

        for (Integer[] aposta : p) {
            for (Integer dezena : aposta) {
                if (Arrays.asList(aposta).contains(dezena + 10)){
                    toRemoveList.add(aposta);
                    break;
                }
            }
        }

        p.removeAll(toRemoveList);

        LOGGER.debug("RegraNaoVertical: " + (Float.valueOf(toRemoveList.size()) / total * 100));
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 01, 11, 21, 31, 41, 51 });

        Regra regra = new RegraNaoVertical();
        regra.aplicar(p);
    }
}
