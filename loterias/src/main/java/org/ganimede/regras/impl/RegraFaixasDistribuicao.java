package org.ganimede.regras.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.TiposConcurso;
import org.ganimede.regras.Regra;
import org.ganimede.regras.RegraBase;

/**
 * Determina como as dezenas estão distribuidas em faixas na aposta, por
 * exemplo, pode ser determinado que uma aposta aposta ao menos uma dezenas
 * entre 1 e 20, uma entre 21 e 30 e assim por diante
 * 
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public class RegraFaixasDistribuicao extends RegraBase implements Regra {
    private TiposConcurso tpConcurso;
    private int[] nuFaixas;

    public RegraFaixasDistribuicao(TiposConcurso tpConcurso, int[] nuFaixas) {
        this.tpConcurso = tpConcurso;
        this.nuFaixas = nuFaixas;

        getLogger().debug(String.format("Distribuicao: %s", Arrays.toString(this.nuFaixas)));
        if (this.tpConcurso.nuDezenas % this.nuFaixas.length != 0) {
            throw new RuntimeException(String.format("O numero de faixas [%s] é o "
                            + "o numero de dezenas [%s] é incompatível! A divisão deve ser exata.",
                            this.nuFaixas.length, this.tpConcurso.nuDezenas));
        }
    }

    @Override
    public boolean validar(Integer[] dezenas) {

        int tamanho = this.tpConcurso.nuDezenas / this.nuFaixas.length;

        for (int i = 0; i < this.nuFaixas.length; i++) {
            int inicio = (i * tamanho) + 1;
            int fim = (i + 1) * tamanho;

            boolean existe = false;

            for (Integer dezena : dezenas) {
                if (dezena >= inicio && dezena <= fim) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Regra regra = new RegraFaixasDistribuicao(TiposConcurso.QUINA, new int[] { 1, 1, 1, 1 });

        List<Integer[]> p = new ArrayList<>();

        p.add(new Integer[] { 1, 2, 3, 4, 5 });
        p.add(new Integer[] { 7, 19, 30, 35, 60 });
        p.add(new Integer[] { 1, 21, 41, 61, 71 });

        regra.aplicar(p);
    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger(RegraFaixasDistribuicao.class);
    }
}
