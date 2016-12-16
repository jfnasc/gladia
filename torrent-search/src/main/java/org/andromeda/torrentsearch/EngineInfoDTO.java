package org.andromeda.torrentsearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EngineInfoDTO implements Serializable, Comparable<SerieInfoDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private List<SerieInfoDTO> listaSeries;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the listaSeries
	 */
	public List<SerieInfoDTO> getListaSeries() {
		if (listaSeries == null) {
			listaSeries = new ArrayList<>();
		}
		return listaSeries;
	}

	/**
	 * @param listaSeries
	 *            the listaSeries to set
	 */
	public void setListaSeries(List<SerieInfoDTO> listaSeries) {
		this.listaSeries = listaSeries;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(SerieInfoDTO o) {
		return getName().compareTo(o.getName());
	}

}
