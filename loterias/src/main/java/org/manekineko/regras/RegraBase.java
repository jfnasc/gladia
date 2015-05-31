/**
 * 
 */
package org.manekineko.regras;

import org.manekineko.dao.ResultadoDAO;
import org.manekineko.dao.impl.ResultadoBaseDAOImpl;

/**
 * @author josen
 * 
 */
public abstract class RegraBase {

	/**
	 * 
	 */
	private ResultadoDAO resultadoDAO;

	/**
	 * 
	 * @return
	 */
	protected ResultadoDAO getResultadoDAO() {
		if (this.resultadoDAO == null) {
			this.resultadoDAO = new ResultadoBaseDAOImpl();
		}
		return this.resultadoDAO;
	}

}
