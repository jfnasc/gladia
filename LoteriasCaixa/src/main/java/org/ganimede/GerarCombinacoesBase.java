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

    public abstract int qtDezenasConcurso();

    public abstract List<Regra> regras();

    public abstract void gerar();

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
    public List<Integer[]> gerarPrognosticos(Integer qtPrognosticos, Integer nuDezenas) {

        List<Integer[]> result = new ArrayList<Integer[]>();

        while (result.size() < qtPrognosticos) {

            for (int i = 0; i < qtPrognosticos; i++) {
                result.add(gerarAposta(nuDezenas));
            }

            if (regras() != null) {
                for (Regra regra : regras()) {
                    regra.aplicar(result);
                }
            }

        }

        return result.subList(0, qtPrognosticos);
    }

    public List<Integer[]> gerarPrognosticos(Integer qtPrognosticos, Integer qtDezenas, Integer[] base) {

        List<Integer[]> result = new ArrayList<Integer[]>();

        while (result.size() < qtPrognosticos) {

            for (int i = 0; i < qtPrognosticos; i++) {
                result.add(gerarAposta(qtDezenas, base));
            }

            if (regras() != null) {
                for (Regra regra : regras()) {
                    regra.aplicar(result);
                }
            }

        }

        return result.subList(0, qtPrognosticos);
    }

    /**
     * 
     * @return
     */
    public Integer[] gerarAposta(int qtDezenas) {

        List<Integer> tmp = new ArrayList<Integer>();

        while (tmp.size() < qtDezenas) {
            Integer p1 = RndUtils.nextInt(1, qtDezenasConcurso());
            if (!tmp.contains(p1)) {
                tmp.add(p1);
            }
        }

        Integer[] result = new Integer[qtDezenas];
        tmp.subList(0, qtDezenas).toArray(result);

        return result;
    }

    public Integer[] gerarAposta(int qtDezenas, Integer[] base) {
        List<Integer> tmp = new ArrayList<Integer>();

        while (tmp.size() < qtDezenas) {
            Integer p1 = base[RndUtils.nextInt(1, base.length)];
            if (p1 != null && !tmp.contains(p1)) {
                tmp.add(p1);
            }
        }

        Integer[] result = new Integer[qtDezenas];
        tmp.subList(0, qtDezenas).toArray(result);
        Arrays.sort(result);

        return result;
    }

    public static void verificarAcertos(List<Integer[]> prognosticos, List<Integer> resultado, Integer qtDezenasPremio) {

        for (Integer[] p : prognosticos) {
            int count = 0;
            for (Integer dezena : resultado) {
                if (Arrays.asList(p).contains(dezena)) {
                    count++;
                }
            }

            System.out.println(Arrays.toString(p) + "\t\t acertos:" + count);

            if (count >= qtDezenasPremio) {
                System.out.println("Ganhador!!!");
            }
        }
    }

    public static void verificarAcertos(TiposConcurso tipoConcurso, List<Integer[]> prognosticos,
            Integer qtDezenasPremio) {
        for (int i = 1; i <= getConcursoDAO().recuperarUltimoConcurso(tipoConcurso).getNuConcurso(); i++) {
            List<Integer> resultado = getConcursoDAO().recuperarConcurso(1, tipoConcurso).getSorteios().get(0)
                    .getDezenas();

            System.out.println("\nConcurso: " + i);
            verificarAcertos(prognosticos, resultado, qtDezenasPremio);
        }

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
