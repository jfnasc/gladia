package org.ganimede.dao.impl;

import java.util.List;

import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.TiposConcurso;
import org.ganimede.dao.AnaliseDAO;
import org.ganimede.dao.BaseDAO;
import org.ganimede.dao.ConcursoDAO;

public class AnaliseComposicaoDAOImpl extends BaseDAO implements AnaliseDAO {

    ConcursoDAO concursoDAO = null;

    public static void main(String[] args) {
        AnaliseDAO analise = new AnaliseComposicaoDAOImpl();
        analise.executar(TiposConcurso.LOTO_FACIL, 1);
    }

    @Override
    public void executar(TiposConcurso tpConcurso, int nuSorteio) {

        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(tpConcurso.sigla);

        float count = 0;
        float nuDezenas = 0;
        for (int i = 1; i < ultimoConcurso.getNuConcurso(); i++) {
            Sorteio s1 = getConcursoDAO().recuperarConcurso(i, tpConcurso.sigla, 1).getSorteios().get(0);
            Sorteio s2 = getConcursoDAO().recuperarConcurso(i + 1, tpConcurso.sigla, 1).getSorteios().get(0);
            nuDezenas += (contar(s1.getDezenas(), s2.getDezenas()));
            count++;
        }

        System.out.println(nuDezenas / count);
    }

    private float contar(List<Integer> d1, List<Integer> d2) {
        float count = 0;
        for (Integer dezena : d2) {
            if (d1.contains(dezena)) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return the concursoDAO
     */
    public ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return this.concursoDAO;
    }
}
