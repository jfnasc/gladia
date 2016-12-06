package br.com.sisfashion.services;

import java.util.List;

import br.com.sisfashion.dao.ClientesDAO;
import br.com.sisfashion.dto.ClienteDTO;

public class ClientesService {

	public ClienteDTO pesquisarCliente(ClienteDTO dto) throws Exception {
		return getClientesDAO().pesquisarCliente(dto);
	}

	public List<ClienteDTO> pesquisar(ClienteDTO dto) throws Exception {
		return getClientesDAO().pesquisar(dto);
	}

	public void salvar(ClienteDTO dto) throws Exception {
		getClientesDAO().salvar(dto);
	}

	public void atualizar(ClienteDTO dto) throws Exception {
		getClientesDAO().atualizar(dto);
	}

	public void excluir(ClienteDTO dto) throws Exception {
		getClientesDAO().excluir(dto);
	}

	public List<ClienteDTO> listar() throws Exception {
		return getClientesDAO().listar();
	}

	private ClientesDAO clientesDAO;

	private ClientesDAO getClientesDAO() {
		if (this.clientesDAO == null) {
			this.clientesDAO = new ClientesDAO();
		}
		return this.clientesDAO;
	}
}
