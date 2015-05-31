package org.manekineko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.manekineko.dao.ResultadoDAO;
import org.manekineko.dao.impl.ResultadoBaseDAOImpl;

public class GerarCombinacoesLF {

	static ResultadoDAO resultadoDAO = null;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<List<Integer>> apostas = gerarAposta(5);
		for (List<Integer> aposta : apostas) {
			System.out.println(aposta);
		}
	}

	public static List<List<Integer>> gerarAposta(int qtApostas){
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < qtApostas; i++) {
			result.add(gerarAposta());
		}
		
		return result;
	}
	
	public static List<Integer> gerarAposta(){
		List<Integer> result = new ArrayList<Integer>();
		
		List<Integer> ds = new ArrayList<Integer>();
		for (int i = 0; i < 15; i++) {
			ds.add(i);
		}
		
		Collections.shuffle(ds, new Random(System.currentTimeMillis()));
				
		List<Integer> ultimoSorteio = getResultadoDAO().buscarUltimoResultado("LF");
		for (int i : ds.subList(0, 10)) {
			result.add(ultimoSorteio.get(i));
		}
		
		List<Integer> naoSorteados = new ArrayList<Integer>();
		for (int i = 1; i <= 25; i++) {
			naoSorteados.add(i);
		}
		
		naoSorteados.removeAll(ultimoSorteio);
		Collections.shuffle(naoSorteados, new Random(System.currentTimeMillis()));
		result.addAll(naoSorteados.subList(0, 5));

		Collections.sort(result);
		
		return result;
	}
	/**
	 * 
	 * @return
	 */
	private static ResultadoDAO getResultadoDAO() {
		if (resultadoDAO == null) {
			resultadoDAO = new ResultadoBaseDAOImpl();
		}
		return resultadoDAO;
	}
}
