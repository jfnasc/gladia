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

public class KickAssParser extends Parser {

	private static String SEARCH_ENGINE = "KAT";

	private static String URL_BASE = "http://kat.how/search.php?q=";

	protected static Logger LOGGER = LogManager.getLogger(EZTVParser.class);

	private int limit = -1;
	private boolean isHighResolution = false;

	public KickAssParser() {
		super(SEARCH_ENGINE);
	}

	public KickAssParser(final int limit, final boolean isHighResolution) {
		super(SEARCH_ENGINE);

		this.limit = limit;
		this.isHighResolution = isHighResolution;
	}

	@Override
	public List<TorrentInfo> listar(SeriesDTO serieDTO) {

		System.out.println(serieDTO.getSerie().getNome());

		List<TorrentInfo> result = new ArrayList<>();

		String contents = getContents(URL_BASE, serieDTO.getSerie().getNome());

		List<String> bases = RegexUtils.extract(contents, "<!-- Start of Loop -->", "<!-- End of Loop -->");

		for (String base : bases) {

			List<String> linhas = RegexUtils.extract(base, "<tr>|<tr[\\w\\d\\s=\"]+>", "</tr>");

			int count = 0;

			for (String linha : linhas) {

				List<String> colunas = RegexUtils.extract(linha, "<td>|<td[\\w\\d\\s=\"]+>", "</td>");

				TorrentInfo dto = new TorrentInfo(SEARCH_ENGINE);

				String title = RegexUtils.extract(colunas.get(0), "https://kat.how/t0-", "-tt", true);
				dto.setTitle(title);
				System.out.println(title);

				// if (dto.getTitle().startsWith(serieInfo.getName())) {

				dto.setMagnetLink(RegexUtils.extract(colunas.get(0), "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
				dto.setSize(RegexUtils.replaceAll(colunas.get(1), "<td[\\w\\s\"_=]+>|</td>", ""));
				// dto.setReleased(RegexUtils.replaceAll(colunas.get(4),
				// "<td[\\w\\s\"_=]+>|</td>", ""));
				dto.setSeeds(RegexUtils.replaceAll(colunas.get(2), "<td[\\w\\s\"_=]+>|</td>", ""));

				if (!isHighResolution || (isHighResolution && dto.getTitle().indexOf("720p") != -1)) {
					result.add(dto);
					count++;
				}
				// }

				if (limit != -1 && count == limit) {
					break;
				}

			}

		}

		Collections.sort(result);

		return result;
	}

}
