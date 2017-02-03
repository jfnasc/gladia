package org.avalon.hunterz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EngineInfoDTO implements Serializable, Comparable<EngineInfoDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private List<SeriesDTO> listaSeries;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the listaSeries
	 */
	public List<SeriesDTO> getListaSeries() {
		if (listaSeries == null) {
			listaSeries = new ArrayList<>();
		}
		return listaSeries;
	}

	/**
	 * @param listaSeries
	 *            the listaSeries to set
	 */
	public void setListaSeries(List<SeriesDTO> listaSeries) {
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
	public int compareTo(EngineInfoDTO o) {
		return getName().compareTo(o.getName());
	}

}
