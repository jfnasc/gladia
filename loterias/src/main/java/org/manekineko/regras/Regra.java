package org.manekineko.regras;

import java.util.List;

/**
 * 
 * @author josen
 * 
 */
public interface Regra {

	/**
	 * 
	 * @param prognosticos
	 */
	abstract void aplicar(List<Integer[]> prognosticos);
}
