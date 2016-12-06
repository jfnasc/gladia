package br.com.sisfashion.apresentacao;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "viewController", eager = true)
@RequestScoped
public class ViewController implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String irParaCadastroUsuarios() {
        return "#{request.contextPath}/pages/usuarios/usuarios.jsf";
    }

}
