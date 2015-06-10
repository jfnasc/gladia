select nu_dezena,
       count(nu_concurso)
  from tb_dezenas
 where tp_concurso = 'MS' 
 group by nu_dezena
 order by count(nu_concurso) desc;

 

select count(nu_concurso),
       sum(case when nu_dezena > 0 then 1 else 0 end ),
       sum(case when nu_dezena > 50 then 1 else 0 end ),
       (sum(case when nu_dezena > 50 then 1 else 0 end ) / sum(case when nu_dezena > 0 then 1 else 0 end )) media
  from tb_dezenas
 where tp_concurso = 'MS';

 

select count(nu_concurso),
       sum(case when nu_dezena > 0 then 1 else 0 end ),
       CAST((sum(case when nu_dezena > 50 then 1 else 0 end )) as double precision) / CAST(sum(case when nu_dezena > 0 then 1 else 0 end ) as double precision) *100 media,
       CAST((sum(case when nu_dezena > 55 then 1 else 0 end )) as double precision) / CAST(sum(case when nu_dezena > 0 then 1 else 0 end ) as double precision) *100 media
  from tb_dezenas
 where tp_concurso = 'MS';