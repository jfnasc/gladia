package br.com.sisfashion.form;

import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import br.com.sisfashion.dto.ClienteDTO;

public class ClientesForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String nome;

	private String email;

	private String dtNascimento;

	private String telefone;

	private List<ClienteDTO> listaClientes;

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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the dtNascimento
	 */
	public String getDtNascimento() {
		return dtNascimento;
	}

	/**
	 * @param dtNascimento
	 *            the dtNascimento to set
	 */
	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the listaClientes
	 */
	public List<ClienteDTO> getListaClientes() {
		return listaClientes;
	}

	/**
	 * @param listaClientes
	 *            the listaClientes to set
	 */
	public void setListaClientes(List<ClienteDTO> listaClientes) {
		this.listaClientes = listaClientes;
	}

}
