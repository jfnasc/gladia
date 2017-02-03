package org.avalon.hunterz.model;

import java.io.Serializable;

public class Serie implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * 
	 */
    private Integer codigo;

    /**
	 * 
	 */
    private String nome;

    /**
	 * 
	 */
    private String codigoBusca;

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the codigoBusca
     */
    public String getCodigoBusca() {
        return codigoBusca;
    }

    /**
     * @param codigoBusca
     *            the codigoBusca to set
     */
    public void setCodigoBusca(String codigoBusca) {
        this.codigoBusca = codigoBusca;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Serie [codigo=" + codigo + ", nome=" + nome + ", codigoBusca=" + codigoBusca + "]";
    }

}
