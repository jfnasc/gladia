package org.andromeda.torrentsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RegexUtils {

	public static void main(String[] args) {

		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"table2\" cellpadding=\"6\" cellspacing=\"0\">");
		sb.append("    <tr>");
		sb.append(
				"        <th class=\"thleft\"><span style=\"float:left\">Torrent Name</span><span style=\"float:right\"><img src=\"/static/images/comment16.png\" alt=\"Comments\" title=\"Comments\" /></a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<img src=\"/static/images/vup16.png\" alt=\"Good\" title=\"Good\" /></a></span></th>");
		sb.append("        <th class=\"thnormal\">Added</a></th>");
		sb.append("        <th class=\"thnormal\">Size</a></th>");
		sb.append("        <th class=\"thnormal\"><a href=\"/search/all/blacklist-s01/seeds/1/\">Seed</a></th>");
		sb.append("        <th class=\"thnormal\">Leech</a></th>");
		sb.append("        <th class=\"thright\">Health</th>");
		sb.append("    </tr>");
		sb.append("    <tr bgcolor=\"#F4F4F4\">");
		sb.append("        <td class=\"tdleft\">");
		sb.append(
				"            <div class=\"tt-name\"><a href=\"http://itorrents.org/torrent/B6F20E2129B829AC16989AB80048E29B66EDF873.torrent?title=The-Blacklist-S01-03-Season-1-3-COMPLETE-720p-BluRay-x264-Pahe\" rel=\"nofollow\" class=\"csprite_dl14\"></a><a href=\"/The-Blacklist-S01-03-Season-1-3-COMPLETE-720p-BluRay-x264-Pahe-torrent-8180130.html\">The Blacklist S01-03 Season 1-3 COMPLETE 720p BluRay x264-Pahe</a></div>  ");
		sb.append("            <div class=\"tt-options\"></div>");
		sb.append("        </td>");
		sb.append("        <td class=\"tdnormal\">4 months ago - in TV shows</a></td>");
		sb.append("        <td class=\"tdnormal\">19.66 GB</td>");
		sb.append("        <td class=\"tdseed\">0</td>");
		sb.append("        <td class=\"tdleech\">0</td>");
		sb.append("        <td class=\"tdright\">");
		sb.append("            <div class=\"hb0\"></div>");
		sb.append("        </td>");
		sb.append("    </tr>");
		sb.append("</table>    ");
		sb.append("<table class=\"table2\" cellpadding=\"6\" cellspacing=\"0\">");
		sb.append("    <tr>");
		sb.append(
				"        <th class=\"thleft\"><span style=\"float:left\">Torrent Name</span><span style=\"float:right\"><img src=\"/static/images/comment16.png\" alt=\"Comments\" title=\"Comments\" /></a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<img src=\"/static/images/vup16.png\" alt=\"Good\" title=\"Good\" /></a></span></th>");
		sb.append("        <th class=\"thnormal\">Added</a></th>");
		sb.append("        <th class=\"thnormal\">Size</a></th>");
		sb.append("        <th class=\"thnormal\"><a href=\"/search/all/blacklist-s01/seeds/1/\">Seed</a></th>");
		sb.append("        <th class=\"thnormal\">Leech</a></th>");
		sb.append("        <th class=\"thright\">Health</th>");
		sb.append("    </tr>");
		sb.append("    <tr bgcolor=\"#F4F4F4\">");
		sb.append("        <td class=\"tdleft\">");
		sb.append(
				"            <div class=\"tt-name\"><a href=\"http://itorrents.org/torrent/B6F20E2129B829AC16989AB80048E29B66EDF873.torrent?title=The-Blacklist-S01-03-Season-1-3-COMPLETE-720p-BluRay-x264-Pahe\" rel=\"nofollow\" class=\"csprite_dl14\"></a><a href=\"/The-Blacklist-S01-03-Season-1-3-COMPLETE-720p-BluRay-x264-Pahe-torrent-8180130.html\">The Blacklist S01-03 Season 1-3 COMPLETE 720p BluRay x264-Pahe</a></div>  ");
		sb.append("            <div class=\"tt-options\"></div>");
		sb.append("        </td>");
		sb.append("        <td class=\"tdnormal\">4 months ago - in TV shows</a></td>");
		sb.append("        <td class=\"tdnormal\">19.66 GB</td>");
		sb.append("        <td class=\"tdseed\">0</td>");
		sb.append("        <td class=\"tdleech\">0</td>");
		sb.append("        <td class=\"tdright\">");
		sb.append("            <div class=\"hb0\"></div>");
		sb.append("        </td>");
		sb.append("    </tr>");
		sb.append("</table>    ");

		System.out.println(
				extract(sb.toString(), "<table class=\"table2\" cellpadding=\"6\" cellspacing=\"0\">", "</table>"));

	}

	public static List<String> extract(String base, String start, String end) {

		List<String> result = new ArrayList<>();

		int beginIndex = 0;

		while (base.substring(beginIndex).indexOf(start) != -1) {
			int endIndex = base.substring(beginIndex).indexOf(end) + end.length();
			result.add(base.substring(beginIndex, endIndex).trim());
			base = base.substring(endIndex);
		}

		return result;
	}

	public static String extract(String base, String start, String end, boolean removeTags) {

		String result = null;

		Pattern p = Pattern.compile(start);
		Matcher m = p.matcher(base);

		if (m.find()) {

			int beginIndex = m.start();
			int endIndex = beginIndex + base.substring(beginIndex).indexOf(end) + end.length();

			result = base.substring(beginIndex, endIndex);

			if (removeTags) {
				result = result.replaceAll("^" + start, "");
				result = result.replaceAll(end + "$", "");
			}
		}

		return result;
	}

	public static void main3(String[] args) {

		String base = "<tr name=\"hover\" class=\"forum_header_border\"><td width=\"35\" class=\"forum_thread_post\" align=\"center\"><a href=\"/shows/1582/lucifer/\" title=\"Lucifer Torrent\"><img src=\"/images/eztv_show_info3.png\" border=\"0\" alt=\"Info\" title=\"Lucifer Torrents\"></a></td><td class=\"forum_thread_post\"><a href=\"/ep/161425/lucifer-s02e06-720p-hdtv-x264-dimension/\" title=\"Lucifer S02E06 720p HDTV X264-DIMENSION [eztv] (1.04 GB)\" alt=\"Lucifer S02E06 720p HDTV X264-DIMENSION [eztv] (1.04 GB)\" class=\"epinfo\">Lucifer S02E06 720p HDTV X264-DIMENSION [eztv]</a></td><td align=\"center\" class=\"forum_thread_post\"><a href=\"magnet:?xt=urn:btih:12a9d246afbe7ab4ba616cb1b3c180fb0eff1420&dn=Lucifer.S02E06.720p.HDTV.X264-DIMENSION%5Beztv%5D.mkv%5Beztv%5D&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A80&tr=udp%3A%2F%2Fglotorrents.pw%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&tr=udp%3A%2F%2Fexodus.desync.com%3A6969\" class=\"magnet\" title=\"Lucifer S02E06 720p HDTV X264-DIMENSION [eztv] (1.04 GB) Magnet Link\" rel=\"nofollow\"></a><a href=\"https://zoink.ch/torrent/Lucifer.S02E06.720p.HDTV.X264-DIMENSION[eztv].mkv.torrent\" rel=\"nofollow\" class=\"download_1\" title=\"Lucifer S02E06 720p HDTV X264-DIMENSION Torrent: Download Mirror #1\"></a></td><td align=\"center\" class=\"forum_thread_post\">1.04 GB</td><td align=\"center\" class=\"forum_thread_post\">2d 7h</td><td align=\"center\" class=\"forum_thread_post\"><font color=\"green\">232</font></td><td align=\"center\" class=\"forum_thread_post_end\"><a href=\"/forum/discuss/161425/\" rel=\"nofollow\" title=\"Discuss about Lucifer S02E06 720p HDTV X264-DIMENSION [eztv]:\"><img src=\"/ezimg/s/1/3/chat_empty.png\" border=\"0\" width=\"16\" height=\"16\" alt=\"Discuss\" title=\"Discuss about this show\"/></a></td>";
		String[] parts = base.split("</td>");

		System.out.println(replaceAll(parts[1], "[\\w\\s\\W]+class=\"epinfo\">|</a>", ""));
		System.out.println(extract(parts[2], "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
		System.out.println(replaceAll(parts[3], "<[\\w\\s\\W]+>", ""));
		System.out.println(replaceAll(parts[4], "<[\\w\\s\\W]+>", ""));
		System.out.println(extract(parts[5], "\\d+</font").replaceAll("</font", ""));
		System.out.println(replaceAll(parts[6], "</font>|<[\\w\\s\\W]+>", ""));

	}

	public static String extract(String arg0, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(arg0);

		if (m.find()) {
			return (arg0.substring(m.start(), m.end()));
		}

		return null;
	}

	public static String replaceAll(String arg0, String regex, String arg1) {
		return arg0.replaceAll(regex, arg1);
	}

	public static void main2(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://eztv.ag/search/lucifer");
		CloseableHttpResponse response1 = null;

		String line = null;

		try {
			response1 = httpclient.execute(httpGet);
			System.out.println(response1.getStatusLine());

			HttpEntity entity = response1.getEntity();
			InputStreamReader isr = new InputStreamReader(entity.getContent());
			BufferedReader br = new BufferedReader(isr);

			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			teste(sb.toString());

		} catch (Exception e) {
			System.out.println(line);
			e.printStackTrace();
		} finally {
			try {
				response1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void teste(String line) {
		String prefixo = "<tr name=\"hover\" class=\"forum_header_border\">";
		String sufixo = "</tr>";

		Pattern p = Pattern.compile(prefixo + "[\\W\\w\\d\\s]+" + sufixo);
		Matcher m = p.matcher(line);

		String base = null;
		if (m.find()) {
			base = line.substring(m.start(), m.end());
		}

		String[] parts = base.split("</tr>");
		for (String part : parts) {
			System.out.println(part);
		}
	}

}
