package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.ResultadoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;
import org.ganimede.dao.impl.ResultadoDAOImpl;
import org.ganimede.regras.Regra;
import org.ganimede.utils.RndUtils;

public abstract class GerarCombinacoesBase {

    static ConcursoDAO concursoDAO = null;

    static ResultadoDAO resultadoDAO = null;

    abstract int qtDezenas();

    abstract List<Regra> regras();

    public static void fechamento(List<List<Integer>> apostas, Integer[] p, int pos, int size) {
        List<Integer> base = Arrays.asList(p).subList(pos, pos + size);

        for (Integer dezena : p) {
            List<Integer> aposta = new ArrayList<>();
            aposta.addAll(base);

            if (!base.contains(dezena)) {
                aposta.add(dezena);
                // Collections.sort(aposta);
                apostas.add(aposta);
            }
        }
    }

    /**
     * 
     * @param quantidade
     * @return
     */
    public List<Integer[]> prognosticos(Integer quantidade, Integer nuPrognosticos) {
        
        List<Integer[]> result = new ArrayList<Integer[]>();

        while (result.size() < quantidade) {

            for (int i = 0; i < quantidade; i++) {
                result.add(gerarAposta(nuPrognosticos));
            }

            if (regras() != null) {
                for (Regra regra : regras()) {
                    regra.aplicar(result);
                }
            }
        }

        return result.subList(0, quantidade);
    }

    /**
     * 
     * @return
     */
    public Integer[] gerarAposta(int qtDezenas) {
        
        List<Integer> tmp = new ArrayList<Integer>();

        while (tmp.size() < qtDezenas) {
            Integer p1 = RndUtils.nextInt(1, qtDezenas());
            if (!tmp.contains(p1)) {
                tmp.add(p1);
            }
        }

        Integer[] result = new Integer[qtDezenas];
        tmp.subList(0, qtDezenas).toArray(result);

        return result;
    }

    protected static ConcursoDAO getConcursoDAO() {
        if (concursoDAO == null) {
            concursoDAO = new ConcursoDAOImpl();
        }
        return concursoDAO;
    }

    protected static ResultadoDAO getResultadoDAO() {
        if (resultadoDAO == null) {
            resultadoDAO = new ResultadoDAOImpl();
        }
        return resultadoDAO;
    }
}
