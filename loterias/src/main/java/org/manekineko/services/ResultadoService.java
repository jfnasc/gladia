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
	Sorteio buscarSorteio(int numeroSorteio);

	/**
	 * 
	 */
	void atualizarResultados();

	/**
	 * 
	 * @return
	 */
	int buscarNroUltimoSorteio();

	/**
	 * Verifica se o sorteio já está cadastrado na base
	 * 
	 * @param sorteio
	 * @return
	 */
	boolean isSorteioCadastrado(int sorteio);
}
