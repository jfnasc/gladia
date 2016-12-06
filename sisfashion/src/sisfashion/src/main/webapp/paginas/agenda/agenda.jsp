<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/sisfashion/css/jquery-ui.css">
<link rel="stylesheet" href="/sisfashion/css/bootstrap.min.css">
<link rel="stylesheet" href="/sisfashion/css/sisfashion.css">
<script src="/sisfashion/js/jquery.min.js"></script>
<script src="/sisfashion/js/jquery-ui.js"></script>
<script src="/sisfashion/js/jquery.mask.js"></script>
<script src="/sisfashion/js/bootstrap.min.js"></script>
<title>SISFASHION::Agenda</title>
<script type="text/javascript">
    function editar(codigoAtendimento, somenteLeitura) {
		if (somenteLeitura) {
			document.forms['formPesquisa'].method.value = "visualizar";
		} else {
			document.forms['formPesquisa'].method.value = "alterar";
		}

		document.forms['formPesquisa'].codigoAtendimento.value = codigoAtendimento;
		document.forms['formPesquisa'].submit();
	}

	function pesquisar() {
		document.forms['formPesquisa'].method.value = "pesquisar";
		document.forms['formPesquisa'].submit();
	}

	function cadastrarAtendimento() {
		document.forms['formPesquisa'].method.value = "cadastrar";
		document.forms['formPesquisa'].submit();
	}
</script>
</head>
<body>
    <%@include file="/menu.jsp" %>
    <%
    	if (request.getAttribute("MSG_SUCESSO") != null) {
    %>
    <div class="alert alert-success alert-dismissible">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong><%=request.getAttribute("MSG_SUCESSO")%></strong>
    </div>
    <%
    	}
    %>

    <div class="panel panel-default">
        <div class="panel-heading">Pesquisar Atendimentos</div>
        <div class="panel-body">
            <form id="formPesquisa" class="form-horizontal">

                <input type="hidden" name="method" />
                <input type="hidden" name="codigoAtendimento" />
                <input type="hidden" name="somenteLeitura" />

                <div class="form-group">
                    <label class="control-label col-sm-2" for="dtAtendimento">Data de Atendimento:</label>
                    <div class="col-xs-2">
                        <input type="text" id="datepicker" class="form-control" name="dtAtendimento"
                            placeholder="Clique e escolha a data">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="codigoServico">Serviço:</label>
                    <div class="col-xs-2">
                        <html:select name="agendaForm" property="servico.codigo" styleId="codigoServico" styleClass="form-control">
                            <html:option value="">Selecione</html:option> 
                            <html:optionsCollection name="agendaForm" property="listaServicos"
                                label="nome" value="codigo"/> 
                        </html:select>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-primary" onclick="pesquisar();">Pesquisar</button>
                        <button type="button" class="btn btn-primary" onclick="cadastrarAtendimento();">Cadastrar
                            Atendimento</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">Resultado da pesquisa</div>
        <div class="panel-body">

            <logic:empty name="agendaForm" property="listaAtendimentos">
                Nenhum registro encontrado.
            </logic:empty>


            <logic:notEmpty name="agendaForm" property="listaAtendimentos">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Funcionário</th>
                            <th>Cliente</th>
                            <th>Data/Hora</th>
                            <th>Serviço</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>

                        <logic:iterate id="atendimento" name="agendaForm" property="listaAtendimentos">

                            <tr>
                                <td><bean:write name="atendimento" property="codigo" /></td>
                                <td><bean:write name="atendimento" property="usuario.nome" /></td>
                                <td><bean:write name="atendimento" property="cliente.nome" /></td>
                                <td><bean:write name="atendimento" property="dtAtendimento" /></td>
                                <td><bean:write name="atendimento" property="servico.nome" /></td>
                                <td>
                                    <a href="#" onclick="editar('<bean:write name="atendimento" property="codigo" />', false);">
                                        <span class="glyphicon glyphicon-pencil"></span></a>
                                    <a href="#" onclick="editar('<bean:write name="atendimento" property="codigo" />', true);">
                                        <span class="glyphicon glyphicon-search"></span></a>
                                    <a href="#" data-href="/sisfashion/agenda.do?method=excluir&codigoAtendimento=<bean:write name="atendimento" property="codigo" />"
                                        data-toggle="modal" data-target="#confirm-delete">
                                        <span class="glyphicon glyphicon-trash"></span></a>
                                </td>
                            </tr>

                        </logic:iterate>
                    </tbody>
                </table>
            </logic:notEmpty>
        </div>
    </div>

    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">Deseja realmente excluir?</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                    <a class="btn btn-danger btn-ok">Excluir</a>
                </div>
            </div>
        </div>
    </div>

    <script>
        $('#confirm-delete').on('show.bs.modal', function(e) {
            $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
        });
    </script>

    <script type="text/javascript">
        $( function() {
            $( "#datepicker" ).datepicker({
                dateFormat: 'dd/mm/yy',
                dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
                dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
                dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
                monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
                monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
                nextText: 'Próximo',
                prevText: 'Anterior',
                changeMonth: true,
                changeYear: true,
                yearRange: "-1:+0"
            });
        } );
    </script>
</body>
</html>