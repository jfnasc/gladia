/**
 * 
 */
package org.ganimede.services;

import org.ganimede.Concurso;

/**
 * @author josen
 * 
 */
public interface ResultadoService {

    /**
     * 
     * @return
     */
    Concurso buscarUltimoConcurso();

    /**
     * 
     * @return
     */
    Concurso buscarConcurso(int numeroConcurso);

    /**
	 * 
	 */
    void atualizarResultados();

    /**
     * Verifica se o sorteio já está cadastrado na base
     * 
     * @param sorteio
     * @return
     */
    boolean isConcursoCadastrado(int numeroConcurso);
}
