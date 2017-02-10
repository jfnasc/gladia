package br.com.sisfashion.services;

import java.util.Collection;
import java.util.List;

import br.com.sisfashion.dao.FuncoesDAO;
import br.com.sisfashion.dao.PerfisDAO;
import br.com.sisfashion.dao.UsuariosDAO;
import br.com.sisfashion.dto.FuncaoDTO;
import br.com.sisfashion.dto.PerfilDTO;
import br.com.sisfashion.dto.UsuarioDTO;

public class UsuariosService {

	public UsuarioDTO pesquisarUsuario(UsuarioDTO dto) throws Exception {
		return getUsuariosDAO().pesquisarUsuario(dto);
	}

	public UsuarioDTO pesquisarUsuarioPorEmail(UsuarioDTO dto) throws Exception {
		return getUsuariosDAO().pesquisarUsuarioPorEmail(dto);
	}

	public Collection<UsuarioDTO> pesquisar(UsuarioDTO dto) throws Exception {
		return getUsuariosDAO().pesquisar(dto);
	}

	public void salvar(UsuarioDTO dto) throws Exception {
		getUsuariosDAO().salvar(dto);
	}

	public void atualizar(UsuarioDTO dto) throws Exception {
		getUsuariosDAO().atualizar(dto);
	}

	public void excluir(UsuarioDTO dto) throws Exception {
		getUsuariosDAO().excluir(dto);
	}

	public Collection<UsuarioDTO> listar() throws Exception {
		return getUsuariosDAO().listar();
	}

	public List<FuncaoDTO> listarFuncoes() throws Exception {
		return getFuncoesDAO().listar();
	}

	public List<PerfilDTO> listarPerfis() throws Exception {
		return getPerfisDAO().listar();
	}

	private UsuariosDAO usuariosDAO;

	private UsuariosDAO getUsuariosDAO() {
		if (this.usuariosDAO == null) {
			this.usuariosDAO = new UsuariosDAO();
		}
		return this.usuariosDAO;
	}

	private FuncoesDAO funcoesDAO;

	private FuncoesDAO getFuncoesDAO() {
		if (this.funcoesDAO == null) {
			this.funcoesDAO = new FuncoesDAO();
		}
		return this.funcoesDAO;
	}

	private PerfisDAO perfisDAO;

	private PerfisDAO getPerfisDAO() {
		if (this.perfisDAO == null) {
			this.perfisDAO = new PerfisDAO();
		}
		return this.perfisDAO;
	}

}
