select * from (
	SELECT row_number() over() as rownum, id_torrent, co_search_engine,
	       id_serie, de_title, de_magnet_link,
	       nu_size, dt_released, qt_seeds, qt_leechers
	  FROM hunterz.tb02_torrents
	 order by upper(de_title)
	  ) tb
 where rownum between 21 and 30
 ;
