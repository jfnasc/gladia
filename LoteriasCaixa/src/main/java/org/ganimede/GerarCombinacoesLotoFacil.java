/**
 * 
 */
package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.regras.Regra;
import org.ganimede.utils.StringUtils;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public class GerarCombinacoesLotoFacil extends GerarCombinacoesBase {
    
    private static GerarCombinacoesBase gb = new GerarCombinacoesLotoFacil();
    
    public static void main(String[] args) {
        List<Integer[]> prognosticos = gb.prognosticos(5, TiposConcurso.LOTO_FACIL.maiorFaixaPremiavel);

        System.out.println("--------------------------");
        System.out.println("Boa Sorte!");
        System.out.println("--------------------------");
        for (Integer[] aposta : prognosticos) {
            Arrays.sort(aposta);
            StringUtils.print(aposta);
        }

    }
    
    @Override
    int qtDezenas() {
        return 25;
    }

    @Override
    List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();

        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
        regras.add(new org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.LOTO_FACIL, 1, 9));

        return regras;
    }

}
