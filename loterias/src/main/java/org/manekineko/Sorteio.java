/**
 * 
 */
package org.manekineko;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author josen
 * 
 */
public class Sorteio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String tpSorteio;

	/**
	 * 
	 */
	private Integer nuSorteio;

	/**
	 * 
	 */
	private Date dtSorteio;

	/**
	 * 
	 */
	private List<Integer> dezenas;

	/**
	 * 
	 * @param tpSorteio
	 */
	public Sorteio(String tpSorteio) {
		this.tpSorteio = tpSorteio;
	}

	/**
	 * @return the nuSorteio
	 */
	public Integer getNuSorteio() {
		return nuSorteio;
	}

	/**
	 * @param nuSorteio
	 *            the nuSorteio to set
	 */
	public void setNuSorteio(Integer nuSorteio) {
		this.nuSorteio = nuSorteio;
	}

	/**
	 * @return the dtSorteio
	 */
	public Date getDtSorteio() {
		return dtSorteio;
	}

	/**
	 * @param dtSorteio
	 *            the dtSorteio to set
	 */
	public void setDtSorteio(Date dtSorteio) {
		this.dtSorteio = dtSorteio;
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

	/**
	 * @return the tpSorteio
	 */
	public String getTpSorteio() {
		return tpSorteio;
	}

	/**
	 * @param tpSorteio
	 *            the tpSorteio to set
	 */
	public void setTpSorteio(String tpSorteio) {
		this.tpSorteio = tpSorteio;
	}

	@Override
	public String toString() {
		return "Sorteio [tpSorteio=" + tpSorteio + ", nuSorteio=" + nuSorteio
				+ ", dtSorteio="
				+ (new SimpleDateFormat("dd/mm/yyyy")).format(dtSorteio)
				+ ", dezenas=" + dezenas + "]";
	}

}
