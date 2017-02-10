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
    function voltar(){
        document.forms['formCadastro'].method.value = "iniciar";
        document.forms['formCadastro'].submit();
    }

	function InvalidMsg(textbox) {

		if (textbox.value == '') {
			textbox.setCustomValidity('Todos os campos são obrigatórios');
		} else if (textbox.validity.typeMismatch) {
			textbox.setCustomValidity('Por favor informe um email válido');
		} else {
			textbox.setCustomValidity('');
		}

		return true;
	}
</script>
</head>
<body>
    <%@include file="/menu.jsp" %>
    <div class="panel panel-default">
        <div class="panel-heading">Alterar Cliente</div>
        <div class="panel-body">
            <form id="formCadastro" class="form-horizontal" data-toggle="validator" role="form">

                <input type="hidden" name="method" value="atualizar" />
                <html:hidden name="clientesForm" property="codigo" />

                <div class="form-group">
                    <label class="control-label col-sm-2" for="nome">Nome:</label>
                    <div class="col-xs-4">
                        <input type="Nome" class="form-control" name="nome" id="nome" placeholder="Digite o nome"
                            value="<bean:write name="clientesForm" property="nome"/>" oninput="InvalidMsg(this);"
                            oninvalid="InvalidMsg(this);" required>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-xs-4">
                        <input type="email" class="form-control" name="email" id="email" placeholder="Digite o email"
                            value="<bean:write name="clientesForm" property="email"/>" oninput="InvalidMsg(this);"
                            oninvalid="InvalidMsg(this);" required>

                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="dtNascimento">Data de Nascimento:</label>
                    <div class="col-xs-2">
                        <input type="text" id="datepicker" class="form-control" name="dtNascimento"
                            placeholder="Clique e escolha a data" oninput="InvalidMsg(this);" oninvalid="InvalidMsg(this);"
                            value="<bean:write name="clientesForm" property="dtNascimento"/>"
                            required>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="telefone">Telefone/Celular:</label>
                    <div class="col-xs-4">
                        <input type="text" class="form-control" name="telefone" id="telefone"
                            placeholder="Digite o telefone"
                            value="<bean:write name="clientesForm" property="telefone"/>" oninput="InvalidMsg(this);"
                            oninvalid="InvalidMsg(this);" required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-primary" onclick="voltar();">Voltar</button>
                        <button type="submit" class="btn btn-primary">Alterar</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
    
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
                yearRange: "-100:+0"
            });
        } );
        
       $('#telefone').mask('(00) 0000-0000');        
    </script>
    
    
</body>
</html>