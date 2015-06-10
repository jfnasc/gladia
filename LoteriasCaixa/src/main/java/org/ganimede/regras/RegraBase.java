/**
 * 
 */
package org.ganimede.regras;

import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.ResultadoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;
import org.ganimede.dao.impl.ResultadoDAOImpl;

/**
 * @author josen
 * 
 */
public abstract class RegraBase {

    /**
	 * 
	 */
    private ResultadoDAO resultadoDAO;

    private ConcursoDAO concursoDAO;

    /**
     * 
     * @return
     */
    protected ResultadoDAO getResultadoDAO() {
        if (this.resultadoDAO == null) {
            this.resultadoDAO = new ResultadoDAOImpl();
        }
        return this.resultadoDAO;
    }

    /**
     * 
     * @return
     */
    protected ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return this.concursoDAO;
    }

}
