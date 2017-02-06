<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');

class TorrentDTO
{

    private $no_serie = null;

    private $co_serie = null;

    private $id_torrent = null;

    private $co_search_engine = null;

    private $id_serie = null;

    private $de_title = null;

    private $de_magnet_link = null;

    private $nu_size = null;

    private $dt_released = null;

    private $qt_seeds = null;

    private $qt_leechers = null;

    public function get_no_serie ()
    {
        return $this->no_serie;
    }

    public function set_no_serie ($no_serie)
    {
        $this->no_serie = $no_serie;
    }

    public function get_co_serie ()
    {
        return $this->co_serie;
    }

    public function set_co_serie ($co_serie)
    {
        $this->co_serie = $co_serie;
    }

    public function get_id_torrent ()
    {
        return $this->id_torrent;
    }

    public function set_id_torrent ($id_torrent)
    {
        $this->id_torrent = $id_torrent;
    }

    public function get_co_search_engine ()
    {
        return $this->co_search_engine;
    }

    public function set_co_search_engine ($co_search_engine)
    {
        $this->co_search_engine = $co_search_engine;
    }

    public function get_id_serie ()
    {
        return $this->id_serie;
    }

    public function set_id_serie ($id_serie)
    {
        $this->id_serie = $id_serie;
    }

    public function get_de_title ()
    {
        return $this->de_title;
    }

    public function set_de_title ($de_title)
    {
        $this->de_title = $de_title;
    }

    public function get_de_magnet_link ()
    {
        return $this->de_magnet_link;
    }

    public function set_de_magnet_link ($de_magnet_link)
    {
        $this->de_magnet_link = $de_magnet_link;
    }

    public function get_nu_size ()
    {
        return $this->nu_size;
    }

    public function set_nu_size ($nu_size)
    {
        $this->nu_size = $nu_size;
    }

    public function get_dt_released ()
    {
        return $this->dt_released;
    }

    public function set_dt_released ($dt_released)
    {
        $this->dt_released = $dt_released;
    }

    public function get_qt_seeds ()
    {
        return $this->qt_seeds;
    }

    public function set_qt_seeds ($qt_seeds)
    {
        $this->qt_seeds = $qt_seeds;
    }

    public function get_qt_leechers ()
    {
        return $this->qt_leechers;
    }

    public function set_qt_leechers ($qt_leechers)
    {
        $this->qt_leechers = $qt_leechers;
    }
}
?>