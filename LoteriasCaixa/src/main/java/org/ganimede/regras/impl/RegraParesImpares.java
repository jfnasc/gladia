/**
 * 
 */
package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.List;

import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

/**
 * @author josen
 * 
 */
public class RegraParesImpares extends RegraBase implements Regra {

    @Override
    public boolean validar(Integer[] aposta) {
        int countPares = 0;
        int countImpares = 0;

        for (Integer dezena : aposta) {
            if (dezena % 2 == 0) {
                ++countPares;
            } else {
                ++countImpares;
            }
        }

        int factor = aposta.length / 2;

        return countPares == factor || countImpares == factor;
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 2, 2, 2, 2, 2, 2 });
        p.add(new Integer[] { 8, 9, 1, 2, 3, 4 });
        p.add(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 });

        Regra regra = new RegraParesImpares();
        regra.aplicar(p);
    }

}
