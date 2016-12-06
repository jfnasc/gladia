package br.com.sisfashion.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import br.com.sisfashion.dto.UsuarioDTO;
import br.com.sisfashion.form.UsuariosForm;
import br.com.sisfashion.services.UsuariosService;

public class UsuariosAction extends DispatchAction {

	public ActionForward iniciar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuariosForm usuarioForm = (UsuariosForm) form;
		usuarioForm.setListaUsuarios(getService().listar());

		return mapping.findForward("iniciar");
	}

	public ActionForward cadastrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuariosForm usuarioForm = (UsuariosForm) form;
		usuarioForm.setListaFuncoes(getService().listarFuncoes());
		usuarioForm.setListaPerfis(getService().listarPerfis());
		
		return mapping.findForward("cadastrar");
	}

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuariosForm usuarioForm = (UsuariosForm) form;

		UsuarioDTO dto = new UsuarioDTO();

		dto.setCodigo(request.getParameter("codigoUsuario"));

		dto = getService().pesquisarUsuario(dto);

		usuarioForm.setCodigo(dto.getCodigo());
		usuarioForm.setNome(dto.getNome());
		usuarioForm.setEmail(dto.getEmail());
		usuarioForm.setTelefone(dto.getTelefone());
		usuarioForm.setFuncao(dto.getFuncao());
		usuarioForm.setPerfil(dto.getPerfil());

		return mapping.findForward("visualizar");
	}

	public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuarioDTO dto = new UsuarioDTO();

		dto.setCodigo(request.getParameter("codigoUsuario"));

		getService().excluir(dto);

		UsuariosForm usuarioForm = (UsuariosForm) form;
		usuarioForm.setListaUsuarios(getService().listar());

		return mapping.findForward("iniciar");
	}

	public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuariosForm usuarioForm = (UsuariosForm) form;
		usuarioForm.setListaFuncoes(getService().listarFuncoes());
		usuarioForm.setListaPerfis(getService().listarPerfis());
		
		UsuarioDTO dto = new UsuarioDTO();

		dto.setCodigo(request.getParameter("codigoUsuario"));

		dto = getService().pesquisarUsuario(dto);

		usuarioForm.setCodigo(dto.getCodigo());
		usuarioForm.setNome(dto.getNome());
		usuarioForm.setEmail(dto.getEmail());
		usuarioForm.setTelefone(dto.getTelefone());
		usuarioForm.setFuncao(dto.getFuncao());
		usuarioForm.setPerfil(dto.getPerfil());

		return mapping.findForward("alterar");
	}

	public ActionForward pesquisar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuariosForm usuarioForm = (UsuariosForm) form;

		UsuarioDTO dto = new UsuarioDTO();

		dto.setNome(usuarioForm.getNome());
		dto.setEmail(usuarioForm.getEmail());

		Collection<UsuarioDTO> listaUsuarios = getService().pesquisar(dto);
		usuarioForm.setListaUsuarios(listaUsuarios);

		return mapping.findForward("iniciar");
	}

	public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuariosForm usuarioForm = (UsuariosForm) form;

		UsuarioDTO dto = new UsuarioDTO();

		dto.setNome(usuarioForm.getNome());
		dto.setEmail(usuarioForm.getEmail());
		dto.setTelefone(usuarioForm.getTelefone());
		dto.setFuncao(usuarioForm.getFuncao());
		dto.setSenha(usuarioForm.getSenha());
		dto.setPerfil(usuarioForm.getPerfil());

		getService().salvar(dto);

		request.setAttribute("MSG_SUCESSO", "Usuário cadastrado com sucesso!");

		usuarioForm.setListaUsuarios(getService().listar());

		return mapping.findForward("iniciar");
	}

	public ActionForward atualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsuariosForm usuarioForm = (UsuariosForm) form;

		usuarioForm.setListaFuncoes(getService().listarFuncoes());
		usuarioForm.setListaUsuarios(getService().listar());
		
		UsuarioDTO dto = new UsuarioDTO();

		dto.setCodigo(usuarioForm.getCodigo());
		dto.setNome(usuarioForm.getNome());
		dto.setEmail(usuarioForm.getEmail());
		dto.setTelefone(usuarioForm.getTelefone());
		dto.setFuncao(usuarioForm.getFuncao());
		dto.setSenha(usuarioForm.getSenha());
		dto.setPerfil(usuarioForm.getPerfil());

		getService().atualizar(dto);

		request.setAttribute("MSG_SUCESSO", "Usuário alterado com sucesso!");

		return iniciar(mapping, usuarioForm, request, response);
	}

	private UsuariosService service;

	private UsuariosService getService() {
		if (this.service == null) {
			this.service = new UsuariosService();
		}
		return this.service;
	}

}
