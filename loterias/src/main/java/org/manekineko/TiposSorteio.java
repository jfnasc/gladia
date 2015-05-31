/**
 * 
 */
package org.manekineko;

/**
 * @author Administrador
 *
 */
public enum TiposSorteio {
	MEGASENA("MS", "Megasena");

	public String sigla;
	public String nome;

	private TiposSorteio(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}
}
