/**
 * 
 */
package br.com.sisfashion.negocio;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.sisfashion.modelo.dao.UsuarioDAO;
import br.com.sisfashion.modelo.dto.UsuarioDTO;

/**
 * @author josen
 * 
 */
@Stateless
public class UsuarioService implements UsuarioServiceInterface {

	@EJB
	UsuarioDAO usuarioDAO;

	@Override
	public void salvarUsuario(UsuarioDTO usuarioDTO) throws Exception {
		usuarioDAO.salvarUsuario(usuarioDTO);
	}

	@Override
	public Collection<UsuarioDTO> pesquisarUsuarios(UsuarioDTO usuarioDTO) throws Exception {
		return usuarioDAO.pesquisarUsuarios(usuarioDTO);
	}

	@Override
	public Collection<UsuarioDTO> pesquisarUsuarios() throws Exception {
		return usuarioDAO.pesquisarUsuarios();
	}

}
