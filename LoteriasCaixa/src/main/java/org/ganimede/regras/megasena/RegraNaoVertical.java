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
import org.ganimede.utils.StringUtils;

/**
 * @author josen
 * 
 */
public class RegraNaoVertical extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraNaoVertical.class);

    public void aplicar(List<Integer[]> apostas) {

        if (apostas == null || apostas.size() == 0) {
            return;
        }

        float total = Float.valueOf(apostas.size());

        List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

        for (Integer[] aposta : apostas) {

            int count = 0;

            for (Integer dezena : aposta) {
                for (int i = 1; i < 6; i++) {
                    if (Arrays.asList(aposta).contains(dezena + (10 * i))) {
                        count++;
                    }
                }
            }

            if (count > 0) {
                toRemoveList.add(aposta);
            }
        }

        apostas.removeAll(toRemoveList);

        LOGGER.debug("RegraNaoVertical: " + (Float.valueOf(toRemoveList.size()) / total * 100));
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 01, 18, 21, 32, 41, 52 });

        Regra regra = new RegraNaoVertical();
        regra.aplicar(p);

        for (Integer[] aposta : p) {
            StringUtils.print(aposta);
        }
    }
}
