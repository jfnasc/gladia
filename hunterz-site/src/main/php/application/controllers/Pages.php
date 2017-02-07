<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Pages extends CI_Controller
{

    public function __construct ()
    {
        parent::__construct();

        $this->load->helper('url');
        
        $this->load->model('M_Torrents');
        $this->load->model('M_Search_Engine');
        $this->load->model('M_Series');
        
        $this->load->library('TorrentDTO');
        $this->load->library('Search_Engine_DTO');
        $this->load->library('Serie_DTO');
    }

    public function configuracao ()
    {
        $data = [];
        
        $this->load->view('templates/header', $data);
        $this->load->view('configuracao/painel_controle', $data);
        $this->load->view('templates/footer', $data);
    }

    public function resumo ()
    {
        $data['lista_series_ativas'] = $this->M_Series->listar_series_ativas();
        $data['lista_engines_ativos'] = $this->M_Search_Engine->listar_engines_ativos();
    
        $this->load->view('templates/header', $data);
        $this->load->view('configuracao/resumo', $data);
        $this->load->view('templates/footer', $data);
    }
    
    public function config_series ()
    {
        $data['lista_series_ativas'] = $this->M_Series->listar_series_ativas();
    
        $this->load->view('templates/header', $data);
        $this->load->view('configuracao/config_series', $data);
        $this->load->view('templates/footer', $data);
    }
    
    public function config_engines ()
    {
        $data['lista_engines_ativos'] = $this->M_Search_Engine->listar_engines_ativos();
    
        $this->load->view('templates/header', $data);
        $this->load->view('configuracao/config_engines', $data);
        $this->load->view('templates/footer', $data);
    }
    
    public function show_menu ()
    {
        $data['lista_engines'] = $this->M_Search_Engine->listar_engines_ativos();
        
        $this->load->view('menu', $data);
    }

    public function find_by_search_engine ($co_search_engine)
    {
        $data['title'] = $co_search_engine; // Capitalize the first letter
                                            
        // dados
        $data['listaTorrents'] = $this->M_Torrents->find_by_search_engine($co_search_engine);
        
        $this->load->view('templates/header', $data);
        $this->load->view('templates/listar_torrents', $data);
        $this->load->view('templates/footer', $data);
    }

    public function list_new ()
    {
        $data['title'] = "Mais recentes"; // Capitalize the first letter
                                          
        // dados
        $data['listaTorrents'] = $this->M_Torrents->listar_mais_recentes(10);
        
        $this->load->view('templates/header', $data);
        $this->load->view('templates/listar_torrents', $data);
        $this->load->view('templates/footer', $data);
    }
}
