package br.com.sisfashion.apresentacao.usuarios;

import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.sisfashion.modelo.dto.UsuarioDTO;
import br.com.sisfashion.negocio.UsuarioServiceInterface;

@ManagedBean
public class UsuarioBean {

    @EJB
    private UsuarioServiceInterface usuarioService;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public Collection<UsuarioDTO> listarUsuarios() throws Exception{
        return usuarioService.listarUsuarios();
    }
}
