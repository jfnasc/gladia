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

public class GerenciadorDownloads {

	public static final void downloadSerie(Serie serie) {
		String homeDir = "D:/projetos/tmp" + "/" + serie.getNome();

		new File(homeDir).mkdirs();

		try {
			for (Capitulo capitulo : serie.getCapitulos()) {
				capitulo.setHomeDir(homeDir);
				for (String url : capitulo.getUrlPaginas()) {
					System.out.println(url);
					if (!downloadFile(url, capitulo.getHomeDir())){
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean downloadFile(String url, String dir) throws IOException {

		System.out.println(url);
		System.out.println(dir);
		System.out.println(Utils.extrairUltimaParte(url, "/"));
		String filePath = dir + "/" + Utils.extrairUltimaParte(url, "/");
		System.out.println(filePath);

		(new File(dir)).mkdirs();

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
}
