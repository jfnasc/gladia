create schema hunterz;

create sequence hunterz.sq01_series start with 1;

drop table hunterz.tb01_series;

create table hunterz.tb01_series (
  id_serie   int          not null,
  no_serie   varchar(100) not null,
  co_serie   varchar(100) not null,
  primary key(id_serie)
);

insert into hunterz.tb01_series values(nextval('hunterz.sq01_series'), 'Westworld', 'westworld');

select * from hunterz.tb01_series;