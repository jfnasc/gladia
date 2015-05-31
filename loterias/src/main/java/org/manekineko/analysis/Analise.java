package org.manekineko.analysis;

import java.util.List;

import org.manekineko.Sorteio;
import org.manekineko.dao.ResultadoDAO;
import org.manekineko.dao.impl.ResultadoBaseDAOImpl;

abstract class Analise {
	ResultadoDAO resultadoDAO = null;

	abstract void executar();

	/**
	 * 
	 * @return
	 */
	protected ResultadoDAO getResultadoDAO() {
		if (resultadoDAO == null) {
			resultadoDAO = new ResultadoBaseDAOImpl();
		}
		return resultadoDAO;
	}
}
