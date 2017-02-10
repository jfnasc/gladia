package br.com.sisfashion.services;

import br.com.sisfashion.dto.UsuarioDTO;

public class LoginService {

	public UsuarioDTO recuperarUsuarioPorEmail(UsuarioDTO usuario) throws Exception{
		return getUsuariosService().pesquisarUsuarioPorEmail(usuario);
	}

	private UsuariosService usuariosService;

	private UsuariosService getUsuariosService() {
		if (this.usuariosService == null) {
			this.usuariosService = new UsuariosService();
		}
		return this.usuariosService;
	}

}
