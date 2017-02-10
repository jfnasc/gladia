package br.com.sisfashion.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import br.com.sisfashion.dto.AtendimentoDTO;
import br.com.sisfashion.dto.ClienteDTO;
import br.com.sisfashion.dto.ServicoDTO;
import br.com.sisfashion.dto.UsuarioDTO;

public class AgendaForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String dtAtendimento;

	private String hrAtendimento;

	private ServicoDTO servico;

	private UsuarioDTO usuario;

	private ClienteDTO cliente;

	private List<AtendimentoDTO> listaAtendimentos;

	private List<ServicoDTO> listaServicos;

	private List<ClienteDTO> listaClientes;

	private List<UsuarioDTO> listaUsuarios;

	/**
	 * @return the listaAtendimentos
	 */
	public List<AtendimentoDTO> getListaAtendimentos() {
		return listaAtendimentos;
	}

	/**
	 * @param listaAtendimentos
	 *            the listaAtendimentos to set
	 */
	public void setListaAtendimentos(List<AtendimentoDTO> listaAtendimentos) {
		this.listaAtendimentos = listaAtendimentos;
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
		if (servico == null) {
			this.servico = new ServicoDTO();
		}
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
	 * @return the cliente
	 */
	public ClienteDTO getCliente() {
		if (cliente == null) {
			this.cliente = new ClienteDTO();
		}
		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the listaServicos
	 */
	public List<ServicoDTO> getListaServicos() {
		return listaServicos;
	}

	/**
	 * @param listaServicos
	 *            the listaServicos to set
	 */
	public void setListaServicos(List<ServicoDTO> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public UsuarioDTO getUsuario() {
		if (usuario == null) {
			usuario = new UsuarioDTO();
		}
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public List<ClienteDTO> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<ClienteDTO> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<UsuarioDTO> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UsuarioDTO> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getHrAtendimento() {
		return hrAtendimento;
	}

	public void setHrAtendimento(String hrAtendimento) {
		this.hrAtendimento = hrAtendimento;
	}

}
