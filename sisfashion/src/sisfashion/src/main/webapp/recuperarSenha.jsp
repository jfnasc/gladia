<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
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
<title>SISFASHION</title>
<script type="text/javascript">
    function voltar(){
        document.location = "/sisfashion/login.jsp";
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

    <%
        if ("SIM".equals(request.getAttribute("STATUS_SENHA_RECUPERADA"))) {
    %>
    <div class="alert alert-success alert-dismissable fade in">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong>Ok!</strong> Senha enviada com sucesso!.
    </div>
    <%
        }
    %>

    <%
        if ("NAO".equals(request.getAttribute("STATUS_SENHA_RECUPERADA"))) {
    %>
    <div class="alert alert-danger alert-dismissable fade in">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong>Erro!</strong> Email inválido.
    </div>
    <%
        }
    %>

    <div style='width: 500px; margin: 0 auto;' class="panel panel-default">
        <div class="panel-heading">Login</div>
        <div class="panel-body">

            <p>Informe o email cadastrado no sistema para recuperação da senha</p>

            <form id="formLogin" class="form-horizontal" data-toggle="validator" role="form" method="POST" action="/sisfashion/login.do">
            
                <input type="hidden" name="method" value="recuperarSenha"/>
            
                <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-xs-6">
                        <input type="email" class="form-control" name="email" id="email"
                            placeholder="Digite o email" oninput="InvalidMsg(this);" oninvalid="InvalidMsg(this);"
                            required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">Enviar senha</button>
                        <button type="button" class="btn btn-primary" onclick="voltar();">Voltar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>