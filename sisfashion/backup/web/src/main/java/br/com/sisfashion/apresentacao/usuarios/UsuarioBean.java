package br.com.sisfashion.apresentacao.usuarios;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import br.com.sisfashion.modelo.dto.UsuarioDTO;
import br.com.sisfashion.negocio.UsuarioServiceInterface;

@ManagedBean
public class UsuarioBean {

	@EJB
	private UsuarioServiceInterface usuarioService;

	private UsuarioDTO usuarioDTO;

	private UsuarioDTO usuarioPesquisaDTO;

	private Collection<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Collection<UsuarioDTO> listarUsuarios() throws Exception {
		return listaUsuarios;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pesquisarUsuarios() throws Exception {
		try {
			this.listaUsuarios.addAll(usuarioService.pesquisarUsuarios(this.usuarioPesquisaDTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pages/usuarios/usuarios.jsf";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public void pesquisarUsuario(ActionEvent evt) throws Exception {
		System.out.println("dsadasdasdasdasdasd");
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void cadastrarUsuario() throws Exception {
		usuarioService.salvarUsuario(this.usuarioDTO);
	}

	public UsuarioDTO getUsuario() {
		if (this.usuarioDTO == null) {
			this.usuarioDTO = new UsuarioDTO();
		}
		return this.usuarioDTO;
	}

	public UsuarioDTO getUsuarioPesquisa() {
		if (this.usuarioPesquisaDTO == null) {
			this.usuarioPesquisaDTO = new UsuarioDTO();
		}
		return this.usuarioPesquisaDTO;
	}

	public String carregarPagina() {
		try {
			this.listaUsuarios.addAll(usuarioService.pesquisarUsuarios());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pages/usuarios/usuarios.jsf";
	}
}
