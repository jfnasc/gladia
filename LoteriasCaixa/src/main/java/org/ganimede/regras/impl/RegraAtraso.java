/**
 * 
 */
package org.ganimede.regras.impl;

import java.util.ArrayList;
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
public class RegraAtraso extends RegraBase implements Regra {

    private static final int QT_MINIMA_DEZENAS = 2;
    private int qtConcursos;
    private TiposConcurso tpConcurso;

    public RegraAtraso(TiposConcurso tpConcurso, int qtConcursos) {
        this.tpConcurso = tpConcurso;
        this.qtConcursos = qtConcursos;
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 1, 2, 3, 4, 5, 6 });
        p.add(new Integer[] { 8, 9, 1, 2, 3, 4 });
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });

        Regra regra = new RegraAtraso(TiposConcurso.QUINA, 8);
        regra.aplicar(p);

        for (Integer[] p1 : p) {
            StringUtils.print(p1);
        }

    }

    @Override
    public boolean validar(Integer[] aposta) {
        int count = 0;

        for (Integer dezena : aposta) {
            int qtConcursosAtraso = getResultadoDAO().qtAtrasoDezena(this.tpConcurso.sigla, 1, dezena);
            if (qtConcursosAtraso >= this.qtConcursos) {
                count++;
            }
        }

        return count >= QT_MINIMA_DEZENAS;
    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger(getClass());
    }
}
