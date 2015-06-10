package org.ganimede;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.ResultadoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;
import org.ganimede.dao.impl.ResultadoDAOImpl;
import org.ganimede.regras.Regra;
import org.ganimede.utils.RndUtils;
import org.ganimede.utils.StringUtils;

public class GerarCombinacoesMS {

    static ConcursoDAO concursoDAO = null;

    static ResultadoDAO resultadoDAO = null;

    static List<Regra> regras = new ArrayList<Regra>();

    static {
        regras.add(new org.ganimede.regras.megasena.RegraNaoSequencial());
        regras.add(new org.ganimede.regras.megasena.RegraSorteiosAnteriores());
        regras.add(new org.ganimede.regras.megasena.RegraParesImpares());
        regras.add(new org.ganimede.regras.megasena.RegraNaoVertical());
        regras.add(new org.ganimede.regras.megasena.RegraAtraso(8));
    }

    public static void fechamento(List<String> apostas, Integer[] p, int pos, int size) {
        StringBuilder sb = new StringBuilder();

        List<Integer> base = Arrays.asList(p).subList(pos, pos + size);

        for (Integer dezena : base) {
            sb.append(dezena + ",");
        }

        for (Integer dezena : p) {
            if (!base.contains(dezena)) {
                apostas.add(sb.toString() + dezena);
            }
        }
    }

    public static void main(String[] args) {
        List<String> apostas = new ArrayList<>();

        List<Integer[]> prognosticos = prognosticos(1, 15);

        for (Integer[] p : prognosticos) {
            System.out.println("-- BASE");
            StringUtils.print(p);
            System.out.println("--------------------");

            for (int i = 0; i < p.length / 5; i++) {
                fechamento(apostas, p, i * 5, 5);
            }

            for (String aposta : apostas) {
                System.out.println(aposta);
            }
            System.out.println("--------------------");
            System.out.println("Nro. apostas: " + apostas.size());
            System.out.println("Vl.  apostas: " + Float.valueOf(apostas.size()) * 3.50);
        }
    }

    /**
     * 
     * @param args
     */
    public static void main2(String[] args) {
        List<Integer[]> prognosticos = prognosticos(3, 6);

        System.out.println("--------------------------");
        System.out.println("Boa Sorte!");
        System.out.println("--------------------------");
        for (Integer[] aposta : prognosticos) {
            StringUtils.print(aposta);
        }

        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.MEGA_SENA.sigla);

        System.out.println("------------------------------------------------");
        System.out.println("Ultimas sorteadas");
        System.out.println("------------------------------------------------");
        for (Sorteio sorteio : ultimoConcurso.getSorteios()) {
            System.out.println(sorteio.getDezenas());
        }

        System.out.println("------------------------------------------------");
        System.out.println("Atrasos (entre 8 e 9 sorteios de atraso)");
        System.out.println("------------------------------------------------");
        List<Integer> atrasos = getResultadoDAO().buscarDezenasEmAtraso(TiposConcurso.MEGA_SENA.sigla, 1, 8);
        Collections.sort(atrasos);
        System.out.println(atrasos);
    }

    /**
     * 
     * @param quantidade
     * @return
     */
    public static List<Integer[]> prognosticos(Integer quantidade, Integer qtDezenas) {
        List<Integer[]> result = new ArrayList<Integer[]>();

        while (result.size() < quantidade) {

            for (int i = 0; i < quantidade; i++) {
                result.add(gerarAposta(qtDezenas));
            }

            for (Regra regra : regras) {
                regra.aplicar(result);
            }
        }

        return result.subList(0, quantidade);
    }

    /**
     * 
     * @return
     */
    public static Integer[] gerarAposta(int qtDezenas) {
        List<Integer> tmp = new ArrayList<Integer>();

        while (tmp.size() < qtDezenas) {
            Integer p1 = RndUtils.nextInt(1, 60);
            if (!tmp.contains(p1)) {
                tmp.add(p1);
            }
        }

        Integer[] result = new Integer[qtDezenas];
        tmp.subList(0, qtDezenas).toArray(result);

        // Integer[] result = new Integer[] { 15, 14, 13, 12, 11, 10, 9, 8, 7,
        // 6, 5, 4, 3, 2, 1 };
        
        return result;
    }

    private static ConcursoDAO getConcursoDAO() {
        if (concursoDAO == null) {
            concursoDAO = new ConcursoDAOImpl();
        }
        return concursoDAO;
    }

    private static ResultadoDAO getResultadoDAO() {
        if (resultadoDAO == null) {
            resultadoDAO = new ResultadoDAOImpl();
        }
        return resultadoDAO;
    }
}
