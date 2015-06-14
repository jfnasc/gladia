import org.ganimede.RegistrarEventosSISAR;

public class TestCombinacoesQuina extends BaseCombinacoes {

    public static void main(String[] args) {
        BaseCombinacoes p = new TestCombinacoesQuina();
        p.executar();
    }

    @Override
    Integer[] simularResultado() {
        return RegistrarEventosSISAR.simularResultado(5, 80);
    }

    @Override
    int getLimite() {
        return 1000;
    }

    @Override
    int getNumeroMinimoAcertos() {
        return 3;
    }
}
