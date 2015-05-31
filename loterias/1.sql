create or replace procedure get_long_text is

v_name  varchar2(100); 
v_text  varchar2(32767); 

cursor my_cursor is 
    select view_name, text 
      from ALL_VIEWS 
     where owner = 'AAD'
;

begin

    delete from aad.view_tmp;
    commit;

    open my_cursor;

    loop
        fetch my_cursor into v_name, v_text;
        exit when my_cursor%notfound;
        
        DBMS_OUTPUT.PUT_LINE(v_name);
        insert into aad.view_tmp(name, text) values(v_name, substr(v_text,1,4000));    
    end loop;
    
    close my_cursor;    

    commit;
    
end;
/

show errors;