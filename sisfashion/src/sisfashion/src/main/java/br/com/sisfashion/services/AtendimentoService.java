package br.com.sisfashion.services;

import java.util.List;

import br.com.sisfashion.dao.AtendimentoDAO;
import br.com.sisfashion.dao.ClientesDAO;
import br.com.sisfashion.dao.ServicosDAO;
import br.com.sisfashion.dao.UsuariosDAO;
import br.com.sisfashion.dto.AtendimentoDTO;
import br.com.sisfashion.dto.ClienteDTO;
import br.com.sisfashion.dto.ServicoDTO;
import br.com.sisfashion.dto.UsuarioDTO;

public class AtendimentoService {

	public AtendimentoDTO pesquisarAtendimento(AtendimentoDTO dto) throws Exception {
		return getAtendimentoDAO().pesquisarAtendimento(dto);
	}

	public List<AtendimentoDTO> pesquisar(AtendimentoDTO dto) throws Exception {
		return getAtendimentoDAO().pesquisar(dto);
	}

	public void salvar(AtendimentoDTO dto) throws Exception {
		getAtendimentoDAO().salvar(dto);
	}

	public void atualizar(AtendimentoDTO dto) throws Exception {
		getAtendimentoDAO().atualizar(dto);
	}

	public void excluir(AtendimentoDTO dto) throws Exception {
		getAtendimentoDAO().excluir(dto);
	}

	public List<AtendimentoDTO> listar() throws Exception {
		return getAtendimentoDAO().listar();
	}

	public List<ServicoDTO> listarServicos() throws Exception {
		return getServicosDAO().listar();
	}

	public List<UsuarioDTO> listarUsuarios() throws Exception {
		return getUsuariosDAO().listar();
	}

	public List<ClienteDTO> listarClientes() throws Exception {
		return getClientesDAO().listar();
	}

	private AtendimentoDAO atendimentoDAO;

	private AtendimentoDAO getAtendimentoDAO() {
		if (this.atendimentoDAO == null) {
			this.atendimentoDAO = new AtendimentoDAO();
		}
		return this.atendimentoDAO;
	}

	private ServicosDAO servicosDAO;

	private ServicosDAO getServicosDAO() {
		if (this.servicosDAO == null) {
			this.servicosDAO = new ServicosDAO();
		}
		return this.servicosDAO;
	}

	private ClientesDAO clientesDAO;

	private ClientesDAO getClientesDAO() {
		if (this.clientesDAO == null) {
			this.clientesDAO = new ClientesDAO();
		}
		return this.clientesDAO;
	}

	private UsuariosDAO usuariosDAO;

	private UsuariosDAO getUsuariosDAO() {
		if (this.usuariosDAO == null) {
			this.usuariosDAO = new UsuariosDAO();
		}
		return this.usuariosDAO;
	}

}
