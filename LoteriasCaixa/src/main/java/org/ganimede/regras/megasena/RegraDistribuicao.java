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
public class RegraDistribuicao extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraNaoVertical.class);

    public void aplicar(List<Integer[]> apostas) {

        if (apostas == null || apostas.size() == 0) {
            return;
        }

        float total = Float.valueOf(apostas.size());

        List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

        for (Integer[] aposta : apostas) {

            boolean valido = true;

            if (!possuiAoMenosUma(aposta, 1, 20, aposta.length / 3)) {
                valido = false;
            }

            if (valido && !possuiAoMenosUma(aposta, 21, 40, aposta.length / 3)) {
                valido = false;
                break;
            }

            if (valido && !possuiAoMenosUma(aposta, 41, 60, aposta.length / 3)) {
                valido = false;
                break;
            }

            if (!valido) {
                toRemoveList.add(aposta);
            }
        }

        apostas.removeAll(toRemoveList);

        LOGGER.debug("RegraDistribuicao: " + (Float.valueOf(toRemoveList.size()) / total * 100));
    }

    private boolean possuiAoMenosUma(Integer[] dezenas, int start, int end, int qtOcorrencias) {
        int count = 0;
        for (Integer dezena : dezenas) {
            if (dezena >= start && dezena <= end) {
                count++;
            }
        }
        return count >= qtOcorrencias;
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 01, 11, 21, 31, 41, 51 });

        Regra regra = new RegraDistribuicao();
        regra.aplicar(p);
    }
}
