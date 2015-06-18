package org.ganimede.dao;

import java.util.List;

import org.ganimede.Sorteio;

public interface ResultadoDAO {

    int concursosEmAtraso(String tpConcurso, int nuSorteio, int nuDezena);

    boolean existeSorteioIgual(String tpConcurso, String hash);

    List<Integer> buscarDezenasEmAtraso(String tpConcurso, int nuSorteio, int qtConcursos);

    boolean isDezenaFrequente(String tpConcurso, int nuDezena, int limite);

    List<Sorteio> buscarSorteios(String tpConcurso, int nuSorteio);
}
