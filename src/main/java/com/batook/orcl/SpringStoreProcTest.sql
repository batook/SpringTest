CREATE OR REPLACE TYPE TEST_EMP_OBJ AS OBJECT (empno number, empname varchar2(30));
create or replace type TEST_EMP_OBJ_ARRAY as table of TEST_EMP_OBJ;
create table test_emp (empno number, empname varchar2(30));

CREATE OR REPLACE PROCEDURE TEST_EMP_OBJ_ARRAY_PROC ( p_obj_array in TEST_EMP_OBJ_ARRAY ) AS
begin
 for i in 1..p_obj_array.count loop
  insert into test_emp (empno, empname) values(p_obj_array(i).empno, p_obj_array(i).empname);
 end loop;
end;
/