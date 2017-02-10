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
</head>
<body>
    <%@include file="/menu.jsp" %>
    <div class="panel panel-default">
        <div class="panel-heading">Cadastro de Usuário</div>
        <div class="panel-body">
            <form id="formCadastro" class="form-horizontal" data-toggle="validator" role="form">

                <input type="hidden" name="method" value="iniciar" />
                <html:hidden name="clientesForm" property="codigo" />

                <div class="form-group">
                    <label class="control-label col-sm-2" for="nome">Nome:</label>
                    <div class="col-xs-4"><bean:write name="clientesForm" property="nome"/></div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-xs-4"><bean:write name="clientesForm" property="email"/></div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="dtNascimento">Data de Nascimento:</label>
                    <div class="col-xs-2"><bean:write name="clientesForm" property="dtNascimento"/></div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="telefone">Telefone/Celular:</label>
                    <div class="col-xs-4"><bean:write name="clientesForm" property="telefone"/></div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">Voltar</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</body>
</html>