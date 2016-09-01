package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.List;

import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;
import org.ganimede.utils.MathUtils;

public class RegraSorteiosAnteriores extends RegraBase implements Regra {

    private TiposConcurso tpConcurso;

    public RegraSorteiosAnteriores(TiposConcurso tpConcurso) {
        this.tpConcurso = tpConcurso;
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });
        p.add(new Integer[] { 2, 25, 36, 41, 42, 53 });

        RegraSorteiosAnteriores rs = new RegraSorteiosAnteriores(TiposConcurso.MEGA_SENA);
        rs.aplicar(p);
    }

    @Override
    public boolean validar(Integer[] aposta) {
        return !getResultadoDAO().existeSorteioIgual(tpConcurso.sigla, MathUtils.hash(aposta));
    }
}
