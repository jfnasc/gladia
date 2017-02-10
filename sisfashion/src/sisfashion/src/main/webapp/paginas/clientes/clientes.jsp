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
<title>SISFASHION::Clientes</title>
<script type="text/javascript">
    function editar(codigoCliente, somenteLeitura) {
		if (somenteLeitura) {
			document.forms['formPesquisa'].method.value = "visualizar";
		} else {
			document.forms['formPesquisa'].method.value = "alterar";
		}

		document.forms['formPesquisa'].codigoCliente.value = codigoCliente;
		document.forms['formPesquisa'].somenteLeitura.value = somenteLeitura;
		document.forms['formPesquisa'].submit();
	}

	function pesquisar() {
		document.forms['formPesquisa'].method.value = "pesquisar";
		document.forms['formPesquisa'].submit();
	}

	function cadastrarCliente() {
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
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong><%=request.getAttribute("MSG_SUCESSO")%></strong>
    </div>
    <%
    	}
    %>

    <div class="panel panel-default">
        <div class="panel-heading">Pesquisar Clientes</div>
        <div class="panel-body">
            <form id="formPesquisa" class="form-horizontal">

                <input type="hidden" name="method" />
                <input type="hidden" name="codigoCliente" />
                <input type="hidden" name="somenteLeitura" />

                <div class="form-group">
                    <label class="control-label col-sm-2" for="nome">Nome:</label>
                    <div class="col-xs-4">
                        <input type="text" class="form-control" name="nome" id="nome" placeholder="Digite o nome">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-xs-4">
                        <input type="email" class="form-control" name="email" id="email" placeholder="Digite o email">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-primary" onclick="pesquisar();">Pesquisar</button>
                        <button type="button" class="btn btn-primary" onclick="cadastrarCliente();">Cadastrar
                            Novo Cliente</button>
                    </div>
                </div>
            </form>
        </div>
    </div>


    <div class="panel panel-default">
        <div class="panel-heading">Resultado da pesquisa</div>
        <div class="panel-body">

            <logic:empty name="clientesForm" property="listaClientes">
                Nenhum registro encontrado.
            </logic:empty>


            <logic:notEmpty name="clientesForm" property="listaClientes">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Cliente</th>
                            <th>Email</th>
                            <th>Telefone/Celular</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>

                        <logic:iterate id="cliente" name="clientesForm" property="listaClientes">

                            <tr>
                                <td><bean:write name="cliente" property="codigo" /></td>
                                <td><bean:write name="cliente" property="nome" /></td>
                                <td><bean:write name="cliente" property="email" /></td>
                                <td><bean:write name="cliente" property="telefone" /></td>
                                <td>
                                    <a href="#" onclick="editar('<bean:write name="cliente" property="codigo" />', false);">
                                        <span class="glyphicon glyphicon-pencil"></span></a>
                                    <a href="#" onclick="editar('<bean:write name="cliente" property="codigo" />', true);">
                                        <span class="glyphicon glyphicon-search"></span></a>
                                    <a href="#" data-href="/sisfashion/clientes.do?method=excluir&codigoCliente=<bean:write name="cliente" property="codigo" />"
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

</body>
</html>