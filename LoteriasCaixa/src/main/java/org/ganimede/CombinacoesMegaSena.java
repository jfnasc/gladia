package org.ganimede;

import java.math.BigDecimal;

public class CombinacoesMegaSena extends BaseCombinacoes {
    public static void main(String[] args) {
        BaseCombinacoes p = new CombinacoesMegaSena();
        p.gerarProvaHTML(15, 6);
        //p.gerarProvaHTML(30, 8);
    }

    @Override
    BigDecimal valorAposta(int numeroPrognosticos) {
        switch (numeroPrognosticos) {
        case 6:
            return BigDecimal.valueOf(3.5f);
        case 7:
            return BigDecimal.valueOf(24.5f);
        case 8:
            return BigDecimal.valueOf(98f);
        case 9:
            return BigDecimal.valueOf(294f);
        case 10:
            return BigDecimal.valueOf(735f);
        case 11:
            return BigDecimal.valueOf(1617f);
        case 12:
            return BigDecimal.valueOf(3234f);
        case 13:
            return BigDecimal.valueOf(6006f);
        case 14:
            return BigDecimal.valueOf(10510.50f);
        case 15:
            return BigDecimal.valueOf(17517.50f);
        }

        throw new RuntimeException("Não suportado!");
    }

    @Override
    int menorFaixaPremiavel() {
        return 4;
    }

    @Override
    BigDecimal valorPremio(int numeroAcertos) {
        switch (numeroAcertos) {
        case 4:
            return BigDecimal.valueOf(440.56f);
        case 5:
            return BigDecimal.valueOf(17070f);
        case 6:
            return BigDecimal.valueOf(31598692.07f);
        }

        throw new RuntimeException("Não suportado!");
    }

    @Override
    int maiorFaixaPremiavel() {
        return 6;
    }

    @Override
    int numeroDezenas() {
        return 60;
    }

    @Override
    GerarCombinacoesBase gerarCombinacoes() {
        return new GerarCombinacoesMS();
    }
}
