<?php
defined('BASEPATH') or exit('No direct script access allowed');

class M_Search_Engine extends CI_Model
{

    public function __construct ()
    {
        $this->load->database();
    }

    public function listar_engines_ativos ()
    {
        $result = [];
        
        $sql = " select co_search_engine, no_search_engine, de_url, sg_ativa";
        $sql .= "  from hunterz.tb03_search_engines";
        $sql .= " where upper(sg_ativa) = upper('S')";
        
        $query = $this->db->query($sql);
        
        foreach ($query->result() as $row) {
            $result[] = $this->map_row($row);
        }
        
        return $result;
    }

    private function map_row ($row)
    {
        $dto = new Search_Engine_DTO();
        
        $dto->set_co_search_engine($row->co_search_engine);
        $dto->set_no_search_engine($row->no_search_engine);
        $dto->set_de_url($row->de_url);
        $dto->set_sg_ativa($row->sg_ativa);
        
        return $dto;
    }
}
?>
