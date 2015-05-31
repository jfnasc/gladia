package org.manekineko.regras.megasena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.manekineko.regras.Regra;
import org.manekineko.regras.RegraBase;

public class RegraSorteiosAnteriores extends RegraBase implements Regra {

	@Override
	public void aplicar(List<Integer[]> p) {
		
		if (p == null || p.size() == 0){
			return;
		}
		
		float total = Float.valueOf(p.size());
		
		try {

			List<Integer[]> aRemover = new ArrayList<Integer[]>();

			// senas
			for (int i = 0; i < p.size(); i++) {
				String hash = getResultadoDAO().calcularHash(Arrays.asList(p.get(i)));
				if (getResultadoDAO().existeSorteioIgual("MS", hash)) {
					aRemover.add(p.get(i));
				}
			}

			p.removeAll(aRemover);

			System.out.println("RegraSorteiosAnteriores: "
					+ (Float.valueOf(aRemover.size()) / total * 100));			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
