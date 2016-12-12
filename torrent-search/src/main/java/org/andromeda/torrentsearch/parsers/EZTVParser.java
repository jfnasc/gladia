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

public class EZTVParser extends Parser {

	private static String SEARCH_ENGINE = "EZTV";

	private static String URL_BASE = "https://eztv.ag/search";

	protected static Logger LOGGER = LogManager.getLogger(EZTVParser.class);

	private int limit = -1;
	private boolean isHighResolution = false;

	public EZTVParser() {
		super(SEARCH_ENGINE);
	}

	public EZTVParser(final int limit, final boolean isHighResolution) {
		super(SEARCH_ENGINE);

		this.limit = limit;
		this.isHighResolution = isHighResolution;
	}

	@Override
	public List<TorrentDTO> listar(SerieInfoDTO serieInfo) {

		System.out.println(serieInfo.getName());
		
		List<TorrentDTO> result = new ArrayList<>();

		String contents = getContents(URL_BASE, serieInfo.getSearchCode());

		List<String> bases = RegexUtils.extract(contents, "<tr name=\"hover\" class=\"forum_header_border\">", "</tr>");

		for (String base : bases) {

			List<String> linhas = RegexUtils.extract(base, "<tr>|<tr[\\w\\d\\s=\"]+>", "</tr>");

			int count = 0;

			for (String linha : linhas) {

				List<String> colunas = RegexUtils.extract(linha, "<td>|<td[\\w\\d\\s=\"]+>", "</td>");

				TorrentDTO dto = new TorrentDTO();

				dto.setTitle(RegexUtils.replaceAll(colunas.get(1), "[\\w\\s\\W]+class=\"epinfo\">|</a>|</td>", ""));

				if (dto.getTitle().startsWith(serieInfo.getName())) {

					dto.setMagnetLink(RegexUtils.extract(colunas.get(2), "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
					dto.setSize(RegexUtils.replaceAll(colunas.get(3), "<td[\\w\\s\"_=]+>|</td>", ""));
					dto.setReleased(RegexUtils.replaceAll(colunas.get(4), "<td[\\w\\s\"_=]+>|</td>", ""));
					dto.setSeeds(RegexUtils.replaceAll(colunas.get(5),
							"<td[\\w\\s\"_=]+><font[\\w\\s\"_=]+>|</font></td>", ""));

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
