select f1, f2, f3, count(1)
  from (
		select sum(case when nu_dezena < 21 then 1 else 0 end) F1,
		       sum(case when nu_dezena > 40 then 1 else 0 end) F2,
		       sum(case when nu_dezena > 20 and nu_dezena < 41 then 1 else 0 end) F3
		  from tb_dezenas 
		 where tp_sorteio = 'MS' 
		 group by nu_sorteio
       )
group by f1,f2,f3
order by count(1) desc 
 ;


select * 
  from tb_atrasos 
 where tp_sorteio = 'MS' 
   and nu_sorteio = (
           select max(nu_sorteio)
             from tb_sorteios 
            where tp_sorteio = 'MS'
       ); 


select nu_sorteio, count (1) 
  from tb_atrasos 
 where tp_sorteio = 'MS' 
 group by nu_sorteio
;

select *
  from tb_atrasos 
 where tp_sorteio = 'MS' 
   and qt_atraso = 0
   and nu_sorteio = 1664
;

select *
  from tb_atrasos 
 where tp_sorteio = 'MS' 
   and qt_atraso = 1
   and nu_sorteio = 1665
;

select nu_dezena, qt_atraso, count(1)
  from tb_atrasos 
 where tp_sorteio = 'MS' 
   and qt_atraso = 0
 group by nu_dezena, qt_atraso
 order by count(1) desc   
;

select nu_dezena, avg(qt_atraso)
  from tb_atrasos 
 where tp_sorteio = 'MS' 
 group by nu_dezena
 order by avg(qt_atraso)
;





select *
  from tb_dezenas
 where tp_sorteio = 'MS' 
   and nu_sorteio = 1664
;

