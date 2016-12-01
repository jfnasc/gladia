select qt_atraso 
  from tb_atrasos 
 where nu_concurso = (
		select max(nu_concurso) 
		  from tb_atrasos 
		 where tp_concurso = 'QN' 
		   and nu_sorteio  = 1 
       )
   and nu_dezena = 1
;

		   

select qt_atraso from tb_atrasos where nu_concurso = ();


select max(nu_concurso) * 5
  from tb_dezenas 
 where tp_concurso = 'QN';


select count(nu_concurso),
       cast (sum(case when nu_dezena between  1 and 20 then 1 else 0 end) as float) / cast(count(nu_concurso) as float) * 100 a, 
       cast (sum(case when nu_dezena between 21 and 40 then 1 else 0 end) as float) / cast(count(nu_concurso) as float) * 100 b,
       cast (sum(case when nu_dezena between 41 and 60 then 1 else 0 end) as float) / cast(count(nu_concurso) as float) * 100 c,
       cast (sum(case when nu_dezena between 61 and 80 then 1 else 0 end) as float) / cast(count(nu_concurso) as float) * 100 d
  from tb_dezenas 
 where tp_concurso = 'QN'
   --and nu_concurso = 1
 --group by nu_concurso   
; 


select nu_concurso,
       avg(nu_dezena) as d
  from tb_dezenas 
 where tp_concurso = 'QN'
   and nu_concurso = 1
 group by nu_concurso;



SELECT '20'! AS "factorial";

select nu_concurso, d from (
	select nu_concurso,
	       nu_dezena ! as d
	  from tb_dezenas 
	 where tp_concurso = 'QN'
	   --and nu_concurso = 1
--	 group by nu_concurso
) tb1 
;   


select 
	sum(case when tb1.d between   1 and 100 then 1 else 0 end) a, 
	sum(case when tb1.d between 101 and 200 then 1 else 0 end) b,
	sum(case when tb1.d between 201 and 300 then 1 else 0 end) c ,
	sum(case when tb1.d between 301 and 400 then 1 else 0 end) d
  from (
	select nu_concurso,
	       sum(nu_dezena) d
	  from tb_dezenas 
	 where tp_concurso = 'QN'
	   --and nu_concurso = 1
	 group by nu_concurso   
) tb1
;


select 1;


select count(nu_concurso), a,b,c,d from (
	select nu_concurso,
	       sum(case when nu_dezena between  1 and 20 then 1 else 0 end) a, 
	       sum(case when nu_dezena between 21 and 40 then 1 else 0 end) b,
	       sum(case when nu_dezena between 41 and 60 then 1 else 0 end) c ,
	       sum(case when nu_dezena between 61 and 80 then 1 else 0 end) d
	  from tb_dezenas 
	 where tp_concurso = 'QN'
	   --and nu_concurso = 1
	 group by nu_concurso   
) tb1
group by tb1.a, tb1.b,tb1.c,tb1.d
order by 1 desc
;