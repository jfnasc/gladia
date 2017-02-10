package br.com.sisfashion.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import br.com.sisfashion.dto.AtendimentoDTO;
import br.com.sisfashion.form.AgendaForm;
import br.com.sisfashion.services.AtendimentoService;

public class AgendaAction extends DispatchAction {

	public ActionForward iniciar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;

		agendaForm.setListaServicos(getService().listarServicos());
		agendaForm.setListaAtendimentos(getService().listar());
		
		return mapping.findForward("iniciar");
	}

	public ActionForward cadastrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;
		
		agendaForm.setListaClientes(getService().listarClientes());
		agendaForm.setListaServicos(getService().listarServicos());
		agendaForm.setListaUsuarios(getService().listarUsuarios());
		
		return mapping.findForward("cadastrar");
	}

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;

		agendaForm.setListaClientes(getService().listarClientes());
		agendaForm.setListaServicos(getService().listarServicos());
		agendaForm.setListaUsuarios(getService().listarUsuarios());

		AtendimentoDTO dto = new AtendimentoDTO();

		dto.setCodigo(request.getParameter("codigoAtendimento"));

		dto = getService().pesquisarAtendimento(dto);

		agendaForm.setServico(dto.getServico());
		agendaForm.setCliente(dto.getCliente());
		agendaForm.setUsuario(dto.getUsuario());
		agendaForm.setCodigo(dto.getCodigo());
		agendaForm.setDtAtendimento(dto.getDtAtendimento());
		agendaForm.setHrAtendimento(dto.getHrAtendimento());

		return mapping.findForward("visualizar");
	}

	public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;

		AtendimentoDTO dto = new AtendimentoDTO();

		dto.setCodigo(request.getParameter("codigoAtendimento"));

		getService().excluir(dto);

		agendaForm.setListaServicos(getService().listarServicos());
		agendaForm.setListaAtendimentos(getService().listar());

		return mapping.findForward("iniciar");
	}

	public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;

		agendaForm.setListaClientes(getService().listarClientes());
		agendaForm.setListaServicos(getService().listarServicos());
		agendaForm.setListaUsuarios(getService().listarUsuarios());

		AtendimentoDTO dto = new AtendimentoDTO();

		dto.setCodigo(request.getParameter("codigoAtendimento"));

		dto = getService().pesquisarAtendimento(dto);

		agendaForm.setServico(dto.getServico());
		agendaForm.setCliente(dto.getCliente());
		agendaForm.setUsuario(dto.getUsuario());
		agendaForm.setCodigo(dto.getCodigo());
		agendaForm.setDtAtendimento(dto.getDtAtendimento());
		agendaForm.setHrAtendimento(dto.getHrAtendimento());

		return mapping.findForward("alterar");
	}

	public ActionForward pesquisar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;
		
		agendaForm.setListaServicos(getService().listarServicos());
		
		AtendimentoDTO dto = new AtendimentoDTO();

		dto.setDtAtendimento(agendaForm.getDtAtendimento());
		dto.setServico(agendaForm.getServico());

		agendaForm.setListaAtendimentos(getService().pesquisar(dto));

		return mapping.findForward("iniciar");
	}

	public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;

		AtendimentoDTO dto = new AtendimentoDTO();

		dto.setDtAtendimento(agendaForm.getDtAtendimento());
		dto.setHrAtendimento(agendaForm.getHrAtendimento());
		dto.setServico(agendaForm.getServico());
		dto.setCliente(agendaForm.getCliente());
		dto.getListaUsuarios().add(agendaForm.getUsuario());

		getService().salvar(dto);

		request.setAttribute("MSG_SUCESSO", "Dados cadastrados com sucesso!");

		agendaForm.setListaServicos(getService().listarServicos());
		agendaForm.setListaAtendimentos(getService().listar());

		agendaForm.setServico(null);
		
		return mapping.findForward("iniciar");
	}

	public ActionForward atualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AgendaForm agendaForm = (AgendaForm) form;

		AtendimentoDTO dto = new AtendimentoDTO();

		dto.setDtAtendimento(agendaForm.getDtAtendimento());
		dto.setHrAtendimento(agendaForm.getHrAtendimento());
		dto.setServico(agendaForm.getServico());
		dto.setCliente(agendaForm.getCliente());
		dto.getListaUsuarios().add(agendaForm.getUsuario());

		getService().atualizar(dto);

		request.setAttribute("MSG_SUCESSO", "Dados alterados com sucesso!");

		agendaForm.setListaServicos(getService().listarServicos());
		agendaForm.setListaAtendimentos(getService().listar());
		
		agendaForm.setServico(null);
		
		return mapping.findForward("iniciar");
	}

	private AtendimentoService service;

	private AtendimentoService getService() {
		if (this.service == null) {
			this.service = new AtendimentoService();
		}
		return this.service;
	}

}
