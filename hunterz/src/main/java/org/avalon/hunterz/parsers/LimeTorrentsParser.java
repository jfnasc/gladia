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

public class LimeTorrentsParser extends Parser {

	private static String SEARCH_ENGINE = "LMT";

	private static String URL_BASE = "https://www.limetorrents.cc/search/all/";

	protected static Logger LOGGER = LogManager.getLogger(LimeTorrentsParser.class);

	private int limit = -1;
	private boolean isHighResolution = false;

	public LimeTorrentsParser() {
		super(SEARCH_ENGINE);
	}

	public LimeTorrentsParser(final int limit, final boolean isHighResolution) {
		super(SEARCH_ENGINE);

		this.limit = limit;
		this.isHighResolution = isHighResolution;
	}

	@Override
	public List<TorrentInfo> listar(SeriesDTO serieDTO) {

		List<TorrentInfo> result = new ArrayList<>();

		String contents = getContents(URL_BASE, serieDTO.getSerie().getNome());

		List<String> bases = RegexUtils.extract(contents, "<table class=\"table2\" cellpadding=\"6\" cellspacing=\"0\">", "<\\table>");

		for (String base : bases) {

			List<String> linhas = RegexUtils.extract(base, "<tr>|<tr[\\w\\d\\s=\"]+>", "</tr>");

			int count = 0;

			for (String linha : linhas) {

				List<String> colunas = RegexUtils.extract(linha, "<td>|<td[\\w\\d\\s=\"]+>", "</td>");

				TorrentInfo dto = new TorrentInfo(SEARCH_ENGINE);

				dto.setTitle(RegexUtils.replaceAll(colunas.get(1), "[\\w\\s\\W]+class=\"epinfo\">|</a>|</td>", ""));
				dto.setMagnetLink(RegexUtils.extract(colunas.get(2), "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
				dto.setSize(RegexUtils.replaceAll(colunas.get(3), "<td[\\w\\s\"_=]+>|</td>", ""));
				dto.setReleased(RegexUtils.replaceAll(colunas.get(4), "<td[\\w\\s\"_=]+>|</td>", ""));
				dto.setSeeds(
						RegexUtils.replaceAll(colunas.get(5), "<td[\\w\\s\"_=]+><font[\\w\\s\"_=]+>|</font></td>", ""));

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
