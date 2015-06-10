package org.ganimede;

import java.io.Serializable;

public class Atraso implements Serializable {

    private static final long serialVersionUID = 1L;

    private int nuSorteio;
    private int nuConcurso;
    private String tpConcurso;
    private int nuDezena;
    private int qtAtraso;
    private String icCalculado;

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
     * @return the nuDezena
     */
    public int getNuDezena() {
        return nuDezena;
    }

    /**
     * @param nuDezena
     *            the nuDezena to set
     */
    public void setNuDezena(int nuDezena) {
        this.nuDezena = nuDezena;
    }

    /**
     * @return the qtAtraso
     */
    public int getQtAtraso() {
        return qtAtraso;
    }

    /**
     * @param qtAtraso
     *            the qtAtraso to set
     */
    public void setQtAtraso(int qtAtraso) {
        this.qtAtraso = qtAtraso;
    }

    /**
     * @return the icCalculado
     */
    public String getIcCalculado() {
        return icCalculado;
    }

    /**
     * @param icCalculado
     *            the icCalculado to set
     */
    public void setIcCalculado(String icCalculado) {
        this.icCalculado = icCalculado;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Atraso [nuSorteio=" + nuSorteio + ", nuConcurso=" + nuConcurso + ", tpConcurso=" + tpConcurso
                + ", nuDezena=" + nuDezena + ", qtAtraso=" + qtAtraso + ", icCalculado=" + icCalculado + "]";
    }

}
