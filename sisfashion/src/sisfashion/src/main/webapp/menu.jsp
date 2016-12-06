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
</head>
<body>
<nav class="navbar navbar-custom">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/sisfashion/">SISFASHION</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/sisfashion/paginas/bemvindo.jsp">Home</a></li>
            <% if (request.isUserInRole("administradores")) { %>
            <li><a href="/sisfashion/usuarios.do?method=iniciar">Usuários</a></li>
            <% } %>
            <li><a href="/sisfashion/agenda.do?method=iniciar">Agenda</a></li>
            <li><a href="/sisfashion/clientes.do?method=iniciar">Clientes</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/sisfashion/logout.jsp">Sair</a></li>
        </ul>
    </div>
</nav>
<script type="text/javascript">
    $(".nav a").on("click", function(){
       $(".nav").find(".active").removeClass("active");
       $(this).parent().addClass("active");
    });
</script>
</body>
</html>