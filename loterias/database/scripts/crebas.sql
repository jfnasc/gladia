drop SCHEMA if exists loterias cascade;
create SCHEMA loterias;


drop table if exists loterias.tb_concursos;

create table loterias.tb_concursos(
  nu_concurso  integer     not null,
  tp_concurso  char(2)     not null,
  dt_concurso  char(10)    not null,
  primary key (nu_concurso, tp_concurso)
);

drop table if exists loterias.tb_sorteios;

create table loterias.tb_sorteios(
  nu_sorteio   integer     not null,
  nu_concurso  integer     not null,
  tp_concurso  char(2)     not null,
  hash         char(50)    not null,
  primary key (nu_sorteio, nu_concurso, tp_concurso)
);

drop table if exists loterias.tb_dezenas;

create table loterias.tb_dezenas(
  nu_sorteio   integer     not null,
  nu_concurso  integer     not null,
  tp_concurso  char(2)     not null,
  nu_dezena    integer     not null,
  nu_posicao   integer     not null,
  primary key (nu_sorteio, nu_concurso, tp_concurso, nu_dezena)
);

drop table if exists loterias.tb_atrasos;

create table loterias.tb_atrasos(
  nu_sorteio   integer     not null,
  nu_concurso  integer     not null,
  tp_concurso  char(2)     not null,
  nu_dezena    integer     not null,
  qt_atraso    integer     not null default 1,
  ic_calculado char(1)     not null default 'N', 
  primary key (nu_sorteio, nu_concurso, tp_concurso, nu_dezena)
);