package org.ganimede.regras;

import java.util.List;

/**
 * 
 * @author josen
 * 
 */
public interface Regra {

    /**
     * Aplica a regra especificada para o conjunto de regras informado. As
     * apostas que não atendem a regra serão descartadas.
     * 
     * @param apostas
     */
    abstract void aplicar(List<Integer[]> apostas);
}
