package org.ganimede.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.TiposConcurso;
import org.ganimede.dao.AnaliseDAO;
import org.ganimede.dao.BaseDAO;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.services.ServiceConfig;

public class AnaliseParesImparesDAOImpl extends BaseDAO implements AnaliseDAO {

    ConcursoDAO concursoDAO = null;

    public static void main(String[] args) {
        AnaliseDAO analise = new AnaliseParesImparesDAOImpl();
        analise.executar(TiposConcurso.QUINA, 1);
    }

    @Override
    public void executar(TiposConcurso tpConcurso, int nuSorteio) {
        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(tpConcurso);

        Map<String, Integer> totalizador = new HashMap<>();
        int count = 0;

        for (int i = 1; i <= ultimoConcurso.getNuConcurso(); i++) {

            Concurso concurso = getConcursoDAO().recuperarConcurso(i, tpConcurso);

            for (Sorteio sorteio : concurso.getSorteios()) {
                int countPares = 0;
                int countImpares = 0;

                for (int dezena : sorteio.getDezenas()) {
                    if (dezena % 2 == 0) {
                        countPares++;
                    } else {
                        countImpares++;
                    }
                }

                String key = String.format("%s/%s", countPares, countImpares);
                if (totalizador.get(key) == null) {
                    totalizador.put(key, 0);
                }
                totalizador.put(key, totalizador.get(key) + 1);
                count++;
            }
        }

        for (String key : totalizador.keySet()) {
            float percent = (Float.valueOf(totalizador.get(key)) / Float.valueOf(count)) * 100;
            System.out.println(String.format("%s\t%s", key, ServiceConfig.df.format(percent)));
        }
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
