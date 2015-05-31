/**
 * 
 */
package org.manekineko.services;

import org.manekineko.Sorteio;

/**
 * @author josen
 *
 */
public interface ResultadoService {
	
	/**
	 * 
	 * @return
	 */
	Sorteio buscarResultado();

	/**
	 * 
	 * @return
	 */
	Sorteio buscarResultado(int numeroSorteio);

	/**
	 * 
	 */
	void atualizarResultados();
	
	/**
	 * 
	 * @return
	 */
	int buscarNroUltimoSorteioCEF();
}
