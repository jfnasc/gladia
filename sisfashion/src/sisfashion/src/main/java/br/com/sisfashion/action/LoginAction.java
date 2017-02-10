package br.com.sisfashion.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import br.com.sisfashion.dto.UsuarioDTO;
import br.com.sisfashion.form.LoginForm;
import br.com.sisfashion.services.LoginService;

public class LoginAction extends DispatchAction {

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.getSession().invalidate();

		response.sendRedirect("/sisfashion/login.jsp");
		
		return null;
	}

	public ActionForward recuperarSenha(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginForm loginForm = (LoginForm) form;

		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setEmail(loginForm.getEmail());

		usuario = getService().recuperarUsuarioPorEmail(usuario);

		if (usuario.getCodigo() == null) {
			request.setAttribute("STATUS_SENHA_RECUPERADA", "NAO");
			return mapping.findForward("recuperarSenha");
		} else {
			request.setAttribute("STATUS_SENHA_RECUPERADA", "SIM");
			return mapping.findForward("login");
		}
	}

	private LoginService service;

	private LoginService getService() {
		if (this.service == null) {
			this.service = new LoginService();
		}
		return this.service;
	}

}
