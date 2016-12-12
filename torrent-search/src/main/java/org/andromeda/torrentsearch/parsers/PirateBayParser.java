package org.andromeda.torrentsearch.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.andromeda.torrentsearch.Parser;
import org.andromeda.torrentsearch.RegexUtils;
import org.andromeda.torrentsearch.SerieInfoDTO;
import org.andromeda.torrentsearch.TorrentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PirateBayParser extends Parser {

	private static String SEARCH_ENGINE = "PirateBay";

	private static String URL_BASE = "https://thepiratebay.org/search";

	protected static Logger LOGGER = LogManager.getLogger(PirateBayParser.class);

	private int limit = -1;

	private boolean isHighResolution = false;

	/**
	 * 
	 */
	public PirateBayParser() {
		super(SEARCH_ENGINE);
	}

	/**
	 * 
	 * @param limit
	 * @param isHighResolution
	 */
	public PirateBayParser(final int limit, final boolean isHighResolution) {
		super(SEARCH_ENGINE);

		this.limit = limit;
		this.isHighResolution = isHighResolution;
	}

	@Override
	public List<TorrentDTO> listar(SerieInfoDTO serieInfo) {

		List<TorrentDTO> result = new ArrayList<>();

		String contents = getContents(URL_BASE, serieInfo.getName());

		List<String> bases = RegexUtils.extract(contents, "<table id=\"searchResult\">", "</table>");

		for (String base : bases) {

			List<String> linhas = RegexUtils.extract(base, "<tr>", "</tr>");

			int count = 0;

			for (String linha : linhas) {

				List<String> colunas = RegexUtils.extract(linha, "<td>|<td[\\w\\d\\s=\"]+>", "</td>");

				TorrentDTO dto = new TorrentDTO();

				dto.setTitle(RegexUtils.extract(colunas.get(1), "title=\"Details for ", "\">", true));
				dto.setMagnetLink(RegexUtils.extract(colunas.get(1), "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
				dto.setSize(RegexUtils.extract(colunas.get(1), "Size ", ",", true));
				dto.setReleased(RegexUtils.extract(colunas.get(1), "Uploaded ", ",", true));
				dto.setSeeds(RegexUtils.replaceAll(colunas.get(2), "<td[\\w\\s\"_=]+>|</td>", ""));

				if (!isHighResolution || (isHighResolution && dto.getTitle().indexOf("720p") != -1)) {
					result.add(dto);
					count++;
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
