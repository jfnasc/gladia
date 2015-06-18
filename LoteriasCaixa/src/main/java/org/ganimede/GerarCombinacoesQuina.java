/**
 * 
 */
package org.ganimede;

import java.util.List;

import org.ganimede.regras.Regra;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public class GerarCombinacoesQuina extends GerarCombinacoesBase {

    public static void main(String[] args) {
        BaseCombinacoes p = new CombinacoesQuina();
        p.gerarProvaHTML(13, 5, 2);
        // p.gerarProva(10, 5, 3);
    }

    @Override
    int qtDezenas() {
        return 80;
    }

    @Override
    List<Regra> regras() {
        // TODO Auto-generated method stub
        return null;
    }

}
