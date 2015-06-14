import java.util.Arrays;
import java.util.List;

import org.ganimede.RegistrarEventosSISAR;

public abstract class BaseCombinacoes {
    public void executar() {
        int n = 10;

        Integer[] base = new Integer[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }

        List<Integer[]> apostas = RegistrarEventosSISAR.gerar(base, 3, 5);
        gerarProva(base, apostas);
    }

    abstract Integer[] simularResultado();

    abstract int getLimite();

    abstract int getNumeroMinimoAcertos();

    private void gerarProva(Integer[] base, List<Integer[]> apostas) {
        int tentativas = 0;
        while (tentativas < getLimite()) {

            Integer[] resultado = simularResultado();
            Arrays.sort(resultado);

            if (RegistrarEventosSISAR.conferir(resultado, base) > getNumeroMinimoAcertos()) {
                float premio = Float.valueOf(0);
                for (Integer[] aposta : apostas) {
                    Arrays.sort(aposta);

                    int acertos = RegistrarEventosSISAR.conferir(resultado, aposta);

                    if (acertos > 2) {
                        if (acertos == 3) {
                            premio += 270.26;
                        }
                        if (acertos == 4) {
                            premio += 18812.46;
                        }
                        if (acertos == 5) {
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

        System.out.println(tentativas);
    }
}
