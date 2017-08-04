package org.ganimede.regras.impl;

import java.util.Arrays;

import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

public class RegraQuantidadeDezenas extends RegraBase implements Regra {

    private Integer qtMinimo;
    private Integer qtMaximo;
    private Integer[] dezenas;

    public RegraQuantidadeDezenas(Integer[] dezenas, Integer qtMinimo, Integer qtMaximo) {
        this.qtMinimo = qtMinimo;
        this.qtMaximo = qtMaximo;
        this.dezenas = dezenas;
    }

    @Override
    public boolean validar(Integer[] aposta) {
        int count = 0;
        for (Integer dezena : dezenas) {
            if (Arrays.asList(aposta).contains(dezena)) {
                count++;
            }
        }
        return count >= qtMinimo && count <= qtMaximo;
    }

}
