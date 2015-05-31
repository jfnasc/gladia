package org.manekineko.services.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.manekineko.Sorteio;
import org.manekineko.dao.ResultadoDAO;
import org.manekineko.dao.impl.ResultadoBaseDAOImpl;
import org.manekineko.services.BaseService;
import org.manekineko.services.ResultadoService;

/**
 * 
 * @author josen
 * 
 */
public class ResultadoMegaSenaService extends BaseService implements ResultadoService {

	static ResourceBundle rb = ResourceBundle.getBundle("maneki");

	static SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.manekineko.services.ResultadoService#buscarNumeroUltimoSorteioCEF()
	 */
	public int buscarNroUltimoSorteioCEF() {
		int resultado = 0;

		String url = rb.getString("url.ms");
		try {
			resultado = obterNumeroSorteio(response(url));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.manekineko.services.ResultadoService#atualizarResultados()
	 */
	public void atualizarResultados() {

		// último sorteio
		int nroUltimoSorteioCEF = buscarNroUltimoSorteioCEF();

		// último sorteio gravado
		int nroUltimoSorteioGravado = getResultadoDAO().buscarNroUltimoSorteioGravado("MS");

		if (nroUltimoSorteioGravado < nroUltimoSorteioCEF) {

			List<Sorteio> sorteios = new ArrayList<>();

			for (int numeroSorteio = nroUltimoSorteioGravado + 1; numeroSorteio <= nroUltimoSorteioCEF; numeroSorteio++) {
				sorteios.add(buscarResultado(numeroSorteio));
				if (sorteios.size() == 10) {
					getResultadoDAO().salvarSorteios(sorteios);
					sorteios.clear();
				}
			}

			// sorteios
			getResultadoDAO().salvarSorteios(sorteios);

		}

		// atrasos
		getResultadoDAO().registrarAtrasos(nroUltimoSorteioCEF, "MS", 60);

		// atualizar hashes
		getResultadoDAO().atualizarHashSorteios("MS");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.manekineko.services.ResultadoService#buscarResultado()
	 */
	public Sorteio buscarResultado() {
		return buscarResultado(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.manekineko.services.ResultadoService#buscarResultado(int)
	 */
	public Sorteio buscarResultado(int numeroSorteio) {
		Sorteio result = null;

		String url = null;
		if (numeroSorteio > 0) {
			url = String.format("%s%s%s", rb.getString("url.ms"), "?submeteu=sim&opcao=concurso&txtConcurso=",
					numeroSorteio);
		} else {
			url = rb.getString("url.ms");
		}

		try {
			result = criarSorteio(response(url));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private List<Integer> obterDezenasSorteadas(String s) {
		Integer[] result = new Integer[6];

		String[] parts = s.split("\\|");
		Pattern p = Pattern.compile("<li>[0-9]{2}</li>");
		Matcher m = p.matcher(parts[2]);

		int i = 0;
		while (m.find()) {
			result[i] = Integer.valueOf(parts[2].substring(m.start() + 4, m.end() - 5));
			i++;
		}

		return Arrays.asList(result);
	}

	private Sorteio criarSorteio(String resposta) {
		Sorteio sorteio = new Sorteio("MS");

		sorteio.setNuSorteio(obterNumeroSorteio(resposta));
		sorteio.setDezenas(obterDezenasSorteadas(resposta));
		sorteio.setDtSorteio(obterDataSorteio(resposta));

		return sorteio;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private int obterNumeroSorteio(String s) {
		int result = 0;

		String[] parts = s.split("\\|");
		result = Integer.valueOf(parts[0]);

		return result;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private Date obterDataSorteio(String s) {
		Date result = null;

		try {
			String[] parts = s.split("\\|");
			result = sdf.parse(parts[11]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 
	 */
	private ResultadoDAO resultadoDAO;

	/**
	 * 
	 * @return
	 */
	private ResultadoDAO getResultadoDAO() {
		if (this.resultadoDAO == null) {
			this.resultadoDAO = new ResultadoBaseDAOImpl();
		}
		return this.resultadoDAO;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ResultadoMegaSenaService rms = new ResultadoMegaSenaService();
		// print(rms.buscarResultado(1630));
		// print(rms.buscarResultado());
		rms.atualizarResultados();
	}

}
