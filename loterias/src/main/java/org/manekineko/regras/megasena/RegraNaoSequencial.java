/**
 * 
 */
package org.manekineko.regras.megasena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.manekineko.regras.Regra;
import org.manekineko.regras.RegraBase;

/**
 * @author josen
 * 
 */
public class RegraNaoSequencial extends RegraBase implements Regra {

	private Logger LOGGER = Logger.getLogger(RegraNaoSequencial.class);
	
	public void aplicar(List<Integer[]> p) {

		if (p == null || p.isEmpty()){
			return;
		}
		
		float total = Float.valueOf(p.size());
		
		List<Integer[]> toRemoveList = new ArrayList<Integer[]>();

		for (int i = 0; i < p.size(); i++) {

			Arrays.sort(p.get(i));

			for (int k = 0; k < p.get(i).length; k++) {
				if ((k + 1) < p.get(i).length) {
					if ((p.get(i)[k] + 1) == (p.get(i)[k + 1])) {
						toRemoveList.add(p.get(i));
						break;
					}
				}
			}
		}

		p.removeAll(toRemoveList);

		LOGGER.debug("RegraNaoSequencial: "
				+ (Float.valueOf(toRemoveList.size()) / total * 100));
	}
}
