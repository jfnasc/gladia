package org.andromeda.torrentsearch.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andromeda.torrentsearch.DateUtils;
import org.andromeda.torrentsearch.Parser;
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
	public List<TorrentDTO> listar(String nomeSerie) {
		String line = null;

		if (!isResultInCache(nomeSerie)) {
			line = pesquisar(URL_BASE + "/" + nomeSerie);
			writeCache(nomeSerie, line);
		} else {
			line = restoreFromCache(nomeSerie);
		}

		List<TorrentDTO> result = new ArrayList<>();

		// primeira fase
		Pattern p = Pattern.compile("<table id=\"searchResult\">[\\W\\w\\d\\s]+</table>");
		Matcher m = p.matcher(line);

		String base = null;
		if (m.find()) {
			base = line.substring(m.start(), m.end());

			// segunda fase
			p = Pattern.compile("<tr>[\\W\\w\\d\\s]+</tr>");
			m = p.matcher(line);

			if (m.find()) {
				base = line.substring(m.start(), m.end());
			}
		}

		String[] episodes = base.split("</tr>");

		int count = 0;

		for (String episode : episodes) {

			String[] parts = episode.split("</td>");

			TorrentDTO dto = new TorrentDTO();

			System.out.println("1 " + parts[1]);
			System.out.println("2 " + parts[2]);
			System.out.println("3 " + parts[3]);
			
			// title="Details for Blade Runner (1982) [The FInal Cut] 720p BrRip
			// - 700MB - YIFY">

			dto.setTitle(extract(parts[1], "title=\"Details for [\\w\\d\\s\\~\\{\\}\\|\\/\\\\\'\\&\\;\\-\\:\\*\\+\\,\\.\\_\\-(\\)\\[\\]#]+\"").replaceAll("title=\"Details for |\"", ""));
			dto.setMagnetLink(extract(parts[1], "magnet:[\\w\\d\\?=:&\\_\\,\\.\\%\\-]*"));

			// dto.setSize(replaceAll(parts[3], "<[\\w\\s\\W]+>", ""));
			// dto.setReleased(replaceAll(parts[4], "<[\\w\\s\\W]+>", ""));
			dto.setSeeds(extract(parts[2], "[\\d]+$"));
			dto.setLeechers(extract(parts[3], "[\\d]+$"));
			
			String size = extract(parts[1], "Size[\\s\\d\\.\\,]+&nbsp;[\\w]+,");
			if (size != null){
				size = size.replaceAll("Size[\\s]+|,", "");
			}
			dto.setSize(size);
			
			String released = extract(parts[1], "Uploaded[\\s\\d-]+&nbsp;[\\w\\d]*,");
			if (released != null){
				released = released.replaceAll("Uploaded[\\s]+", "");
				released = released.replaceAll("&nbsp;", "-");
				released = DateUtils.format(DateUtils.parse(released, "mm-DD-yyyy"),"dd/MM/yyyy");
			}
			dto.setReleased(released);
			//
			// if (!isHighResolution || (isHighResolution &&
			// dto.getTitle().indexOf("720p") != -1)) {
			result.add(dto);
			// count++;
			// }
			//
			// if (limit != -1 && count == limit) {
			// break;
			// }
			
		}

		Collections.sort(result);

		return result;

	}

}
