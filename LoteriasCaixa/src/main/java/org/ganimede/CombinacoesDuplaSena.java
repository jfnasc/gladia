package org.ganimede;
import java.math.BigDecimal;

public class CombinacoesDuplaSena extends BaseCombinacoes {

    public static void main(String[] args) {
        BaseCombinacoes p = new CombinacoesDuplaSena();
        p.gerarProvaHTML(10, 6, 4);
    }

    @Override
    Integer[] simularResultado() {
        return Combinacoes.simularResultado(6, 50);
    }

    @Override
    int menorFaixaPremiavel() {
        return 4;
    }

    @Override
    BigDecimal valorAposta(int numeroPrognosticos) {
        switch (numeroPrognosticos) {
        case 6:
            return BigDecimal.valueOf(2.0f);
        case 7:
            return BigDecimal.valueOf(14f);
        case 8:
            return BigDecimal.valueOf(56f);
        case 9:
            return BigDecimal.valueOf(168f);
        case 10:
            return BigDecimal.valueOf(420f);
        case 11:
            return BigDecimal.valueOf(924f);
        case 12:
            return BigDecimal.valueOf(1848f);
        case 13:
            return BigDecimal.valueOf(3432f);
        case 14:
            return BigDecimal.valueOf(6006f);
        case 15:
            return BigDecimal.valueOf(10010f);
        }

        throw new RuntimeException("Não suportado!");
    }

    @Override
    BigDecimal valorPremio(int acertos) {

        switch (acertos) {
        case 4:
            return BigDecimal.valueOf(2979726.32f);
        case 5:
            return BigDecimal.valueOf(8219.01f);
        case 6:
            return BigDecimal.valueOf(107.08f);
        }

        return BigDecimal.ZERO;
    }

    @Override
    int maiorFaixaPremiavel() {
        return 6;
    }

    @Override
    int numeroDezenas() {
        return 50;
    }

    @Override
    GerarCombinacoesBase gerarCombinacoes() {
        return null;
    }
}
