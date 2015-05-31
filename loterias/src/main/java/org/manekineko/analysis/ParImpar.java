package org.manekineko.analysis;

import java.util.List;

import org.manekineko.Sorteio;
import org.manekineko.TiposSorteio;

public class ParImpar extends Analise {
	public static void main(String[] args) {
		ParImpar p = new ParImpar();
		p.executar();
	}

	public void executar() {
		List<Sorteio> sorteios = getResultadoDAO().listarSorteios(TiposSorteio.MEGASENA.sigla);

		// media de sorteios / numeros pares
		int mediaPares = 0;
		for (Sorteio sorteio : sorteios) {
			mediaPares += contarPares(sorteio.getDezenas());
		}
		System.out.println(mediaPares / sorteios.size());

		for (Sorteio sorteio : sorteios) {
			System.out.println(contarPares(sorteio.getDezenas()));
		}
	}

	private int contarPares(List<Integer> dezenas) {
		int result = 0;
		for (Integer dezena : dezenas) {
			if (dezena % 2 == 0) {
				result++;
			}
		}
		return result;
	}
}
