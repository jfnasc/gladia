package org.ganimede.teoremas;

import java.util.Arrays;
import java.util.List;

import org.ganimede.GerarCombinacoesBase;
import org.ganimede.GerarCombinacoesMegaSena;

public class BaseFixa {
    public static void main(String[] args) {
        // escolha 3 numeros, eles serão repetidos em todas as combincoes
        Integer[] base = new Integer[] { 1, 2, 3 };

        // escolha o numero de combinacoes
        int quantidade = 10;

        GerarCombinacoesBase g = new GerarCombinacoesMegaSena();
        List<Integer[]> a = g.gerarPrognosticos(quantidade, 3);
        for (Integer[] complemento : a) {
            System.out.println(Arrays.toString(base) + Arrays.toString(complemento));
        }
    }
}
