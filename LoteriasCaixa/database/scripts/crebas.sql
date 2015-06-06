-- CREATE USER daikoku WITH PASSWORD 'daikoku';
-- GRANT ALL PRIVILEGES ON DATABASE daikoku to daikoku;

drop table if exists tb_concursos;

create table tb_concursos(
  nu_concurso  integer     not null,
  tp_concurso  char(2)     not null,
  dt_concurso  char(10)    not null,
  primary key (nu_concurso, tp_concurso, dt_concurso)
);

drop table if exists tb_sorteios;

create table tb_sorteios(
  nu_sorteio   integer     not null,
  nu_concurso  integer     not null,
  tp_concurso  char(2)     not null,
  hash         char(33)    not null,
  primary key (nu_sorteio, nu_concurso)
);

drop table if exists tb_dezenas;

create table tb_dezenas(
  nu_sorteio   integer     not null,
  nu_concurso  integer     not null,
  tp_concurso  char(2)     not null,
  nu_dezena    integer     not null,
  nu_posicao   integer     not null,
  primary key (nu_sorteio, nu_concurso, tp_concurso, nu_dezena)
);

drop table if exists tb_atrasos;

create table tb_atrasos(
  nu_sorteio   integer     not null,
  nu_concurso  integer     not null,
  tp_sorteio   char(2)     not null,
  nu_dezena    integer     not null,
  qt_atraso    integer     not null,
  primary key (nu_sorteio, nu_concurso, tp_sorteio, nu_dezena)
);