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
public class RegraNaoSequencial extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraNaoSequencial.class);

    public void aplicar(List<Integer[]> apostas) {

        if (apostas == null || apostas.isEmpty()) {
            return;
        }

        float total = Float.valueOf(apostas.size());

        List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

        for (Integer[] aposta : apostas) {
            for (Integer dezena : aposta) {
                if (Arrays.asList(aposta).contains(dezena + 1)) {
                    toRemoveList.add(aposta);
                    break;
                }
            }
        }

        apostas.removeAll(toRemoveList);

        LOGGER.debug(Float.valueOf(toRemoveList.size()) / total * 100);
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 1, 2, 6, 4, 5, 3 });
        p.add(new Integer[] { 9, 8, 1, 2, 3, 4 });
        p.add(new Integer[] { 1, 5, 7, 9, 13, 15 });

        Regra regra = new RegraNaoSequencial();
        regra.aplicar(p);
    }
}
