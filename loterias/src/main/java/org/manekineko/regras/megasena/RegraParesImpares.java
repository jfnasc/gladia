/**
 * 
 */
package org.manekineko.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.manekineko.regras.Regra;
import org.manekineko.regras.RegraBase;

/**
 * @author josen
 * 
 */
public class RegraParesImpares extends RegraBase implements Regra {

	/*
	 * (non-Javadoc)
	 * 
	 * @see daikoku.Regra#aplicar(java.util.List)
	 */
	@Override
	public void aplicar(List<Integer[]> p) {

		if (p == null || p.size() == 0){
			return;
		}
		
		float total = Float.valueOf(p.size());

		List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

		for (int i = 0; i < p.size(); i++) {
			int countPares = 0;
			// System.out.println(prognosticos.get(i));
			for (int k = 0; k < p.get(i).length; k++) {
				if ((p.get(i)[k] % 2) == 0) {
					++countPares;
				}
			}

			if (countPares < 1 || countPares > 5) {
				toRemoveList.add(p.get(i));
			}
		}

		p.removeAll(toRemoveList);

		System.out.println("RegraParesImpares: " + (Float.valueOf(toRemoveList.size()) / total * 100));
	}
}
