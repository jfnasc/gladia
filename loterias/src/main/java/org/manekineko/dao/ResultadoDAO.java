package org.manekineko.dao;

import java.util.List;

import org.manekineko.Sorteio;

public interface ResultadoDAO {
	/**
	 * 
	 * @param tpSorteio
	 */
	void atualizarHashSorteios(String tpSorteio);

	/**
	 * 
	 * @param nuSorteio
	 * @param tpSorteio
	 * @return
	 */
	List<Integer> buscarDezenasSorteadas(int nuSorteio, String tpSorteio);

	/**
	 * 
	 * @param tpSorteio
	 * @return
	 */
	int buscarNroUltimoSorteioGravado(String tpSorteio);

	/**
	 * 
	 * @return
	 */
	int buscarNumeroUltimoSorteioAtraso(String tpSorteio);

	/**
	 * 
	 * @param sorteio
	 * @return
	 */
	String calcularHash(int nuSorteio, String tpSorteio);

	/**
	 * 
	 * @param dezenas
	 * @return
	 */
	String calcularHash(List<Integer> dezenas);

	/**
	 * 
	 * @param tpSorteio
	 * @param qtSorteios
	 * @param dezena
	 * @return
	 */
	boolean isDezenaEmAtraso(String tpSorteio, int qtSorteios, int dezena);

	/**
	 * 
	 * @param tpSorteio
	 * @param dezena
	 * @return
	 */
	boolean isDezenaEmAtraso(String tpSorteio, int dezena);

	/**
	 * 
	 */
	boolean existeAtrasoRegistrado(int nuSorteio, String tpSorteio);

	/**
	 * 
	 * @param hash
	 * @return
	 */
	boolean existeSorteio(String tpSorteio, String hash);

	/**
	 * 
	 * @param tpSorteio
	 * @param hash
	 * @return
	 */
	boolean existeSorteioIgual(String tpSorteio, String hash);

	/**
	 * 
	 */
	void exportarAtrasos(String tpSorteio);

	/**
	 * 
	 */
	void registrarAtrasos(int nuUltimoSorteio, String tpSorteio, int qtDezenas);

	/**
	 * 
	 * @return
	 */
	List<Integer> buscarUltimoResultado(String tpSorteio);

	/**
	 * 
	 * @param tpSorteio
	 * @param numeroSorteio
	 * @return
	 */
	Sorteio buscarSorteio(String tpSorteio, int numeroSorteio);

	/**
	 * 
	 * @param tpSorteio
	 * @param numeroSorteio
	 * @return
	 */
	List<Integer> buscarDezenasSorteadas(String tpSorteio, int numeroSorteio);

	/**
	 * 
	 * @param sorteios
	 */
	void salvarSorteios(List<Sorteio> sorteios);

	/**
	 * 
	 * @param tpSorteio
	 * @param qtSorteios
	 * @return
	 */
	List<Integer> buscarDezenasEmAtraso(String tpSorteio, int qtSorteios);

	/**
	 * 
	 * @param tpSorteio
	 * @return
	 */
	List<Integer> buscarDezenasEmAtraso(String tpSorteio);

	/**
	 * 
	 * @param tpSorteio
	 * @return
	 */
	int mediaAtraso(String tpSorteio);

	/**
	 * 
	 * @param tpSorteio
	 * @return
	 */
	void limparResultados(String tpSorteio);

	/**
	 * 
	 * @param tpSorteio
	 * @return
	 */
	List<Sorteio> listarSorteios(String tpSorteio);

	/**
	 * 
	 * @param tpSorteio
	 * @return
	 */
	List<Integer> listarAtrasos(String tpSorteio, Integer nuDezena);
}
