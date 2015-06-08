/**
 * 
 */
package org.ganimede;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jose Flavio <jfnasc@outlook.com>
 * 
 */
public class Concurso implements Serializable {

    private static final long serialVersionUID = 1L;

    private int nuConcurso;

    private String tpConcurso;

    private Date dtConcurso;

    private List<Sorteio> sorteios = new ArrayList<>();

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
     * @return the dtConcurso
     */
    public Date getDtConcurso() {
        return dtConcurso;
    }

    /**
     * @param dtConcurso
     *            the dtConcurso to set
     */
    public void setDtConcurso(Date dtConcurso) {
        this.dtConcurso = dtConcurso;
    }

    /**
     * 
     * @param sorteio
     */
    public void addSorteio(Sorteio sorteio) {
        this.sorteios.add(sorteio);
    }

    /**
     * @return the sorteios
     */
    public List<Sorteio> getSorteios() {
        return sorteios;
    }

    /**
     * @param sorteios
     *            the sorteios to set
     */
    public void setSorteios(List<Sorteio> sorteios) {
        this.sorteios = sorteios;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Concurso [nuConcurso=" + nuConcurso + ", tpConcurso=" + tpConcurso + ", dtConcurso=" + dtConcurso + "]";
    }

}
