/**
 * 
 */
package org.ganimede.dao;

import java.util.Collection;

import org.ganimede.Concurso;
import org.ganimede.TiposConcurso;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public interface ConcursoDAO {

    void salvarConcursos(Collection<Concurso> concursos);

    Concurso recuperarConcurso(int nuConcurso, TiposConcurso tpConcurso);

    Concurso recuperarUltimoConcurso(TiposConcurso tpConcurso);
}
