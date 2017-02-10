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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/hunterz/css/hunterz.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">

<?php
echo "<div class=\"panel panel-default\">";
echo "	<div class=\"panel-heading\">Engines Ativos</div>";
echo "	<div class=\"panel-body\">";
echo "    <table class=\"table table-condensed table-hover\">";
echo "      <tr>";
echo "        <th>Código</th>";
echo "        <th>Nome</th>";
echo "        <th>Url de Busca</th>";
echo "        <th>Ativo</th>";
echo "      </tr>";

if (isset($lista_engines_ativos) && count($lista_engines_ativos) > 0) {
    
    foreach ($lista_engines_ativos as $engine) {
        echo "      <tr>";
        echo "        <td>" . $engine->get_co_search_engine() . "</td>";
        echo "        <td>" . $engine->get_no_search_engine() . "</td>";
        echo "        <td>" . $engine->get_de_url() . "</td>";
        echo "        <td>" . $engine->get_sg_ativo() . "</td>";
        echo "      </tr>";
    }
}

echo "	  </table>";
echo "	</div>";
echo "</div>";
?>		
		
<?php
echo "<div class=\"panel panel-default\">";
echo "	<div class=\"panel-heading\">Séries Ativas</div>";
echo "	<div class=\"panel-body\">";
echo "    <table class=\"table table-condensed table-hover\">";
echo "      <tr>";
echo "        <th>ID</th>";
echo "        <th>Nome</th>";
echo "        <th>Código de Busca</th>";
echo "        <th>Ativo</th>";
echo "      </tr>";

if (isset($lista_series_ativas) && count($lista_series_ativas) > 0) {
    
    foreach ($lista_series_ativas as $serie) {
        echo "      <tr>";
        echo "        <td>" . $serie->get_id_serie() . "</td>";
        echo "        <td>" . $serie->get_no_serie() . "</td>";
        echo "        <td>" . $serie->get_co_serie() . "</td>";
        echo "        <td>" . $serie->get_sg_ativo() . "</td>";
        echo "      </tr>";
    }
}

echo "	  </table>";
echo "	</div>";
echo "</div>";
?>		

	</div>
</body>
</html>