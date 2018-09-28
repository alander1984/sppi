--------------------------------------------------------
--  Create Tablespaces
--  Attention! For Development purpose only
--------------------------------------------------------
declare
  oradata_directory varchar2(100) := '${ts.directory}';

  i       number;
  execSql varchar2(8096);
    TablespaceExistsExcep EXCEPTION;
  pragma exception_init(TablespaceExistsExcep, -1543);

  TYPE ts_rec is RECORD(
    ts_name       VARCHAR2(30),
    datafile_name varchar2(30),
    ts_size       varchar2(10),
    ts_maxsize    varchar2(10));
  TYPE ts_tab IS TABLE OF ts_rec;
  TablespaceList ts_tab;

  FUNCTION fill_rec(ts_name       VARCHAR2,
                    datafile_name varchar2,
                    ts_size       varchar2,
                    ts_maxsize    varchar2) RETURN ts_rec IS
    a ts_rec;
    BEGIN
      a.ts_name       := ts_name;
      a.datafile_name := datafile_name;
      a.ts_size       := ts_size;
      a.ts_maxsize    := ts_maxsize;
      RETURN a;
    END fill_rec;

begin
  TablespaceList := ts_tab(fill_rec('UFS_TS_TX_DATA',
                                    'ufs_tx_data01.dbf',
                                    '10M',
                                    '1G'),
                           fill_rec('UFS_TS_TX_IDX',
                                    'ufs_tx_idx01.dbf',
                                    '10M',
                                    '1G'));

  FOR i IN TablespaceList.FIRST .. TablespaceList.LAST LOOP
    begin
      execSql := 'create tablespace ' || TablespaceList(i).ts_name ||
                 ' datafile ''' || oradata_directory || TablespaceList(i)
                 .datafile_name || ''' size ' || TablespaceList(i).ts_size ||
                 ' autoextend on next 10M maxsize ' || TablespaceList(i)
                 .ts_maxsize;
      DBMS_OUTPUT.PUT_LINE(execSql);
      execute immediate execSql;

      exception
      when TablespaceExistsExcep then
      dbms_output.put_line('----- Tablespace ' || TablespaceList(i).ts_name ||
                           ' already exist!');

    end;

  end loop;
  exception
  when others then
  dbms_output.put_line('Error: ' || SQLCODE || ' ' || SQLERRM);
end;
/