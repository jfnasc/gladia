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
    function voltar() {
        document.forms['formCadastro'].method.value = "iniciar";
        document.forms['formCadastro'].submit();
    }

    function InvalidMsg(textbox) {

        if (textbox.value == '') {
            textbox.setCustomValidity('Todos os campos são obrigatórios');
        } else if (textbox.validity.typeMismatch) {
            textbox.setCustomValidity('Por favor informe um email válido');
        } else if (textbox.id == 'senha' && textbox.value.length < 6) {
            textbox.setCustomValidity('Deve possuir ao menos 6 caracteres');
        } else if (textbox.id == 'confSenha'
                && textbox.value != $('#senha').val()) {
            textbox
                    .setCustomValidity('A senha deve ser igual à senha informada anteriormente.');
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
        <div class="panel-heading">Cadastrar Usuário</div>
        <div class="panel-body">
            <form id="formCadastro" class="form-horizontal" data-toggle="validator" role="form">

                <html:hidden name="agendaForm" property="codigo"/>
                <input type="hidden" name="method" value="atualizar" />

                <div class="form-group">
                    <label class="control-label col-sm-2" for="nomeCliente">Cliente:</label>
                    <div class="col-xs-4"><bean:write name="agendaForm" property="cliente.nome"/></div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="codigoServico">Serviço:</label>
                    <div class="col-xs-4"><bean:write name="agendaForm" property="servico.nome"/></div>
                </div>
                
                <div class="form-group">
                    <label class="control-label col-sm-2" for="dtAtendimento">Data de Atendimento:</label>
                    <div class="col-xs-2"><bean:write name="agendaForm" property="dtAtendimento"/></div>
                </div>
                
                <div class="form-group">
                    <label class="control-label col-sm-2" for="hrAtendimento">Hora:</label>
                    <div class="col-xs-2"><bean:write name="agendaForm" property="hrAtendimento"/></div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="codigoUsuario">Funcionario(s):</label>
                    <div class="col-xs-4"><bean:write name="agendaForm" property="cliente.nome"/></div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-primary" onclick="voltar();">Voltar</button>
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
                minDate: "-0D", 
                maxDate: "+1Y"
            });
        } );
        
       $('#telefone').mask('(00) 0000-0000');
       $('#hrAtendimento').mask('00:00');
       
       $('#nomeCliente').attr('oninvalid','InvalidMsg(this);');
       $('#nomeCliente').attr('oninput','InvalidMsg(this);');
       $('#nomeCliente').attr('required','true');

       $('#codigoServico').attr('oninvalid','InvalidMsg(this);');
       $('#codigoServico').attr('oninput','InvalidMsg(this);');
       $('#codigoServico').attr('required','true');
               
       $('#codigoUsuario').attr('oninvalid','InvalidMsg(this);');
       $('#codigoUsuario').attr('oninput','InvalidMsg(this);');
       $('#codigoUsuario').attr('required','true');
               
    </script>
        
    
</body>
</html>