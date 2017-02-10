package org.gladia;

import java.util.ArrayList;
import java.util.List;

public class Capitulo {

	private int numeroCapitulo;
	private List<String> urlPaginas = new ArrayList<String>();
	private String homeDir;

	/**
	 * @return the numeroCapitulo
	 */
	public int getNumeroCapitulo() {
		return numeroCapitulo;
	}

	/**
	 * @param numeroCapitulo
	 *            the numeroCapitulo to set
	 */
	public void setNumeroCapitulo(int numeroCapitulo) {
		this.numeroCapitulo = numeroCapitulo;
	}

	/**
	 * @return the urlPaginas
	 */
	public List<String> getUrlPaginas() {
		return urlPaginas;
	}

	/**
	 * @param urlPaginas
	 *            the urlPaginas to set
	 */
	public void setUrlPaginas(List<String> urlPaginas) {
		this.urlPaginas = urlPaginas;
	}

	public void addUrlPagina(String urlPagina) {
		getUrlPaginas().add(urlPagina);
	}

	/**
	 * @return the homeDir
	 */
	public String getHomeDir() {
		return homeDir;
	}

	/**
	 * @param homeDir
	 *            the homeDir to set
	 */
	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir + "/" + Utils.completarNumeracao(numeroCapitulo, 4);
	}

}
