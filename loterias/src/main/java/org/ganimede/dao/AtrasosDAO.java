package org.ganimede.dao;

import org.ganimede.Concurso;
import org.ganimede.TiposConcurso;

public interface AtrasosDAO {

    Concurso recuperarUltimoConcurso(String tpConcurso, int numeroSorteio);

    void registrarAtrasos(TiposConcurso tpConcurso, int numeroSorteio);

    void calcularAtrasos(String tpConcurso, int numeroSorteio);

}
