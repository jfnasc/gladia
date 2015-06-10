package org.ganimede.regras;

import java.util.List;

/**
 * 
 * @author josen
 * 
 */
public interface Regra {

    /**
     * 
     * @param apostas
     */
    abstract void aplicar(List<Integer[]> apostas);
}
