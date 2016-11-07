package org.andromeda.torrentsearch.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andromeda.torrentsearch.Parser;
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
	public List<TorrentDTO> listar(String nomeSerie) {

		String line = null;

		if (!isResultInCache(nomeSerie)) {
			line = pesquisar(URL_BASE + "/" + nomeSerie);
			writeCache(nomeSerie, line);
		} else {
			line = restoreFromCache(nomeSerie);
		}

		List<TorrentDTO> result = new ArrayList<>();

		String prefixo = "<tr name=\"hover\" class=\"forum_header_border\">";
		String sufixo = "</tr>";

		Pattern p = Pattern.compile(prefixo + "[\\W\\w\\d\\s]+" + sufixo);
		Matcher m = p.matcher(line);

		String base = null;
		if (m.find()) {
			base = line.substring(m.start(), m.end());
		}

		String[] episodes = base.split("</tr>");

		int count = 0;
		for (String episode : episodes) {

			String[] parts = episode.split("</td>");

			TorrentDTO dto = new TorrentDTO();

			dto.setTitle(replaceAll(parts[1], "[\\w\\s\\W]+class=\"epinfo\">|</a>", ""));
			dto.setMagnetLink(extract(parts[2], "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
			dto.setSize(replaceAll(parts[3], "<[\\w\\s\\W]+>", ""));
			dto.setReleased(replaceAll(parts[4], "<[\\w\\s\\W]+>", ""));
			dto.setSeeds(replaceAll(parts[5].replaceAll("</font>", ""), "<[\\w\\s\\W]+>", ""));

			if (!isHighResolution || (isHighResolution && dto.getTitle().indexOf("720p") != -1)) {
				result.add(dto);
				count++;
			}

			if (limit != -1 && count == limit) {
				break;
			}
		}

		Collections.sort(result);

		return result;
	}

}
