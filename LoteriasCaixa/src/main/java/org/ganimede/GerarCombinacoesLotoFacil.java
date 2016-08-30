/**
 * 
 */
package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        List<Integer[]> prognosticos = gb.prognosticos(1, 18);

        System.out.println("--------------------------");
        System.out.println("Boa Sorte!");
        System.out.println("--------------------------");
        for (Integer[] aposta : prognosticos) {
            Arrays.sort(aposta);
            StringUtils.print(aposta);
        }

        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.LOTO_FACIL);

        System.out.println("------------------------------------------------");
        System.out.println("Ultimas sorteadas");
        System.out.println("------------------------------------------------");
        for (Sorteio sorteio : ultimoConcurso.getSorteios()) {
            System.out.println(sorteio.getDezenas());
        }

        System.out.println("------------------------------------------------");
        System.out.println("Atrasos");
        System.out.println("------------------------------------------------");
        List<Integer> atrasos = getResultadoDAO().buscarDezenasEmAtraso(TiposConcurso.LOTO_FACIL.sigla, 1, 2);
        Collections.sort(atrasos);
        System.out.println(atrasos);
    }
    
    @Override
    public int qtDezenas() {
        return TiposConcurso.LOTO_FACIL.nuDezenas;
    }

    @Override
    public List<Regra> regras() {
        List<Regra> regras = new ArrayList<Regra>();

        regras.add(new org.ganimede.regras.impl.RegraParesImpares());
        //regras.add(new org.ganimede.regras.impl.RegraDezenasAnteriores(TiposConcurso.LOTO_FACIL, 1, 9));

        return regras;
    }

}
