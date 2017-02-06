<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/hunterz/css/hunterz.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
#painel {
	position: fixed;
	left: 0;
	top: 65px;
	width: 100%;
	height: 100%;
}

#painel iframe {
	width: 100%;
	height: 100%;
	border: none;
	overflow: hidden;
}
</style>
<script type="text/javascript">
$(document).ready(function () {
	$(".nav a").on("click", function(){
	  $(".nav").find(".active").removeClass("active");
	  $(this).parent().addClass("active");
	});
});
</script>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs">
			<li class="active"><a href="resumo" target="painel">Configuração Atual</a></li>
			<li><a href="config_series" target="painel">Configuração de Series</a></li>
			<li><a href="config_engines" target="painel">Engines de Busca</a></li>
		</ul>
		<div id="painel">
			<iframe src="/hunterz/pages/resumo" name="painel"></iframe>
			<iframe src="/hunterz/footer.php"></iframe>
		</div>
	</div>
</body>
</html>