/**
 * 
 */
package org.ganimede;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public class Sorteio implements Serializable {
    private static final long serialVersionUID = 1L;

    private int nuSorteio;
    private int nuConcurso;
    private String tpConcurso;
    private String hash;
    private List<Integer> dezenas;

    /**
     * @return the nuSorteio
     */
    public int getNuSorteio() {
        return nuSorteio;
    }

    /**
     * @param nuSorteio
     *            the nuSorteio to set
     */
    public void setNuSorteio(int nuSorteio) {
        this.nuSorteio = nuSorteio;
    }

    /**
     * @return the nuConcurso
     */
    public int getNuConcurso() {
        return nuConcurso;
    }

    /**
     * @param nuConcurso
     *            the nuConcurso to set
     */
    public void setNuConcurso(int nuConcurso) {
        this.nuConcurso = nuConcurso;
    }

    /**
     * @return the tpConcurso
     */
    public String getTpConcurso() {
        return tpConcurso;
    }

    /**
     * @param tpConcurso
     *            the tpConcurso to set
     */
    public void setTpConcurso(String tpConcurso) {
        this.tpConcurso = tpConcurso;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash
     *            the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the dezenas
     */
    public List<Integer> getDezenas() {
        return dezenas;
    }

    /**
     * @param dezenas
     *            the dezenas to set
     */
    public void setDezenas(List<Integer> dezenas) {
        this.dezenas = dezenas;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Sorteio [nuSorteio=" + nuSorteio + ", nuConcurso=" + nuConcurso + ", tpConcurso=" + tpConcurso
                        + ", hash=" + hash + "]";
    }

}
