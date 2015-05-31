-- CREATE USER daikoku WITH PASSWORD 'daikoku';
-- GRANT ALL PRIVILEGES ON DATABASE daikoku to daikoku;

drop table if exists tb_sorteios;

create table tb_sorteios(
  nu_sorteio  integer,
  tp_sorteio  char(2),
  dt_sorteio  char(10),
  hash        char(33),
  primary key (nu_sorteio, tp_sorteio, dt_sorteio)
);

drop table if exists tb_dezenas;

create table tb_dezenas(
  nu_sorteio  integer,
  tp_sorteio  char(2),
  nu_dezena   integer,
  nu_posicao  integer,
  primary key (nu_sorteio, tp_sorteio, nu_dezena)
);

drop table if exists tb_atrasos;

create table tb_atrasos(
  nu_sorteio  integer,
  tp_sorteio  char(2),
  nu_dezena   integer,
  qt_atraso   integer,
  primary key (nu_sorteio, tp_sorteio, nu_dezena)
);