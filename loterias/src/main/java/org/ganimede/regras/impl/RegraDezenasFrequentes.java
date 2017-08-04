package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.List;

import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

public class RegraDezenasFrequentes extends RegraBase implements Regra {

    private int qtConcursos;
    private TiposConcurso tpConcurso;

    public RegraDezenasFrequentes(TiposConcurso tpConcurso, int qtConcursos) {
        this.tpConcurso = tpConcurso;
        this.qtConcursos = qtConcursos;
    }

    @Override
    public boolean validar(Integer[] aposta) {
        for (Integer dezena : aposta) {
            if (getResultadoDAO().isDezenaFrequente(tpConcurso.sigla, dezena, this.qtConcursos)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });
        p.add(new Integer[] { 60, 59, 58, 57, 56, 55 });

        Regra rs = new RegraDezenasFrequentes(TiposConcurso.QUINA, 10);
        rs.aplicar(p);
    }

}
