<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Pages extends CI_Controller
{

    public function __construct ()
    {
        parent::__construct();
        
        $this->load->model('M_Series');
        $this->load->model('M_Search_Engine');
        
        $this->load->library('TorrentDTO');
        $this->load->library('Search_Engine_DTO');
    }

    public function index ()
    {
        $this->view('home');
    }

    public function show_menu ()
    {
        $data['lista_engines'] = $this->M_Search_Engine->listar_engines_ativos();

        $this->load->view('templates/menu', $data);
    }

    public function find_by_search_engine ($co_search_engine)
    {
        $data['title'] = $co_search_engine; // Capitalize the first letter
                                            
        // dados
        $data['listaTorrents'] = $this->M_Series->find_by_search_engine($co_search_engine);
        
        $this->load->view('templates/header', $data);
        $this->load->view('templates/listar_torrents', $data);
        $this->load->view('templates/footer', $data);
    }

    public function list_new ()
    {
        $data['title'] = "Mais recentes"; // Capitalize the first letter
                                          
        // dados
        $data['listaTorrents'] = $this->M_Series->listar_mais_recentes(10);
        
        $this->load->view('templates/header', $data);
        $this->load->view('templates/listar_torrents', $data);
        $this->load->view('templates/footer', $data);
    }
}
