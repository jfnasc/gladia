<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');

class Search_Engine_DTO
{

    private $co_search_engine = null;

    private $no_search_engine = null;

    private $de_url = null;

    private $sg_ativo = null;

    public function get_co_search_engine ()
    {
        return $this->co_search_engine;
    }

    public function set_co_search_engine ($co_search_engine)
    {
        $this->co_search_engine = $co_search_engine;
    }

    public function get_no_search_engine ()
    {
        return $this->no_search_engine;
    }

    public function set_no_search_engine ($no_search_engine)
    {
        $this->no_search_engine = $no_search_engine;
    }

    public function get_de_url ()
    {
        return $this->de_url;
    }

    public function set_de_url ($de_url)
    {
        $this->de_url = $de_url;
    }

    public function get_sg_ativo ()
    {
        return $this->sg_ativo;
    }

    public function set_sg_ativo ($sg_ativo)
    {
        $this->sg_ativo = $sg_ativo;
    }
}
?>