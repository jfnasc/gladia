package org.manekineko.services.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public int buscarNroUltimoSorteio() {
		int resultado = 0;

		String url = rb.getString("url.ms.ultimosorteio");
		try {
			resultado = obterNumeroUltimoSorteio(response(url));
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
		int nroUltimoSorteioCEF = buscarNroUltimoSorteio();

		// último sorteio gravado
		int nroUltimoSorteioGravado = getResultadoDAO().buscarNroUltimoSorteioGravado("MS");

		if (nroUltimoSorteioGravado < nroUltimoSorteioCEF) {

			List<Sorteio> sorteios = new ArrayList<>();

			for (int numeroSorteio = nroUltimoSorteioGravado + 1; numeroSorteio <= nroUltimoSorteioCEF; numeroSorteio++) {
				sorteios.add(buscarSorteio(numeroSorteio));
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
		return buscarSorteio(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.manekineko.services.ResultadoService#buscarResultado(int)
	 */
	public Sorteio buscarSorteio(int numeroSorteio) {

		if (numeroSorteio < 1) {
			throw new IllegalArgumentException("Numero sorteio deve ser maior que zero.");
		}

		Sorteio result = getResultadoDAO().buscarSorteio("MS", numeroSorteio);
		
		if (result == null){
			String url = String.format("%s%s", rb.getString("url.ms"), numeroSorteio);

			try {
				result = criarSorteio(response(url));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private List<Integer> obterDezenasSorteadas(String s) {
		List<Integer> result = new ArrayList<Integer>();

		Pattern p = Pattern.compile("<Dezenas>[|0-9]*</Dezenas>");
		Matcher m = p.matcher(s);

		if (m.find()) {
			System.out.println(s.substring(m.start() + 9, m.end() - 10));
			String tmp = s.substring(m.start() + 9, m.end() - 10);
			for (String dezena : tmp.split("\\|")) {
				result.add(Integer.valueOf(dezena));
			}
		}

		return result;
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

		Pattern p = Pattern.compile("<Concurso>[0-9]*</Concurso>");
		Matcher m = p.matcher(s);

		if (m.find()) {
			System.out.println(s.substring(m.start() + 10, m.end() - 11));
			result = Integer.valueOf(s.substring(m.start() + 10, m.end() - 11));
		}

		return result;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private int obterNumeroUltimoSorteio(String s) {
		int result = 0;

		Pattern p = Pattern.compile("<Concurso>[0-9]*</Concurso>");
		Matcher m = p.matcher(s);

		if (m.find()) {
			System.out.println(s.substring(m.start() + 10, m.end() - 11));
			result = Integer.valueOf(s.substring(m.start() + 10, m.end() - 11));
		}

		return result;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private Date obterDataSorteio(String s) {

		//
		Date result = null;

		Pattern p = Pattern.compile("<Data>[-T:0-9]*</Data>");
		Matcher m = p.matcher(s);

		try {
			if (m.find()) {
				System.out.println(s.substring(m.start() + 6, m.end() - 16));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				result = sdf.parse(s.substring(m.start() + 6, m.end() - 16));
			}
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
		// rms.buscarResultado(1630);
		rms.atualizarResultados();
	}

	@Override
    public boolean isSorteioCadastrado(int numeroSorteio) {
	    return getResultadoDAO().buscarSorteio("MS", numeroSorteio) != null;
    }

}
