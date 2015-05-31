package org.manekineko.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestEpub {
	
	public static void main2(String[] args) {
		String contents = "http://baixaragora.jegueajato.com/Jorge%20Lourenco/Rio%202054%20(170)/Rio%202054%20-%20Jorge%20Lourenco.epub";
		Pattern p = Pattern.compile("[:\\.\\(\\)\\-\\%\\/\\w]*.epub");
		Matcher m = p.matcher(contents);
		// http://lelivros.gratis/book/download-deixados-para-tras-deixados-para-tras-vol-1-tim-lahaye-em-epub-mobi-e-pdf/
		if (m.find()) {
			System.out.println(contents.substring(m.start(), m.end()));
		}
	}
	
	public static void main(String[] args) throws Exception {
		String host = "lelivros.gratis";
		String url = "/categoria/ficcao-cientifica";
		
		List<String> listUrls = listUrls(host, url);
		for (String urlEpub : listUrls) {
			System.out.println(getLink(openUrl(host, urlEpub)));
		}
	}
	
	private static String getLink(String result){
		Pattern p = Pattern.compile("[:\\.\\(\\)\\-\\%\\/\\w]*.epub");
		Matcher m = p.matcher(result);

		while (m.find()) {
			String tmp = result.substring(m.start(), m.end());
			if (tmp.endsWith(".epub")){
				return tmp;
			}
		}
		return null;
	}

	private static List<String> listUrls(String host, String url) throws Exception {
		List<String> result = new ArrayList<String>();

		String contents = openUrl(host, url);
		Pattern p = Pattern.compile("http://lelivros.gratis/book/[-a-z0-9]*/");
		Matcher m = p.matcher(contents);
		// http://lelivros.gratis/book/download-deixados-para-tras-deixados-para-tras-vol-1-tim-lahaye-em-epub-mobi-e-pdf/
		while (m.find()) {
			String urlEpub = contents.substring(m.start(), m.end()).substring(("http://" + host).length());
			if (!result.contains(urlEpub)){
				result.add(urlEpub);	
			}
		}

		return result;
	}

	private static String openUrl(String host, String url) throws Exception {
		String result = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpHost target = new HttpHost(host, 80, "http");

		// HttpHost proxy = new HttpHost("proxy.spread.com.br", 8080, "http");
		// RequestConfig config =
		// RequestConfig.custom().setProxy(proxy).build();
		// HttpGet request = new HttpGet(url);
		// request.setConfig(config);

		RequestConfig config = RequestConfig.custom().build();
		HttpGet request = new HttpGet(url);
		request.setConfig(config);

		System.out.println("Executing request " + request.getRequestLine() + " to " + target);

		response = httpclient.execute(target, request);
		if (response.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(response.getEntity());
		}

		return result;
	}
}
