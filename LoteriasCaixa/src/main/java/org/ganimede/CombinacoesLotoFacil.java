package org.ganimede;

import java.math.BigDecimal;

public class CombinacoesLotoFacil extends BaseCombinacoes {

    public static void main(String[] args) {
        BaseCombinacoes p = new CombinacoesLotoFacil();
        p.gerarProvaHTML(10, 5, 3);
        // p.gerarProva(10, 5, 3);
    }

    @Override
    int menorFaixaPremiavel() {
        return 11;
    }

    @Override
    BigDecimal valorAposta(int numeroPrognosticos) {

        if (numeroPrognosticos == 15) {
            return BigDecimal.valueOf(2f);
        }
        if (numeroPrognosticos == 16) {
            return BigDecimal.valueOf(32f);
        }
        if (numeroPrognosticos == 17) {
            return BigDecimal.valueOf(272f);
        }
        if (numeroPrognosticos == 18) {
            return BigDecimal.valueOf(1632f);
        }
        throw new RuntimeException("Nao suportado!");
    }

    @Override
    BigDecimal valorPremio(int acertos) {

        if (acertos == 15) {
            return BigDecimal.valueOf(524104f);
        }
        if (acertos == 14) {
            return BigDecimal.valueOf(1327.27f);
        }
        if (acertos == 13) {
            return BigDecimal.valueOf(20f);
        }
        if (acertos == 12) {
            return BigDecimal.valueOf(8f);
        }
        if (acertos == 11) {
            return BigDecimal.valueOf(4f);
        }

        return BigDecimal.ZERO;
    }

    @Override
    int maiorFaixaPremiavel() {
        return 15;
    }

    @Override
    int numeroDezenas() {
        return 25;
    }

    @Override
    GerarCombinacoesBase gerarCombinacoes() {
        return new GerarCombinacoesLotoFacil();
    }
}
