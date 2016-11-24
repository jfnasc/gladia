package br.com.sisfashion.modelo.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer nuUsuario;

	/**
	 * 
	 */
	private String noUsuario;

	/**
	 * 
	 */
	private String deEmail;

	/**
	 * 
	 */
	private String deTelefone;

	/**
	 * 
	 */
	private String deFuncao;

	/**
	 * 
	 */
	private String deSenha;

	/**
	 * 
	 */
	private Integer coPerfil;

	/**
	 * @return the nuUsuario
	 */
	public Integer getNuUsuario() {
		return nuUsuario;
	}

	/**
	 * @param nuUsuario
	 *            the nuUsuario to set
	 */
	public void setNuUsuario(Integer nuUsuario) {
		this.nuUsuario = nuUsuario;
	}

	/**
	 * @return the noUsuario
	 */
	public String getNoUsuario() {
		return noUsuario;
	}

	/**
	 * @param noUsuario
	 *            the noUsuario to set
	 */
	public void setNoUsuario(String noUsuario) {
		this.noUsuario = noUsuario;
	}

	/**
	 * @return the deEmail
	 */
	public String getDeEmail() {
		return deEmail;
	}

	/**
	 * @param deEmail
	 *            the deEmail to set
	 */
	public void setDeEmail(String deEmail) {
		this.deEmail = deEmail;
	}

	/**
	 * @return the deTelefone
	 */
	public String getDeTelefone() {
		return deTelefone;
	}

	/**
	 * @param deTelefone
	 *            the deTelefone to set
	 */
	public void setDeTelefone(String deTelefone) {
		this.deTelefone = deTelefone;
	}

	/**
	 * @return the deFuncao
	 */
	public String getDeFuncao() {
		return deFuncao;
	}

	/**
	 * @param deFuncao
	 *            the deFuncao to set
	 */
	public void setDeFuncao(String deFuncao) {
		this.deFuncao = deFuncao;
	}

	public String getDeSenha() {
		return deSenha;
	}

	public void setDeSenha(String deSenha) {
		this.deSenha = deSenha;
	}

	public Integer getCoPerfil() {
		return coPerfil;
	}

	public void setCoPerfil(Integer coPerfil) {
		this.coPerfil = coPerfil;
	}

}
