package br.com.sisfashion.form;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

import br.com.sisfashion.dto.FuncaoDTO;
import br.com.sisfashion.dto.PerfilDTO;
import br.com.sisfashion.dto.UsuarioDTO;

public class UsuariosForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;

	private String email;

	private String telefone;

	private String senha;

	private String codigo;

	private Collection<UsuarioDTO> listaUsuarios;

	private FuncaoDTO funcao;

	private Collection<FuncaoDTO> listaFuncoes;

	private PerfilDTO perfil;

	private Collection<PerfilDTO> listaPerfis;

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

	public Collection<UsuarioDTO> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(Collection<UsuarioDTO> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
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
		if (perfil == null) {
			perfil = new PerfilDTO();
		}
		return perfil;
	}

	/**
	 * @param perfil
	 *            the perfil to set
	 */
	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}

	/**
	 * @return the listaPerfis
	 */
	public Collection<PerfilDTO> getListaPerfis() {
		return listaPerfis;
	}

	/**
	 * @param listaPerfis
	 *            the listaPerfis to set
	 */
	public void setListaPerfis(Collection<PerfilDTO> listaPerfis) {
		this.listaPerfis = listaPerfis;
	}

	/**
	 * @return the funcao
	 */
	public FuncaoDTO getFuncao() {
		if (funcao == null) {
			funcao = new FuncaoDTO();
		}
		return funcao;
	}

	/**
	 * @param funcao
	 *            the funcao to set
	 */
	public void setFuncao(FuncaoDTO funcao) {
		this.funcao = funcao;
	}

	/**
	 * @return the listaFuncoes
	 */
	public Collection<FuncaoDTO> getListaFuncoes() {
		return listaFuncoes;
	}

	/**
	 * @param listaFuncoes
	 *            the listaFuncoes to set
	 */
	public void setListaFuncoes(Collection<FuncaoDTO> listaFuncoes) {
		this.listaFuncoes = listaFuncoes;
	}

}
