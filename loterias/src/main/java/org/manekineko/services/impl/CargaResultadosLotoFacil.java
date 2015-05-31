/**
 * 
 */
package org.manekineko.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.manekineko.Sorteio;
import org.manekineko.dao.ResultadoDAO;
import org.manekineko.dao.impl.ResultadoBaseDAOImpl;
import org.manekineko.utils.StringUtils;

/**
 * @author josen
 * 
 */
public class CargaResultadosLotoFacil {

	private ResultadoDAO resultadoDAO = null;

	public static void main(String[] args) {
		CargaResultadosLotoFacil clf = new CargaResultadosLotoFacil();
		clf.importar();
	}

	public void importar() {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(new File("resultados/resultado_lf.csv")));

			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			String line = null;
			
			List<Sorteio> sorteios = new ArrayList<>();
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(";");

				Sorteio sorteio = new Sorteio("LF");

				sorteio.setNuSorteio(Integer.valueOf(parts[0]));
				sorteio.setDtSorteio(sdf.parse(parts[1]));
				sorteio.setDezenas(StringUtils.splitAsList(line.substring(
						line.indexOf(parts[1]) + parts[1].length() + 1)));

				sorteios.add(sorteio);
			}
			
			getResultadoDAO().salvarSorteios(sorteios);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ResultadoDAO getResultadoDAO() {
		if (this.resultadoDAO == null) {
			this.resultadoDAO = new ResultadoBaseDAOImpl();
		}
		return this.resultadoDAO;
	}
}
