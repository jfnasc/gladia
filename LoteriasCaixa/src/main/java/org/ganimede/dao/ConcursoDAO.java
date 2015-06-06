/**
 * 
 */
package org.ganimede.dao;

import org.ganimede.Concurso;

/**
 * @author josen
 *
 */
public interface ConcursoDAO {
    
    void salvarConcurso(Concurso concurso);
    
    Concurso obterConcurso(Concurso concurso);
    
    Concurso obterUltimoConcursoSalvo(String tpConcurso);
}
