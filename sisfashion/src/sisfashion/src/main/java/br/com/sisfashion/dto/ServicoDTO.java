package br.com.sisfashion.dto;

import java.io.Serializable;

public class ServicoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String nome;

	public ServicoDTO() {

	}

	public ServicoDTO(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

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

}
