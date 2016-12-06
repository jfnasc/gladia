package br.com.sisfashion.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String nome;

	private String email;

	private FuncaoDTO funcao;

	private String telefone;

	private String senha;

	private PerfilDTO perfil;

	public UsuarioDTO() {

	}

	public UsuarioDTO(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the funcao
	 */
	public FuncaoDTO getFuncao() {
		return funcao;
	}

	/**
	 * @param funcao
	 *            the funcao to set
	 */
	public void setFuncao(FuncaoDTO funcao) {
		this.funcao = funcao;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the perfil
	 */
	public PerfilDTO getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil
	 *            the perfil to set
	 */
	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}

}
