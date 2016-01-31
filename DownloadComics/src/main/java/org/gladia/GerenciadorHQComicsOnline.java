package org.gladia;

public class GerenciadorHQComicsOnline {
	public static void main(String[] args) {

		// http://hqcomicsonline.com.br/leitor/hq/Lazarus/01/Lazarus%20001-000.jpg
		// http://hqcomicsonline.com.br/leitor/hq/Lazarus/07/Lazarus-007-000.jpg
		// http://hqcomicsonline.com.br/leitor/hq/Lazarus/08/Lazarus-008-(2014)-(Digital)-(Nahga-Empire)-001.jpg
		// Lazarus-008-(2014)-(Digital)-(Nahga-Empire)-001.jpg
		// http://hqcomicsonline.com.br/leitor/hq/Lazarus/11/Lazarus%20011-000.jpg
		// http://hqcomicsonline.com.br/leitor/hq/Lazarus/12/xx01.jpg
		//http://hqcomicsonline.com.br/leitor/hq/Lazarus/13/Lazarus-013-000.jpg
		// http://hqcomicsonline.com.br/leitor/hq/Lazarus/16/01.jpg
		//http://hqcomicsonline.com.br/leitor/hq/Lazarus/06/Lazarus-006-000.jpg

		String home = "http://hqcomicsonline.com.br/leitor/hq/Lazarus/%s";

		int qtCapitulos = 20;
		int qtMaxPaginas = 50;

		Serie serie = new Serie();
		serie.setNome("Lazarus");

		for (int numeroCapitulo = 6; numeroCapitulo <= 6; numeroCapitulo++) {

			Capitulo capitulo = new Capitulo();
			capitulo.setNumeroCapitulo(numeroCapitulo);

			String urlCapitulo = String.format(home, Utils.completarNumeracao(numeroCapitulo, 2));
			for (int numeroPagina = 0; numeroPagina <= qtMaxPaginas; numeroPagina++) {
				capitulo.addUrlPagina(urlCapitulo + "/Lazarus-" + 
						Utils.completarNumeracao(numeroCapitulo, 3) + "-" +
						Utils.completarNumeracao(numeroPagina, 3) +".jpg");
			}

			serie.addCapitulo(capitulo);
		}

		GerenciadorDownloads.downloadSerie(serie);
	}
}
