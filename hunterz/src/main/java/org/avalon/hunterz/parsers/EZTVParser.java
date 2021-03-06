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

public class EZTVParser extends Parser {

	private static String SEARCH_ENGINE = "EZTV";

	private static String URL_BASE = "https://eztv.ag/search/";

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
	public List<TorrentInfo> listar(SeriesDTO serieDTO) {

		System.out.println(serieDTO.getSerie().getNome());
		
		List<TorrentInfo> result = new ArrayList<>();

		String contents = getContents(URL_BASE, serieDTO.getSerie().getCodigoBusca());

		List<String> bases = RegexUtils.extract(contents, "<tr name=\"hover\" class=\"forum_header_border\">", "</tr>");

		for (String base : bases) {

			List<String> linhas = RegexUtils.extract(base, "<tr>|<tr[\\w\\d\\s=\"]+>", "</tr>");

			int count = 0;

			for (String linha : linhas) {

				List<String> colunas = RegexUtils.extract(linha, "<td>|<td[\\w\\d\\s=\"]+>", "</td>");

				TorrentInfo dto = new TorrentInfo(SEARCH_ENGINE);

				dto.setTitle(RegexUtils.replaceAll(colunas.get(1), "[\\w\\s\\W]+class=\"epinfo\">|</a>|</td>", ""));

				if (dto.getTitle().startsWith(serieDTO.getSerie().getNome())) {

					dto.setMagnetLink(RegexUtils.extract(colunas.get(2), "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
					System.out.println(dto.getMagnetLink());
					dto.setSize(RegexUtils.replaceAll(colunas.get(3), "<td[\\w\\s\"_=]+>|</td>", ""));
					System.out.println(dto.getSize());
					dto.setReleased(RegexUtils.replaceAll(colunas.get(4), "<td[\\w\\s\"_=]+>|</td>", ""));
					System.out.println(dto.getReleased());
					dto.setSeeds(RegexUtils.replaceAll(colunas.get(5),"<td[\\d\\w\\s\"_=]+>|<font[\\w\\s\"_=]+>|</font>|</td>", ""));
					System.out.println(dto.getSeeds());

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
