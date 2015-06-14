import java.util.Arrays;
import java.util.List;

import org.ganimede.RegistrarEventosSISAR;

public class TestCombinacoesMegaSena {
    public static void main(String[] args) {
        int n = 10;

        Integer[] base = new Integer[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }

        List<Integer[]> apostas = RegistrarEventosSISAR.gerar(base, 4, 5);
        gerarProva(base, apostas);
    }

    public static void gerarProva(Integer[] base, List<Integer[]> apostas) {
        int tentativas = 0;
        while (tentativas < 100) {

            Integer[] resultado = RegistrarEventosSISAR.simularResultado(6, 60);
            Arrays.sort(resultado);

            if (RegistrarEventosSISAR.conferir(resultado, base) > 3) {
                float premio = Float.valueOf(0);
                for (Integer[] aposta : apostas) {
                    Arrays.sort(aposta);

                    int acertos = RegistrarEventosSISAR.conferir(resultado, aposta);

                    if (acertos > 3) {
                        if (acertos == 4) {
                            premio += 270.26;
                        }
                        if (acertos == 5) {
                            premio += 18812.46;
                        }
                        if (acertos == 6) {
                            premio += 658437.00;
                        }
                    }
                }

                if (premio == 0) {
                    throw new RuntimeException("Falha!");
                }

                tentativas++;

                if (tentativas % 100000 == 0) {
                    System.out.println(tentativas);
                }
            }

        }
    }
}
