public class TestCombinacoesMegaSenaHTML extends BaseCombinacoesHTML {

    public static void main(String[] args) {
        BaseCombinacoesHTML p = new TestCombinacoesMegaSenaHTML();
        p.gerar();
    }

    @Override
    int getNumeroPrognosticos() {
        return 6;
    }

    @Override
    int getMinimoAcertos() {
        return 4;
    }

    @Override
    int getMenorFaixaPremiavel() {
        return 4;
    }

    @Override
    boolean isPararSeErro() {
        return false;
    }

    @Override
    int getTamanhoBase() {
        return 10;
    }

    @Override
    int getNumeroDezenasFixas() {
        return 3;
    }

    @Override
    float getValorPremio(int numeroAcertos) {

        if (numeroAcertos == 4) {
            return 455.17f;
        }
        if (numeroAcertos == 5) {
            return 25199.07f;
        }
        if (numeroAcertos == 6) {
            return 29938493.60f;
        }

        return 0;
    }

    @Override
    float getValorAposta(int numeroPrognosticos) {
        
        if (numeroPrognosticos == 6) {
            return 3.50f;
        }
        if (numeroPrognosticos == 7) {
            return 24.50f;
        }
        if (numeroPrognosticos == 8) {
            return 98.00f;
        }
        if (numeroPrognosticos == 9) {
            return 294f;
        }
        if (numeroPrognosticos == 10) {
            return 735f;
        }
        if (numeroPrognosticos == 11) {
            return 1617f;
        }
        if (numeroPrognosticos == 12) {
            return 3234f;
        }
        if (numeroPrognosticos == 13) {
            return 6006f;
        }
        if (numeroPrognosticos == 14) {
            return 10510.50f;
        }
        if (numeroPrognosticos == 15) {
            return 17517.50f;
        }

        throw new RuntimeException("Nao suportado!");
    }
}
