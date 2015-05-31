package org.manekineko.analysis;

import java.util.List;

import org.manekineko.TiposSorteio;

public class SorteioConsecutivo extends Analise {
	public static void main(String[] args) {
		SorteioConsecutivo p = new SorteioConsecutivo();
		p.executar();
	}

	public void executar() {
		List<Integer> atrasos = getResultadoDAO().listarAtrasos(TiposSorteio.MEGASENA.sigla, 1);
		for (Integer atraso : atrasos) {
			System.out.println(atraso);
		}
	}
}
