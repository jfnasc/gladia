package org.gladia;

import java.util.ArrayList;
import java.util.List;

public class Serie {
	private String nome;

	private List<Capitulo> capitulos = new ArrayList<Capitulo>();

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the capitulos
	 */
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

	/**
	 * @param capitulos
	 *            the capitulos to set
	 */
	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}

	/**
	 * 
	 * @param capitulo
	 */
	public void addCapitulo(Capitulo capitulo) {
		getCapitulos().add(capitulo);
	}
}
