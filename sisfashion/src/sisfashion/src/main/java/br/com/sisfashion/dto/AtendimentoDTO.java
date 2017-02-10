package br.com.sisfashion.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AtendimentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String dtAtendimento;

	private String hrAtendimento;

	private ServicoDTO servico;

	private List<UsuarioDTO> listaUsuarios;

	private ClienteDTO cliente;

	public void addUsuario(UsuarioDTO usuario) {
		getListaUsuarios().add(usuario);
	}

	/**
	 * @return the dtAtendimento
	 */
	public String getDtAtendimento() {
		return dtAtendimento;
	}

	/**
	 * @param dtAtendimento
	 *            the dtAtendimento to set
	 */
	public void setDtAtendimento(String dtAtendimento) {
		this.dtAtendimento = dtAtendimento;
	}

	/**
	 * @return the servico
	 */
	public ServicoDTO getServico() {
		return servico;
	}

	/**
	 * @param servico
	 *            the servico to set
	 */
	public void setServico(ServicoDTO servico) {
		this.servico = servico;
	}

	/**
	 * @return the usuario
	 */
	public List<UsuarioDTO> getListaUsuarios() {
		if (this.listaUsuarios == null) {
			this.listaUsuarios = new ArrayList<UsuarioDTO>();
		}
		return this.listaUsuarios;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setListaUsuario(List<UsuarioDTO> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	/**
	 * @return the cliente
	 */
	public ClienteDTO getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setListaUsuarios(List<UsuarioDTO> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public UsuarioDTO getUsuario() {
		if (!getListaUsuarios().isEmpty()) {
			return getListaUsuarios().get(0);
		}
		return null;
	}

	public String getHrAtendimento() {
		return hrAtendimento;
	}

	public void setHrAtendimento(String hrAtendimento) {
		this.hrAtendimento = hrAtendimento;
	}

}
