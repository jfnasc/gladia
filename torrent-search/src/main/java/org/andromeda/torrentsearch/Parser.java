package org.andromeda.torrentsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Parser {

	private static Logger LOGGER = LogManager.getLogger(Parser.class);

	private String searchEngine;

	/**
	 * 
	 * @param searchEngine
	 */
	public Parser(String searchEngine) {
		this.searchEngine = searchEngine;
	}

	public abstract List<TorrentDTO> listar(String nomeSerie);

	protected void writeCache(String nomeSerie, String line) {
		FileWriter fw = null;

		try {

			fw = new FileWriter(getFileNameCache(nomeSerie));
			fw.write(line + "\n");
			fw.flush();

		} catch (IOException e) {

			LOGGER.error(e);

			try {
				fw.close();
			} catch (IOException e1) {
				LOGGER.error(e1);
			}
		}
	}

	/*
	 * 
	 */
	protected boolean isResultInCache(String nomeSerie) {
		return (new File(getFileNameCache(nomeSerie))).exists();
	}

	/*
	 * 
	 */
	protected Long getTimestamp() {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);

		return cal.getTimeInMillis();

	}

	protected String getContents(String url, String nomeSerie) {

		String line = null;

		if (!isResultInCache(nomeSerie)) {
			line = pesquisar(url + "/" + nomeSerie);
			writeCache(nomeSerie, line);
		} else {
			line = restoreFromCache(nomeSerie);
		}

		if (line != null) {
			line = (line.replaceAll(">[\\s]*<", "><")).trim();
		}

		return line;
	}

	/*
	 * 
	 */
	protected String getFileNameCache(String nomeSerie) {
		
		return String.format("./cache/%s-%s-%s.cache", searchEngine, nomeSerie, getTimestamp());

	}

	protected String restoreFromCache(String nomeSerie) {

		StringBuilder sb = new StringBuilder();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(new File(getFileNameCache(nomeSerie))));

			LOGGER.info("Loading file: " + getFileNameCache(nomeSerie));

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();

			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return sb.toString();
	}

	protected String pesquisar(String url) {

		String result = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = null;

		String line = null;

		try {
			response1 = httpclient.execute(httpGet);

			LOGGER.info(url);
			LOGGER.info(response1.getStatusLine());

			HttpEntity entity = response1.getEntity();
			InputStreamReader isr = new InputStreamReader(entity.getContent());
			BufferedReader br = new BufferedReader(isr);

			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			result = sb.toString();

		} catch (Exception e) {
			LOGGER.error(url);
			LOGGER.error(e);
		} finally {
			try {
				response1.close();
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}

		return result;
	}

}
