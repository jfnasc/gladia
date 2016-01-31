package org.gladia;

public class GerenciadorHQComicsOnline {
	public static void main(String[] args) {

		// http://hqcomicsonline.com.br/leitor/hq/Lazarus/01/Lazarus%20001-000.jpg

		String home = "http://hqcomicsonline.com.br/leitor/hq/Lazarus/%s";

		int qtCapitulos = 1;
		int qtMaxPaginas = 50;

		Serie serie = new Serie();
		serie.setNome("Lazarus");

		for (int numeroCapitulo = 1; numeroCapitulo <= qtCapitulos; numeroCapitulo++) {

			Capitulo capitulo = new Capitulo();
			capitulo.setNumeroCapitulo(numeroCapitulo);

			String urlCapitulo = String.format(home, Utils.completarNumeracao(numeroCapitulo, 2));
			for (int numeroPagina = 0; numeroPagina <= qtMaxPaginas; numeroPagina++) {
				capitulo.addUrlPagina(urlCapitulo + "/Lazarus%200" + Utils.completarNumeracao(numeroCapitulo, 2) + "-"
				        + Utils.completarNumeracao(numeroPagina, 3) + ".jpg");
			}

			serie.addCapitulo(capitulo);
		}

		GerenciadorDownloads.downloadSerie(serie);
	}
}
