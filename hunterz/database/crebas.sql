-- create schema hunterz;

drop sequence hunterz.sq01_series;
drop sequence hunterz.sq02_torrents;

create sequence hunterz.sq01_series start with 1;
create sequence hunterz.sq02_torrents start with 1;

drop table hunterz.tb01_series;

create table hunterz.tb01_series (
  id_serie   int          not null,
  no_serie   varchar(100) not null,
  co_serie   varchar(100) not null,
  sg_ativa   char(1)      not null default 'S',
  primary key(id_serie)
);

drop table hunterz.tb02_torrents;

create table hunterz.tb02_torrents (
  id_torrent        int           not null,
  co_search_engine  varchar(5)    not null,
  id_serie          int           not null,
  de_title          varchar(1000) not null,
  de_magnet_link    varchar(1000) not null,
  nu_size           varchar(30)   not null,
  dt_released       varchar(30)   not null,
  qt_seeds          varchar(30)   not null default '0',
  qt_leechers       varchar(30)   not null default '0',
  primary key(id_torrent)
);

drop table hunterz.tb03_search_engines;

create table hunterz.tb03_search_engines (
  co_search_engine  varchar(5)    not null,
  no_search_engine  varchar(100)  not null,
  de_url            varchar(1000) not null,
  sg_ativa          char(1)       not null default 'S',
  primary key(co_search_engine)
);

insert into hunterz.tb03_search_engines values('PBY','The Pirate Bay', 'https://thepiratebay.org/search/dsadasd/0/99/0','S');
insert into hunterz.tb03_search_engines values('EZTV','eztv.ag', 'https://eztv.ag/search/', 'S');

insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Westworld', 'westworld', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Night Of','the-night-of', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Frontier','frontier', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Empire','empire-2015', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Walking Dead','the-walking-dead', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Van Helsing','van-helsing', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Chicago PD','chicago-pd', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Chicago Fire','chicago-fire', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Chicago Med','chicago-med', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Impastor','impastor', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Exorcist','the-exorcist', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Fall','the-fall', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Flash','the-flash-2014', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Blacklist','blacklist', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Lucifer','lucifer', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Outcast','outcast', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Vampire Diaries','the-vampire-diaries', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Lethal Weapon','lethal-weapon', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Gotham','gotham', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Travelers','travelers', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Legends of Tomorrow','legends-of-tomorrow', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Expanse','the-expanse', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Colony','colony', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Bosch','bosch', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Berlin Station','berlin-station', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Shooter','shooter', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Chance','chance', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Vikings','vikings', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'The Night Manager','the-night-manager', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Incorporated','incorporated', 'S');
insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Poldark','poldark', 'S');
