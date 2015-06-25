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

        for (Integer dezena : aposta) {
            if (dezena % 2 == 0) {
                ++countPares;
            }
        }

        return countPares >= aposta.length;
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 2, 2, 2, 2, 2, 2 });
        p.add(new Integer[] { 8, 9, 1, 2, 3, 4 });

        Regra regra = new RegraParesImpares();
        regra.aplicar(p);
    }

}
