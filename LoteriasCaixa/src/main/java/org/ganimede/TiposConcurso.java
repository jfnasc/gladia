/**
 * 
 */
package org.ganimede;

/**
 * @author Administrador
 *
 */
public enum TiposConcurso {
	MEGASENA("MS", "Megasena");

	public String sigla;
	public String nome;

	private TiposConcurso(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}
}
