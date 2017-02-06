<?php
if (! defined('BASEPATH')) exit('No direct script access allowed');

class Serie_DTO
{
  private $id_serie = null;
  private $no_serie = null;
  private $co_serie = null;
  private $sg_ativo = null;

  public function get_id_serie ()
  {
      return $this->id_serie;
  }

  public function set_id_serie ($id_serie)
  {
      $this->id_serie = $id_serie;
  }

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