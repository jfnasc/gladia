/**
 * 
 */
package br.com.sisfashion.apresentacao.login;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author josen
 * 
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    /**
     * Realiza o logout do usuario invalidando sua sessão.
     * 
     * @return
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.jsf?faces-redirect=true";
    }

    /**
     * Recupera a senha do usuario a partir do email informado
     * 
     * @return
     */
    public String recuperarSenha() {
        return "/recuperarSenha.jsf";
    }
}
