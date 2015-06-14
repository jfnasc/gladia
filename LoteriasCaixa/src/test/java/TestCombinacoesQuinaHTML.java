public class TestCombinacoesQuinaHTML extends BaseCombinacoesHTML {

    public static void main(String[] args) {
        BaseCombinacoesHTML p = new TestCombinacoesQuinaHTML();
        p.gerar();
    }

    @Override
    int getNumeroPrognosticos() {
        return 5;
    }

    @Override
    int getMinimoAcertos() {
        return 3;
    }

    @Override
    int getMenorFaixaPremiavel() {
        return 3;
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
    boolean isPararSeErro() {
        return false;
    }

    @Override
    float getValorPremio(int acertos) {

        if (acertos == 3) {
            return 215.10f;
        }
        if (acertos == 4) {
            return 10635.79f;
        }
        if (acertos == 5) {
            return 610494.27f;
        }

        return 0;
    }

    @Override
    float getValorAposta(int numeroPrognosticos) {

        if (numeroPrognosticos == 5) {
            return 1.50f;
        }
        if (numeroPrognosticos == 6) {
            return 7.50f;
        }
        if (numeroPrognosticos == 7) {
            return 20f;
        }

        throw new RuntimeException("Nao suportado!");
    }
}
