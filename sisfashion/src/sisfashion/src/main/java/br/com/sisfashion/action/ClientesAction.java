package br.com.sisfashion.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import br.com.sisfashion.dto.ClienteDTO;
import br.com.sisfashion.form.ClientesForm;
import br.com.sisfashion.services.ClientesService;
import br.com.sisfashion.utils.DateUtils;

public class ClientesAction extends DispatchAction {

	public ActionForward iniciar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientesForm clientesForm = (ClientesForm) form;

		clientesForm.setListaClientes(getService().listar());

		return mapping.findForward("iniciar");
	}

	public ActionForward cadastrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("cadastrar");
	}

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientesForm clientesForm = (ClientesForm) form;

		ClienteDTO dto = new ClienteDTO();

		dto.setCodigo(request.getParameter("codigoCliente"));

		dto = getService().pesquisarCliente(dto);

		clientesForm.setCodigo(dto.getCodigo());
		clientesForm.setNome(dto.getNome());
		clientesForm.setEmail(dto.getEmail());
		clientesForm.setTelefone(dto.getTelefone());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		clientesForm.setDtNascimento(sdf.format(dto.getDtNascimento()));

		return mapping.findForward("visualizar");
	}

	public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientesForm clientesForm = (ClientesForm) form;

		ClienteDTO dto = new ClienteDTO(request.getParameter("codigoCliente"));
		getService().excluir(dto);

		clientesForm.setListaClientes(getService().listar());

		return mapping.findForward("iniciar");
	}

	public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientesForm clientesForm = (ClientesForm) form;

		ClienteDTO dto = new ClienteDTO(request.getParameter("codigoCliente"));

		dto = getService().pesquisarCliente(dto);

		clientesForm.setCodigo(dto.getCodigo());
		clientesForm.setNome(dto.getNome());
		clientesForm.setEmail(dto.getEmail());
		clientesForm.setTelefone(dto.getTelefone());
		clientesForm.setDtNascimento(DateUtils.format(dto.getDtNascimento()));

		return mapping.findForward("alterar");
	}

	public ActionForward pesquisar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientesForm clientesForm = (ClientesForm) form;

		ClienteDTO dto = new ClienteDTO();

		dto.setNome(clientesForm.getNome());
		dto.setEmail(clientesForm.getEmail());

		List<ClienteDTO> listaClientes = getService().pesquisar(dto);
		clientesForm.setListaClientes(listaClientes);

		return mapping.findForward("iniciar");
	}

	public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientesForm clientesForm = (ClientesForm) form;

		ClienteDTO dto = new ClienteDTO();

		dto.setNome(clientesForm.getNome());
		dto.setEmail(clientesForm.getEmail());
		dto.setTelefone(clientesForm.getTelefone());
		dto.setDtNascimento(DateUtils.parse(clientesForm.getDtNascimento()));

		getService().salvar(dto);

		request.setAttribute("MSG_SUCESSO", "Dados cadastrados com sucesso!");

		clientesForm.setListaClientes(getService().listar());

		return mapping.findForward("iniciar");
	}

	public ActionForward atualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientesForm clientesForm = (ClientesForm) form;

		ClienteDTO dto = new ClienteDTO();

		dto.setCodigo(clientesForm.getCodigo());
		dto.setNome(clientesForm.getNome());
		dto.setEmail(clientesForm.getEmail());
		dto.setTelefone(clientesForm.getTelefone());
		dto.setDtNascimento(DateUtils.parse(clientesForm.getDtNascimento()));

		getService().atualizar(dto);

		request.setAttribute("MSG_SUCESSO", "Dados alterados com sucesso!");

		clientesForm.setListaClientes(getService().listar());

		return mapping.findForward("iniciar");
	}

	private ClientesService service;

	private ClientesService getService() {
		if (this.service == null) {
			this.service = new ClientesService();
		}
		return this.service;
	}

}
