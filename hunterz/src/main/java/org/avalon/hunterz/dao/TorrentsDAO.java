package org.avalon.hunterz.dao;

import org.avalon.hunterz.SeriesDTO;
import org.avalon.hunterz.model.TorrentInfo;

public interface TorrentsDAO {

    /**
     * 
     * @param lista
     * @throws Exception
     */
    void salvar(SeriesDTO dto) throws Exception;
    
    /**
     * 
     * @param magnetLink
     * @return
     * @throws Exception
     */
    TorrentInfo findByUrl(String magnetLink) throws Exception;

}
