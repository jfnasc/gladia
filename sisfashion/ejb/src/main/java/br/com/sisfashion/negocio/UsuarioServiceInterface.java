/**
 * 
 */
package br.com.sisfashion.negocio;

import java.util.Collection;

import javax.ejb.Local;

import br.com.sisfashion.modelo.dto.UsuarioDTO;

/**
 * @author josen
 * 
 */
@Local
public interface UsuarioServiceInterface {

	public void salvarUsuario(UsuarioDTO usuarioDTO) throws Exception;

	public Collection<UsuarioDTO> pesquisarUsuarios(UsuarioDTO usuarioDTO) throws Exception;
	
	public Collection<UsuarioDTO> pesquisarUsuarios() throws Exception;

}
