import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.utils.Combinations;

public class TestFechamento {
    public static void main(String[] args) {
        
        // base
        int n = 10;
        
        int tamanho = 5;
        
        // dezenas variaveis
        int m = 3;
        
        // dezenas fixas 
        int p = tamanho - m;

        Integer[] base = new Integer[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }        
        
        List<Integer[]> result = new ArrayList<>();
        
        List<Integer[]> aps = Combinations.comb(n, m);
        for (Integer[] ap : aps) {
            System.out.println(Arrays.toString(ap));
            
            List<Integer[]> aps2 = Combinations.comb(remover(base, ap), p);
            for (Integer[] ap2 : aps2) {
                //System.out.println(Arrays.toString(ap2));
                //System.out.println(Arrays.toString(combinar(ap, ap2)));
                if (!existeCombinacao(result, ap)){
                    result.add(combinar(ap, ap2));
                    break;
                }
            }
        }
        
        for (Integer[] i : result) {
            System.out.println(Arrays.toString(i));
        }
        
        System.out.println(result.size());
    }

    public static Integer[] remover(Integer[] base, Integer[] lista) {
        List<Integer> tmp = new ArrayList<>();
        tmp.addAll(Arrays.asList(base));
        tmp.removeAll(Arrays.asList(lista));

        Integer[] result = new Integer[tmp.size()];
        tmp.toArray(result);

        return result;
    }

    public static Integer[] combinar(Integer[] base, Integer[] combinacao) {
        List<Integer> tmp = new ArrayList<>();
        tmp.addAll(Arrays.asList(base));
        tmp.addAll(Arrays.asList(combinacao));

        Integer[] result = new Integer[tmp.size()];
        tmp.toArray(result);

        return result;
    }

    public static boolean estaContido(Integer[] base, Integer[] combinacao) {
        for (Integer dezena : combinacao) {
            if (!Arrays.asList(base).contains(dezena)) {
                return false;
            }
        }

        return true;
    }

    public static boolean existeCombinacao(List<Integer[]> apostas, Integer[] combinacao) {
        for (Integer[] aposta : apostas) {
            if (estaContido(aposta, combinacao)) {
                return true;
            }
        }
        return false;
    }

}
