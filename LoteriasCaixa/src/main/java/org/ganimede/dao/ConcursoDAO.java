/**
 * 
 */
package org.ganimede.dao;

import java.util.Collection;

import org.ganimede.Concurso;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public interface ConcursoDAO {

    void salvarConcursos(Collection<Concurso> concursos);

    Concurso recuperarConcurso(Concurso concurso);

    Concurso recuperarUltimoConcurso(String tpConcurso);
}
