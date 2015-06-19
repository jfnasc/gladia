package org.ganimede;

import java.math.BigDecimal;

public class CombinacoesQuina extends BaseCombinacoes {

    public static void main(String[] args) {
        BaseCombinacoes p = new CombinacoesQuina();
        p.gerarProvaHTML(10, 5, 3);
        // p.gerarProva(10, 5, 3);
    }

    @Override
    Integer[] simularResultado() {
        return Combinacoes.simularResultado(5, 80);
    }

    @Override
    int menorFaixaPremiavel() {
        return 3;
    }

    @Override
    BigDecimal valorAposta(int numeroPrognosticos) {

        if (numeroPrognosticos == 5) {
            return BigDecimal.valueOf(1.50f);
        }
        if (numeroPrognosticos == 6) {
            return BigDecimal.valueOf(7.50f);
        }
        if (numeroPrognosticos == 7) {
            return BigDecimal.valueOf(20f);
        }

        throw new RuntimeException("Nao suportado!");
    }

    @Override
    BigDecimal valorPremio(int acertos) {

        if (acertos == 3) {
            return BigDecimal.valueOf(215.10f);
        }
        if (acertos == 4) {
            return BigDecimal.valueOf(10635.79f);
        }
        if (acertos == 5) {
            return BigDecimal.valueOf(610494.27f);
        }

        return BigDecimal.ZERO;
    }

    @Override
    int maiorFaixaPremiavel() {
        return 5;
    }

    @Override
    int numeroDezenas() {
        return 80;
    }

    @Override
    GerarCombinacoesBase gerarCombinacoes() {
        return new GerarCombinacoesQuina();
    }
}
