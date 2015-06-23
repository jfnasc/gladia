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
public class GerarCombinacoesLotoFacil extends GerarCombinacoesBase {

    public static void main(String[] args) {
        BaseCombinacoes p = new CombinacoesLotoFacil();
        p.gerarProvaHTML(15, 15, 8);
    }

    @Override
    int qtDezenas() {
        return 25;
    }

    @Override
    List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();
        return regras;
    }

}
