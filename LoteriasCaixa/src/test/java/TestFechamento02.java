import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.utils.Combinations;

public class TestFechamento02 {
    public static void main(String[] args) {

        // base
        int m = 15;

        int n = 9;
        
        int garantia = 5;

        Integer[] base = new Integer[m];
        for (int i = 0; i < m; i++) {
            base[i] = i + 1;
        }

        List<Integer[]> reserva = new ArrayList<>();

        List<Integer[]> aps = Combinations.comb(m, n);

        List<Integer[]> aps2 = Combinations.comb(m, garantia);
        for (int i = 0; i < aps2.size(); i++) {
            System.out.println(Arrays.toString(aps2.get(i)));
            for (int j = 0; j < aps.size(); j++) {
                if (existeCombinacao(reserva, aps2.get(i))) {
                    break;
                } else if (estaContido(aps.get(j), aps2.get(i))) {
                    System.out.println(Arrays.toString(aps.get(j)));
                    reserva.add(aps.get(j));
                    break;
                }
            }
        }

        System.out.println("\n\n=========================================\n");

        for (int i = 0; i < reserva.size(); i++) {
            System.out.println(Arrays.toString(reserva.get(i)));
        }

        System.out.println(reserva.size());
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
