package org.ganimede.desdobramentos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ganimede.TiposConcurso;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;
import org.ganimede.utils.RndUtils;

public class DesdobramentoLotoFacil {
    
    public static void main(String[] args) {
        List<List<Integer>> prognosticos = gerarPrognosticos();
        
        for (int i = 1; i <= 1000; i++) {
            List<Integer> resultado = getConcursoDAO().recuperarConcurso(1, TiposConcurso.LOTO_FACIL).getSorteios().get(0)
                    .getDezenas();
            
            System.out.println("Concurso: " + i);
            System.out.println("=================================================================");
            verificar(prognosticos, resultado);
            System.out.println("=================================================================");
        }
    }
    
    public static void verificar(List<List<Integer>> prognosticos, List<Integer> resultado) {
        for (List<Integer> p : prognosticos) {
            int count = 0;
            for (Integer dezena : resultado) {
                if (p.contains(dezena)){
                    count++;
                }
            }
            System.out.println(p + "\t acertos:" + count);
            if (count >= 12){
                System.out.println("Ganhador!!!");
                System.exit(0);
            }
        }
    }
    
    public static List<List<Integer>> gerarPrognosticos() {
        List<List<Integer>> prognosticos = new ArrayList<>();
        
        List<Integer> n = Arrays.asList(RndUtils.nextInts(25, 24));

        Map<Integer, List<Integer>> grupos = new HashMap<Integer, List<Integer>>();
        for (int i = 1; i < 9; i++) {
            grupos.put(i, n.subList((i - 1) * 3, i * 3));
        }

        List<Integer> p1 = new ArrayList<Integer>();
        for (int i = 1; i < 5; i++) {
            p1.addAll(grupos.get(i));
        }

        for (int i = 5; i < 9; i++) {
            List<Integer> p2 = new ArrayList<Integer>();
            p2.addAll(p1);
            p2.addAll(grupos.get(i));
            prognosticos.add(p2);
        }

        p1 = new ArrayList<Integer>();
        for (int i = 5; i < 9; i++) {
            p1.addAll(grupos.get(i));
        }

        for (int i = 1; i < 5; i++) {
            List<Integer> p2 = new ArrayList<Integer>();
            p2.addAll(p1);
            p2.addAll(grupos.get(i));
            prognosticos.add(p2);
        }

        
        return prognosticos;
    }

    static ConcursoDAO concursoDAO = null;

    protected static ConcursoDAO getConcursoDAO() {
        if (concursoDAO == null) {
            concursoDAO = new ConcursoDAOImpl();
        }
        return concursoDAO;
    }

}
