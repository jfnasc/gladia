<!DOCTYPE html>
<html lang="en">
<head>
<title>Torrents :: Search Results</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="configuracao" target="principal">Minhas
						Series</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="list_new" target="principal">Apenas
							mais recentes</a></li>
<?php
if (isset($lista_engines) && count($lista_engines) > 0) {
    foreach ($lista_engines as $engine) {
        echo "<li><a href=\"find_by_search_engine/" . $engine->get_co_search_engine() . "\" target=\"principal\">" . $engine->get_no_search_engine() .
                 "</a></li>";
    }
}
?>
            </ul>
			</div>
		</nav>
	</div>
</body>
</html>