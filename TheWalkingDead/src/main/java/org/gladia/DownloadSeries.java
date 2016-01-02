/**
 * 
 */
package org.gladia;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Administrador
 *
 */
public class DownloadSeries {

	public static void main(String... args) throws IOException {

		for (int j = 24; j < 1000; j++) {
		//for (int j = 21; j < 22; j++) {

			for (int i = 1; i < 30; i++) {
				boolean t1 = DownloadSeries.downloadFile(Config.getString("url.home"), obterNomeEdicao(j),
				        obterNomeArquivo(i, false));

				if (!t1) {
					DownloadSeries.downloadFile(Config.getString("url.home"), obterNomeEdicao(j),
					        obterNomeArquivo(i, true));
				}
			}

			ZipUtils.zip(obterNomeEdicao(j), Config.getString("target.dir"), Config.getString("target.dir"));
		}

	}

	public static boolean downloadFile(String urlHome, String edicao, String filename) throws IOException {
		String url = String.format("%s/%s/%s", urlHome, edicao, filename);
		System.out.println(url);

		String dir = String.format("%s/%s", Config.getString("target.dir"), edicao);
		(new File(dir)).mkdirs();

		String filePath = String.format("%s/%s/%s", Config.getString("target.dir"), edicao, filename);
		System.out.println(filePath);

		if ((new File(filePath)).exists()) {
			System.out.println(filePath + " ja existe.");
			return true;
		}

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);

		System.out.println(response.getStatusLine());
		if (response.getStatusLine().getStatusCode() == 404) {
			return false;
		}
		HttpEntity entity = response.getEntity();

		try {
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					BufferedInputStream bis = new BufferedInputStream(instream);

					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					int inByte;
					while ((inByte = bis.read()) != -1) {
						bos.write(inByte);
					}
					bis.close();
					bos.close();
				} catch (IOException ex) {
					throw ex;
				} catch (RuntimeException ex) {
					throw ex;
				} finally {
					instream.close();
				}
			}
		} finally {
			response.close();
		}

		return true;
	}

	private static String obterNomeEdicao(int i) {
		if (i < 10) {
			return "edicao000" + i;
		} else if (i < 100) {
			return "edicao00" + i;
		} else if (i < 1000) {
			return "edicao0" + i;
		}

		return "edicao" + i;
	}

	private static String obterNomeArquivo(int i, boolean upper) {
		String suffix = ".jpg";

		if (i < 10) {
			return "www.thewalkingdead-online.com-00" + i + (upper ? suffix.toUpperCase() : suffix);
		} else if (i < 100) {
			return "www.thewalkingdead-online.com-0" + i + (upper ? suffix.toUpperCase() : suffix);
		}

		return "www.thewalkingdead-online.com-" + i + (upper ? suffix.toUpperCase() : suffix);
	}
}
