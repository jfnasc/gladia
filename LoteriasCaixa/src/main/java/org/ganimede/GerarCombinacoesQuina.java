/**
 * 
 */
package org.ganimede;

import java.util.ArrayList;
import java.util.List;

import org.ganimede.regras.Regra;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public class GerarCombinacoesQuina extends GerarCombinacoesBase {

    public static void main(String[] args) {
        BaseCombinacoes p = new CombinacoesQuina();
        p.gerarProvaHTML(10, 5, 3);
    }

    @Override
    int qtDezenas() {
        return 80;
    }

    @Override
    List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();

        regras.add(new org.ganimede.regras.megasena.RegraNaoSequencial());
        regras.add(new org.ganimede.regras.megasena.RegraParesImpares());
        regras.add(new org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.QUINA.sigla, 3));

        return regras;
    }

}
