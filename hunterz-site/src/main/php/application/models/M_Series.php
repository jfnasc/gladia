<?php
defined('BASEPATH') or exit('No direct script access allowed');

class M_Series extends CI_Model
{

    public function __construct ()
    {
        $this->load->database();
    }

    public function find_by_search_engine ($co_search_engine)
    {
        $result = [];
        
        $sql = " select id_torrent, co_search_engine, de_title, de_magnet_link,";
        $sql .= "       nu_size, dt_released, qt_seeds, qt_leechers,";
        $sql .= "       tb01.id_serie, tb01.no_serie, tb01.co_serie, tb01.sg_ativa";
        $sql .= "  from hunterz.tb02_torrents tb02,";
        $sql .= "       hunterz.tb01_series tb01";
        $sql .= " where tb02.id_serie = tb01.id_serie";
        $sql .= "   and upper(co_search_engine) = upper(?);";
        
        $query = $this->db->query($sql, $co_search_engine);
        
        foreach ($query->result() as $row) {
            $result[] = $this->map_row($row);
        }
        
        return $result;
    }
    
    public function listar_mais_recentes ($qt_resultados)
    {
        $result = [];
        
        $sql = " select tb01.id_serie as id_serie, no_serie, co_serie, rank, id_torrent, de_title,";
        $sql .= "       de_magnet_link, nu_size, dt_released, qt_seeds, qt_leechers, co_search_engine";
        $sql .= "  from hunterz.tb01_series tb01,";
        $sql .= "       ( select rank() OVER (PARTITION BY id_serie order by de_title desc) rank, ";
        $sql .= "                id_torrent, id_serie, de_title, de_magnet_link, nu_size,";
        $sql .= "                dt_released, qt_seeds, qt_leechers, co_search_engine";
        $sql .= "            from hunterz.tb02_torrents";
        $sql .= "       ) tb02";
        $sql .= " where tb01.id_serie = tb02.id_serie";
        $sql .= "   and tb02.rank <= " . $qt_resultados;
        $sql .= "  order by upper(tb01.no_serie), upper(de_title) desc";
        
        $query = $this->db->query($sql);
        
        foreach ($query->result() as $row) {
            $result[] = $this->map_row($row);
        }
        
        return $result;
    }

    private function map_row ($row)
    {
        $dto = new TorrentDTO();
        
        $dto->set_id_serie($row->id_serie);
        $dto->set_co_search_engine($row->co_search_engine);
        $dto->set_no_serie($row->no_serie);
        $dto->set_co_serie($row->co_serie);
        $dto->set_id_torrent($row->id_torrent);
        $dto->set_de_title($row->de_title);
        $dto->set_de_magnet_link($row->de_magnet_link);
        $dto->set_nu_size($row->nu_size);
        $dto->set_dt_released($row->dt_released);
        $dto->set_qt_seeds($row->qt_seeds);
        $dto->set_qt_leechers($row->qt_leechers);
        
        return $dto;
    }
}
?>
