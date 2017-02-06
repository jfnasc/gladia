<?php
defined('BASEPATH') or exit('No direct script access allowed');

class M_Series extends CI_Model
{

    public function __construct ()
    {
        $this->load->database();
    }

    public function listar_series_ativas ()
    {
        $result = [];
        
        $sql = " select id_serie, no_serie, co_serie, sg_ativo";
        $sql .= "  from hunterz.tb01_series";
        $sql .= " where upper(sg_ativo) = upper('S')";
        $sql .= " order by upper(no_serie)";
        
        $query = $this->db->query($sql);
        
        foreach ($query->result() as $row) {
            $result[] = $this->map_row($row);
        }
        
        return $result;
    }

    private function map_row ($row)
    {
        $dto = new Serie_DTO();
        
        $dto->set_id_serie($row->id_serie);
        $dto->set_no_serie($row->no_serie);
        $dto->set_co_serie($row->co_serie);
        $dto->set_sg_ativo($row->sg_ativo);
        
        return $dto;
    }
}
?>
