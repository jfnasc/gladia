package org.avalon.hunterz.dao;

import java.util.List;

import org.avalon.hunterz.model.Serie;

public interface SeriesDAO {
	/**
	 * lista a series a serem pesquisadas
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Serie> listar() throws Exception;
}
