<?php
if (isset($listaTorrents) && count($listaTorrents) > 0) {
    ?>
<div class="container">
	<div class="container-fluid">
		<table class="table table-condensed table-hover">
			<tr>
				<th>Title</th>
				<th><span class="glyphicon glyphicon-download-alt"></span></th>
				<th>Source</th>
				<th>Size</th>
				<th>Released</th>
				<th>Seeds</th>
				<th>Leechers</th>
			</tr>
<?php
    foreach ($listaTorrents as $torrent) {
        echo "<tr>";
        echo "  <td>" . $torrent->get_de_title() . "</td>";
        echo "  <td><a href=\"" . $torrent->get_de_magnet_link() . "\">";
        echo "    <span class=\"glyphicon glyphicon-download-alt\"></span>";
        echo "  </td></a>";
        echo "  <td>" . $torrent->get_co_search_engine() . "</td>";
        echo "  <td>" . $torrent->get_nu_size() . "</td>";
        echo "  <td>" . $torrent->get_dt_released() . "</td>";
        echo "  <td>" . $torrent->get_qt_seeds() . "</td>";
        echo "  <td>" . $torrent->get_qt_leechers() . "</td>";
        echo "</tr>";
    }
    ?>
    </table>
	</div>
</div>
<?php
}
?>
