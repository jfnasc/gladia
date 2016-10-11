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

    public Collection<UsuarioDTO> listarUsuarios() throws Exception;

}
