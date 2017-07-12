/**
 * 
 */
package org.ganimede.regras;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.ResultadoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;
import org.ganimede.dao.impl.ResultadoDAOImpl;
import org.ganimede.regras.impl.RegraAtraso;

/**
 * @author josen
 * 
 */
public abstract class RegraBase implements Regra{

    private ResultadoDAO resultadoDAO;
    private ConcursoDAO concursoDAO;

    @Override
    public void aplicar(List<Integer[]> apostas) {
        if (apostas == null || apostas.size() == 0) {
            return;
        }

        float total = Float.valueOf(apostas.size());
        
        try {

            List<Integer[]> aRemover = new ArrayList<Integer[]>();

            // senas
            for (Integer[] aposta : apostas) {
                if (!validar(aposta)){
                    aRemover.add(aposta);
                }
            }

            apostas.removeAll(aRemover);

            getLogger().debug((Float.valueOf(aRemover.size()) / total * 100));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public abstract boolean validar(Integer[] aposta);
    
    protected Logger getLogger(){
        return Logger.getLogger(getClass());
    }
    
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
