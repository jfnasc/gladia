import java.text.DecimalFormat;

import org.ganimede.ImagemResultado;
import org.ganimede.TiposConcurso;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;

public class TestGerarImagem {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("##0.00");

        ConcursoDAO dao = new ConcursoDAOImpl();

        for (int i = 1; i <= dao.recuperarUltimoConcurso(TiposConcurso.QUINA).getNuConcurso(); i++) {
            Integer[] a = dao.recuperarConcurso(i, TiposConcurso.QUINA).getSorteios().get(0)
                    .getDezenasAsArray();
            ImagemResultado.write(TiposConcurso.QUINA, i, a);
        }
    }
}
