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
	 * @param p
	 */
	abstract void aplicar(List<Integer[]> p);
}
