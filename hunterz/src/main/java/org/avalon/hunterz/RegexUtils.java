package org.avalon.hunterz;

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

		sb.append("<td align=\"center\" class=\"forum_thread_post\">-</td>");

		System.out.println(RegexUtils.replaceAll(sb.toString(),"<td[\\d\\w\\s\"_=]+>|<font[\\w\\s\"_=]+>|</font>|</td>", ""));
	}

	public static List<String> extractTeste(String base, String start, String end) {

		List<String> result = new ArrayList<>();

		int beginIndex = 0;

		while (base.substring(beginIndex).indexOf(start) != -1) {
			int endIndex = base.substring(beginIndex).indexOf(end) + end.length();
			result.add(base.substring(beginIndex, endIndex).trim());
			base = base.substring(endIndex);
		}

		return result;
	}

	public static List<String> extract(String base, String start, String end) {

		List<String> result = new ArrayList<>();

		Pattern p = Pattern.compile(start);
		Matcher m = p.matcher(base);

		while (m.find()) {

			int beginIndex = m.start();
			int endIndex = beginIndex + base.substring(beginIndex).indexOf(end) + end.length();

			result.add(base.substring(beginIndex, endIndex));
		}

		return result;
	}

	public static String extract(String base, String start, String end, boolean removeTags) {

		String result = null;

		int beginIndex = base.indexOf(start);
		if (beginIndex == -1) {
			return null;
		}

		int endIndex = beginIndex + base.substring(beginIndex).indexOf(end);
	
		if (beginIndex == -1 || endIndex == -1) {
			return null;
		}
		
		result = base.substring(beginIndex + start.length(), endIndex);

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
