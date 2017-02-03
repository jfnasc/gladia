create schema hunterz;

drop sequence hunterz.sq01_series;
drop sequence hunterz.sq02_torrents;

create sequence hunterz.sq01_series start with 1;
create sequence hunterz.sq02_torrents start with 1;

drop table hunterz.tb01_series;

create table hunterz.tb01_series (
  id_serie   int          not null,
  no_serie   varchar(100) not null,
  co_serie   varchar(100) not null,
  primary key(id_serie)
);

drop table hunterz.tb02_torrents;

create table hunterz.tb02_torrents (
  id_torrent   int           not null,
  id_serie     int           not null,
  de_title     varchar(1000) not null,
  de_link      varchar(1000) not null,
  nu_size      varchar(10)   not null,
  dt_released  varchar(10)   not null,
  qt_seeds     varchar(10)   not null default '0',
  qt_leechers  varchar(10)   not null default '0',
  primary key(id_torrent)
);

insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Westworld', 'westworld');
--select * from hunterz.tb01_series;
