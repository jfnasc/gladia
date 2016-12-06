/**
 * 
 */
package br.com.sisfashion.modelo.dao;

import java.util.Collection;

import javax.ejb.Local;

import br.com.sisfashion.modelo.dto.UsuarioDTO;

/**
 * @author josen
 *
 */
@Local
public interface UsuarioDAO {

	/**
	 * 
	 * @param usuario
	 * @throws Exception
	 */
	void salvarUsuario(UsuarioDTO usuarioDTO) throws Exception;

	/**
	 * 
	 * @param usuarioDTO
	 * @return
	 * @throws Exception
	 */
	Collection<UsuarioDTO> pesquisarUsuarios(UsuarioDTO usuarioDTO) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	Collection<UsuarioDTO> pesquisarUsuarios() throws Exception;
}
