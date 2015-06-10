package org.ganimede.dao;

import org.ganimede.Concurso;

public interface AtrasosDAO {

    Concurso recuperarUltimoConcurso(String tpConcurso, int numeroSorteio);

    void registrarAtrasos(String tpConcurso, int numeroSorteio, int qtDezenas);

    void calcularAtrasos(String tpConcurso, int numeroSorteio);

}
