/**
 * 
 */
package br.com.sisfashion.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;

import br.com.sisfashion.modelo.dto.UsuarioDTO;

/**
 * @author josen
 * 
 */
@Stateless
public class UsuarioService implements UsuarioServiceInterface {

    @Override
    public Collection<UsuarioDTO> listarUsuarios() throws Exception {
        List<UsuarioDTO> result = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            UsuarioDTO usuario = new UsuarioDTO();

            usuario.setNuUsuario(i);
            usuario.setNoUsuario("Usuario Cod. " + i);
            usuario.setDeFuncao("Funcao Usuario Cod. " + i);
            usuario.setDeTelefone("Telefone Usuario Cod. " + i);
            usuario.setDeEmail("E-mail Usuario Cod. " + i);

            result.add(usuario);
        }

        return result;
    }

}
