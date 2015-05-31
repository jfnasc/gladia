/**
 * 
 */
package org.manekineko.tests;

import java.util.ArrayList;
import java.util.List;

import org.manekineko.TiposSorteio;
import org.manekineko.dao.ResultadoDAO;
import org.manekineko.dao.impl.ResultadoBaseDAOImpl;
import org.manekineko.regras.Regra;

/**
 * @author josen
 * 
 */
public class TestRegrasMegaSena {

	static List<Regra> regras = new ArrayList<Regra>();

	static {
		regras.add(new org.manekineko.regras.megasena.RegraNaoSequencial());
		regras.add(new org.manekineko.regras.megasena.RegraSorteiosAnteriores());
		regras.add(new org.manekineko.regras.megasena.RegraParesImpares());
		regras.add(new org.manekineko.regras.megasena.RegraNaoVertical());
		regras.add(new org.manekineko.regras.megasena.RegraAtraso());
	}

	/**
	 * 
	 */
	static ResultadoDAO resultadoDAO;

	/**
	 * 
	 * @return
	 */
	static ResultadoDAO getResultadoDAO() {
		if (resultadoDAO == null) {
			resultadoDAO = new ResultadoBaseDAOImpl();
		}
		return resultadoDAO;
	}

	public static void main(String[] args) {

		List<Integer[]> p = new ArrayList<Integer[]>();

		for (int i = 1; i <= 1636; i++) {
			List<Integer> dezenas = getResultadoDAO().buscarDezenasSorteadas(i, TiposSorteio.MEGASENA.sigla);

			Integer[] tmp = new Integer[dezenas.size()];
			dezenas.toArray(tmp);

			p.add(tmp);
		}

		for (Regra regra : regras) {
			regra.aplicar(p);
		}
	}
}
