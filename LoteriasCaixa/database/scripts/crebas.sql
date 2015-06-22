-- drop table if exists tb_concursos;
-- 
-- create table tb_concursos(
--   nu_concurso  integer     not null,
--   tp_concurso  char(2)     not null,
--   dt_concurso  char(10)    not null,
--   primary key (nu_concurso, tp_concurso)
-- );
-- 
-- drop table if exists tb_sorteios;
-- 
-- create table tb_sorteios(
--   nu_sorteio   integer     not null,
--   nu_concurso  integer     not null,
--   tp_concurso  char(2)     not null,
--   hash         char(50)    not null,
--   primary key (nu_sorteio, nu_concurso, tp_concurso)
-- );
-- 
-- drop table if exists tb_dezenas;
-- 
-- create table tb_dezenas(
--   nu_sorteio   integer     not null,
--   nu_concurso  integer     not null,
--   tp_concurso  char(2)     not null,
--   nu_dezena    integer     not null,
--   nu_posicao   integer     not null,
--   primary key (nu_sorteio, nu_concurso, tp_concurso, nu_dezena)
-- );

-- drop table if exists tb_atrasos;
-- 
-- create table tb_atrasos(
--   nu_sorteio   integer     not null,
--   nu_concurso  integer     not null,
--   tp_concurso  char(2)     not null,
--   nu_dezena    integer     not null,
--   qt_atraso    integer     not null default 1,
--   ic_calculado char(1)     not null default 'N', 
--   primary key (nu_sorteio, nu_concurso, tp_concurso, nu_dezena)
-- );

drop table if exists tb_atrasos;

create table tb_atrasos(
  nu_sorteio     integer     not null,
  tp_concurso    char(2)     not null,
  nu_concurso_1  integer     not null,
  nu_concurso_2  integer     not null,
  qt_concursos   integer     not null,
  qt_dezenas     integer     not null,
  primary key (nu_sorteio, tp_concurso, nu_concurso_1, nu_concurso_2)
);