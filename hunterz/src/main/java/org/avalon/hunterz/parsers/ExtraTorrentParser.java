package org.avalon.hunterz.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.avalon.hunterz.Parser;
import org.avalon.hunterz.RegexUtils;
import org.avalon.hunterz.SeriesDTO;
import org.avalon.hunterz.model.TorrentInfo;

public class ExtraTorrentParser extends Parser {

	private static String SEARCH_ENGINE = "EXT";

	private static String URL_BASE = "http://extratorrent.cc/search/?&new=1&x=0&y=0&search=";

	protected static Logger LOGGER = LogManager.getLogger(ExtraTorrentParser.class);

	private int limit = -1;
	private boolean isHighResolution = false;

	public ExtraTorrentParser() {
		super(SEARCH_ENGINE);
	}

	public ExtraTorrentParser(final int limit, final boolean isHighResolution) {
		super(SEARCH_ENGINE);

		this.limit = limit;
		this.isHighResolution = isHighResolution;
	}

	@Override
	public List<TorrentInfo> listar(SeriesDTO serieDTO) {

		System.out.println(serieDTO.getSerie().getNome());

		List<TorrentInfo> result = new ArrayList<>();

		String contents = getContents(URL_BASE, serieDTO.getSerie().getNome().replaceAll(" ", "+").toLowerCase());

		List<String> bases = RegexUtils.extract(contents, "<table class=\"tl\">", "</table><br />");

		for (String base : bases) {

			List<String> linhas = RegexUtils.extract(base, "<tr class=\"tlz\">|<tr class=\"tlr\">", "</tr>");

			int count = 0;

			for (String linha : linhas) {

				List<String> colunas = RegexUtils.extract(linha, "<td>|<td[\\w\\s\"_=]+>", "</td>");

				TorrentInfo dto = new TorrentInfo(SEARCH_ENGINE);

				dto.setTitle(RegexUtils.extract(colunas.get(0), "title=\"Download ", " torrent", true));

				if (dto.getTitle().startsWith(serieDTO.getSerie().getNome())) {

					dto.setMagnetLink("magnet:" + RegexUtils.extract(colunas.get(0), "magnet:", "\"", true));
					dto.setSize(RegexUtils.replaceAll(colunas.get(4), "<td>|</td>", ""));
					dto.setReleased(RegexUtils.replaceAll(colunas.get(3), "<td>|</td>", ""));
					dto.setSeeds(RegexUtils.replaceAll(colunas.get(5), "<td[\\w\\s\"_=]+>|</td>", ""));

					if (!isHighResolution || (isHighResolution && dto.getTitle().indexOf("720p") != -1)) {
						result.add(dto);
						count++;
					}
				}
				
				if (limit != -1 && count == limit) {
					break;
				}

			}

		}

		Collections.sort(result);

		return result;
	}

}
