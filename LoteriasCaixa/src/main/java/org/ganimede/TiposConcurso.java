/**
 * 
 */
package org.ganimede;

/**
 * @author Administrador
 *
 */
public enum TiposConcurso {
    DUPLA_SENA("DS", "Dupla Sena"),
	MEGA_SENA("MS", "Mega Sena");

	public String sigla;
	public String nome;

	private TiposConcurso(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}
}
