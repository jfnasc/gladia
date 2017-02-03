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

import org.andromeda.torrentsearch.parsers.EZTVParser;
import org.andromeda.torrentsearch.parsers.KickAssParser;
import org.andromeda.torrentsearch.parsers.PirateBayParser;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * @author josen
 * 
 */
public class Main {

	static List<Parser> parsers = null;

	static {
		parsers = new ArrayList<>();

		// parsers.add(new ExtraTorrentParser());
		parsers.add(new EZTVParser());
		parsers.add(new PirateBayParser());
		parsers.add(new KickAssParser());
	}

	public static void main(String[] args) {
		for (Parser p : parsers) {
			listarMaisRecentes(parsers);
			listar(p);
		}
	}

	public static void listarMaisRecentes(List<Parser> parsers) {
		Template template = VelocityUtils.getTemplate("sidenav-resumo-tmpl");

		VelocityContext context = new VelocityContext();

		List<TorrentDTO> torrents = new ArrayList<>();

		for (Parser p : parsers) {

			List<SerieInfoDTO> series = listarEpisodiosMaisRecentes(p);

			for (SerieInfoDTO serie : series) {
				torrents.addAll(serie.getListTorrents());
			}
		}

		Collections.sort(torrents);
		Collections.reverse(torrents);

		// titulo
		context.put("title", new String("Torrents::Search Results"));
		context.put("allTorrentsInfo", torrents);

		StringWriter sw = new StringWriter();
		template.merge(context, sw);

		try {
			FileWriter bw = new FileWriter("pages/resumo.html");
			bw.write(sw.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listar(Parser p) {
		Template template = VelocityUtils.getTemplate("sidenav-tmpl");

		VelocityContext context = new VelocityContext();

		List<SerieInfoDTO> series = listarEpisodios(p);

		// titulo
		context.put("title", new String("Torrents::Search Results"));
		context.put("firstSerieName", series.get(0).getName());
		context.put("allSeriesInfo", series);

		StringWriter sw = new StringWriter();
		template.merge(context, sw);

		try {
			FileWriter bw = new FileWriter("pages/" + p.getClass().getSimpleName() + ".html");
			bw.write(sw.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<SerieInfoDTO> listarEpisodios(Parser p) {

		List<SerieInfoDTO> series = getSeriesInfo();

		for (SerieInfoDTO serieInfoDTO : series) {
			List<TorrentDTO> episodes = (p).listar(serieInfoDTO);
			serieInfoDTO.getListTorrents().addAll(episodes);
		}

		return series;
	}

	private static List<SerieInfoDTO> listarEpisodiosMaisRecentes(Parser p) {

		List<SerieInfoDTO> series = getSeriesInfo();

		for (SerieInfoDTO serieInfoDTO : series) {
			List<TorrentDTO> episodes = (p).listar(serieInfoDTO);
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
