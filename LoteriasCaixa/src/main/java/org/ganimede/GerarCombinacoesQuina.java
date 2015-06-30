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
    @Override
    int qtDezenas() {
        return TiposConcurso.QUINA.nuDezenas;
    }

    @Override
    List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();

        regras.add(new org.ganimede.regras.impl.RegraFaixasDistribuicao(TiposConcurso.QUINA, 
                new int[] { 1, 1, 1, 1, 1}));
        regras.add(new org.ganimede.regras.impl.RegraNaoSequencial());
        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
        regras.add(new org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.QUINA, 3));

        return regras;
    }

}
