package org.ganimede.dao;

import java.util.List;

public interface ResultadoDAO {

    boolean isDezenaEmAtrasoMinimo(String tpConcurso, int nuSorteio, int nuDezena, int qtConcursos);

    boolean existeSorteioIgual(String tpConcurso, String hash);

    List<Integer> buscarDezenasEmAtraso(String tpConcurso, int nuSorteio, int qtConcursos);
    
    boolean isDezenaFrequente(String tpConcurso, int nuDezena, int limite);
}
