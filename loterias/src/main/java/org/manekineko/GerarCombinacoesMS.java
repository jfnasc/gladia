package org.manekineko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.manekineko.dao.ResultadoDAO;
import org.manekineko.dao.impl.ResultadoBaseDAOImpl;
import org.manekineko.regras.Regra;
import org.manekineko.utils.RndUtils;
import org.manekineko.utils.StringUtils;

public class GerarCombinacoesMS {

	static ResultadoDAO resultadoDAO = null;

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
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer[]> prognosticos = prognosticos(10,6);

		System.out.println("--------------------------");
		System.out.println("Boa Sorte!");
		System.out.println("--------------------------");
		for (Integer[] aposta : prognosticos) {
			StringUtils.print(aposta);
		}

		int ultimoSorteio = getResultadoDAO().buscarNroUltimoSorteioGravado(TiposSorteio.MEGASENA.sigla);

		System.out.println("------------------------------------------------");
		System.out.println("Ultimas sorteadas");
		System.out.println("------------------------------------------------");
		List<Integer> ultimasDezenas = getResultadoDAO().buscarDezenasSorteadas(ultimoSorteio, TiposSorteio.MEGASENA.sigla);
		Collections.sort(ultimasDezenas);
		System.out.println(ultimasDezenas);

		System.out.println("------------------------------------------------");
		System.out.println("Atrasos (entre 8 e 9 sorteios de atraso)");
		System.out.println("------------------------------------------------");
		List<Integer> atrasos = getResultadoDAO().buscarDezenasEmAtraso(TiposSorteio.MEGASENA.sigla, 8);
		Collections.sort(atrasos);
		System.out.println(atrasos);
	}

	/**
	 * 
	 * @param quantidade
	 * @return
	 */
	public static List<Integer[]> prognosticos(Integer quantidade, Integer qtDezenas) {
		List<Integer[]> result = new ArrayList<Integer[]>();

		while (result.size() < quantidade) {

			for (int i = 0; i < quantidade; i++) {
				result.add(gerarAposta(2, 2, 2, qtDezenas));
				result.add(gerarAposta(2, 1, 3, qtDezenas));
			}

			for (Regra regra : regras) {
				regra.aplicar(result);
			}
		}

		return result.subList(0, quantidade);
	}

	public static Integer[] gerarAposta(int qtDezenas) {
		return gerarAposta(2, 2, 2, qtDezenas);
	}

	/**
	 * 
	 * @return
	 */
	public static Integer[] gerarAposta(int a, int b, int c, int qtDezenas) {

		List<Integer> tmp = new ArrayList<Integer>();

		int p1 = 0;
		while (tmp.size() < a) {
			p1 = RndUtils.nextInt(1, 21);
			if (!tmp.contains(p1)) {
				tmp.add(p1);
			}
		}

		while (tmp.size() < (a + b)) {
			p1 = RndUtils.nextInt(21, 40);
			if (!tmp.contains(p1)) {
				tmp.add(p1);
			}
		}

		while (tmp.size() < (a + b + c)) {
			p1 = RndUtils.nextInt(41, 60);
			if (!tmp.contains(p1)) {
				tmp.add(p1);
			}
		}

		if (qtDezenas > 6) {
			while (tmp.size() < qtDezenas) {
				p1 = RndUtils.nextInt(1, 60);
				if (!tmp.contains(p1)) {
					tmp.add(p1);
				}
			}
		}

		Integer[] result = new Integer[qtDezenas];
		tmp.subList(0, qtDezenas).toArray(result);

		Arrays.sort(result);

		return result;
	}

	private static ResultadoDAO getResultadoDAO() {
		if (resultadoDAO == null) {
			resultadoDAO = new ResultadoBaseDAOImpl();
		}
		return resultadoDAO;
	}
}
