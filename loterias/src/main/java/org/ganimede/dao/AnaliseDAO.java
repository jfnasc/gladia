package org.ganimede.dao;

import org.ganimede.TiposConcurso;

public interface AnaliseDAO {

    abstract void executar(TiposConcurso tpConcurso, int nuSorteio);

}