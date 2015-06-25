/**
 * 
 */
package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

/**
 * @author josen
 * 
 */
public class RegraNaoSequencial extends RegraBase implements Regra {

    @Override
    public boolean validar(Integer[] aposta) {
        for (Integer dezena : aposta) {
            if (Arrays.asList(aposta).contains(dezena + 1)) {
                return false;
            }
        }
        return true;
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
