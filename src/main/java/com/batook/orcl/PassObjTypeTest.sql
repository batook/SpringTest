create or replace type RECTYPE as object (
    cnumber   number (9)
  , cvarchar  varchar2 (32 char)
  , cdate     date
)
/
create or replace type RECTAB as table of RECTYPE;
/
create or replace package IOSTRUCTARRAY as
  procedure TESTPROC (
      iorec in out RECTAB
    , orec out RECTAB
  );
end;
/
create or replace package body IOSTRUCTARRAY as
  procedure TESTPROC (
      iorec in out RECTAB
    , orec out RECTAB
  ) is
  begin
    -- Копирование in-out параметра в out и изменение in-out параметра.
    orec := iorec;
    for i in 1 .. iorec.count loop
      iorec (i).cnumber := -orec (i).cnumber;
      iorec (i).cvarchar := 'New-' || orec (i).cvarchar;
      iorec (i).cdate := orec (i).cdate + 1 + 12 / 24 + 34 / (24 * 60) + 56 / (24 * 60 * 60);
    end loop;
  end;
end;
/