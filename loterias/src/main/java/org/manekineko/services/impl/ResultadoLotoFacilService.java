package org.manekineko.services.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
public class ResultadoLotoFacilService extends BaseService implements
		ResultadoService {

	static ResourceBundle rb = ResourceBundle.getBundle("maneki");

	static SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.manekineko.services.ResultadoService#buscarNumeroUltimoSorteioCEF()
	 */
	public int buscarNroUltimoSorteio() {
		int resultado = 0;

		String url = rb.getString("url.lf");
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
		int nroUltimoSorteio = buscarNroUltimoSorteio();

		// último sorteio gravado
		int p = getResultadoDAO().buscarNroUltimoSorteioGravado("LF");

		List<Sorteio> sorteios = new ArrayList<>();
		for (int numeroSorteio = p + 1; numeroSorteio <= nroUltimoSorteio; numeroSorteio++) {
			sorteios.add(buscarResultado(numeroSorteio));
		}

		// sorteios
		getResultadoDAO().salvarSorteios(sorteios);

		// atrasos
		getResultadoDAO().registrarAtrasos(nroUltimoSorteio, "LF", 15);

		// atualizar hashes
		getResultadoDAO().atualizarHashSorteios("LF");

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
			url = String.format("%s%s%s", rb.getString("url.lf"),
					"?submeteu=sim&opcao=concurso&txtConcurso=", numeroSorteio);
		} else {
			url = rb.getString("url.lf");
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
		Integer[] result = new Integer[15];

		String[] parts = s.split("\\|");

		for (int i = 3; i < 18; i++) {
			result[i - 3] = Integer.valueOf(parts[i]);
		}

		return Arrays.asList(result);
	}

	private Sorteio criarSorteio(String resposta) {
		Sorteio sorteio = new Sorteio("LF");

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
			result = sdf.parse(parts[34]);
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
		ResultadoLotoFacilService rlf = new ResultadoLotoFacilService();
		// print(rms.buscarResultado(1630));
		// print(rms.buscarResultado());
		rlf.atualizarResultados();
	}

}
