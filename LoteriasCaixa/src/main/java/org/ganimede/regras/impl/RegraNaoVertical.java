/**
 * 
 */
package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;
import org.ganimede.utils.StringUtils;

/**
 * @author josen
 * 
 */
public class RegraNaoVertical extends RegraBase implements Regra {
    
    private TiposConcurso tpConcurso;
    
    public RegraNaoVertical(TiposConcurso tpConcurso){
        this.tpConcurso = tpConcurso;
    }
    
    @Override
    public boolean validar(Integer[] aposta) {
        int count = 0;

        for (Integer dezena : aposta) {
            for (int i = 1; i < (this.tpConcurso.nuDezenas / 10); i++) {
                if (Arrays.asList(aposta).contains(dezena + (10 * i))) {
                    count++;
                }
            }
        }

        return count == 0;
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 01, 18, 21, 32, 31, 52 });

        Regra regra = new RegraNaoVertical(TiposConcurso.MEGA_SENA);
        regra.aplicar(p);

        for (Integer[] aposta : p) {
            StringUtils.print(aposta);
        }
    }

}
