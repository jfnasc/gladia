/**
 * 
 */
package org.andromeda.torrentsearch;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * @author josen
 * 
 */
public class Main {

	// private static Parser p = new
	// org.andromeda.torrentsearch.parsers.PirateBayParser();
	private static Parser p = new org.andromeda.torrentsearch.parsers.EZTVParser();
	// private static Parser p = new
	// org.andromeda.torrentsearch.parsers.LimeTorrentsParser();

	public static void main(String[] args) {
		listarMaisRecentes();
		listar();
	}

	public static void listarMaisRecentes() {
		Template template = VelocityUtils.getTemplate("sidenav-resumo-tmpl");

		VelocityContext context = new VelocityContext();

		List<SerieInfoDTO> series = listarEpisodiosMaisRecentes();

		List<TorrentDTO> torrents = new ArrayList<>();
		for (SerieInfoDTO serie : series) {
			torrents.addAll(serie.getListTorrents());
		}

		Collections.sort(torrents);
		Collections.reverse(torrents);

		// titulo
		context.put("title", new String("Torrents::Search Results"));
		context.put("allTorrentsInfo", torrents);

		StringWriter sw = new StringWriter();
		template.merge(context, sw);

		try {
			FileWriter bw = new FileWriter("/var/www/html/torrents/resumo.html");
			bw.write(sw.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listar() {
		Template template = VelocityUtils.getTemplate("sidenav-tmpl");

		VelocityContext context = new VelocityContext();

		List<SerieInfoDTO> series = listarEpisodios();

		// titulo
		context.put("title", new String("Torrents::Search Results"));
		context.put("firstSerieName", series.get(0).getName());
		context.put("allSeriesInfo", series);

		StringWriter sw = new StringWriter();
		template.merge(context, sw);

		try {
			FileWriter bw = new FileWriter("/var/www/html/torrents/results.html");
			bw.write(sw.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<SerieInfoDTO> listarEpisodios() {

		List<SerieInfoDTO> series = getSeriesInfo();

		for (SerieInfoDTO serieInfoDTO : series) {
			List<TorrentDTO> episodes = p.listar(serieInfoDTO);
			serieInfoDTO.getListTorrents().addAll(episodes);
		}

		return series;
	}

	private static List<SerieInfoDTO> listarEpisodiosMaisRecentes() {

		List<SerieInfoDTO> series = getSeriesInfo();

		for (SerieInfoDTO serieInfoDTO : series) {
			List<TorrentDTO> episodes = p.listar(serieInfoDTO);
			if (!episodes.isEmpty()) {
				serieInfoDTO.getListTorrents().add(episodes.get(0));
			}
		}

		return series;
	}

	private static List<SerieInfoDTO> getSeriesInfo() {

		List<SerieInfoDTO> result = new ArrayList<>();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/series.lst")));

			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!line.trim().startsWith("#")) {
					SerieInfoDTO dto = new SerieInfoDTO();

					dto.setName(line.split(";")[0].trim());
					dto.setSearchCode(line.split(";")[1].trim());

					result.add(dto);
				}
			}

			if (result.isEmpty()) {
				System.out.println("Nada a fazer! :(");
				System.exit(0);
			}

		} catch (Exception e) {
			e.printStackTrace();

			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		Collections.sort(result);

		return result;
	}

}
