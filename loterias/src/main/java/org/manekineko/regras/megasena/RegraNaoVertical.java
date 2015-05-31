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
public class RegraNaoVertical extends RegraBase implements Regra {

	public void aplicar(List<Integer[]> p) {

		if (p == null || p.size() == 0){
			return;
		}
		
		float total = Float.valueOf(p.size());		
		
		List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

		for (int i = 0; i < p.size(); i++) {
			for (int k = 0; k < p.get(i).length - 1; k++) {
				if ((p.get(i)[k] + 10) == (p.get(i)[k + 1])) {
					toRemoveList.add(p.get(i));
					break;
				}
			}
		}

		p.removeAll(toRemoveList);
		
		System.out.println("RegraNaoVertical: " + (Float.valueOf(toRemoveList.size()) / total * 100));
	}
}
