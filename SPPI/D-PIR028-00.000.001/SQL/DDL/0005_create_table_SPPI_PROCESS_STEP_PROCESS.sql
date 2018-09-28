/*** create table ***/
DECLARE
  execSql varchar2(8096);
  nObjExist INTEGER;
BEGIN
  DBMS_OUTPUT.enable();

  SELECT count(*) INTO nObjExist FROM dba_tables WHERE owner = 'UFS_SPPI' AND table_name = 'SPPI_PROCESS_STEP_PROCESS';
  if (nObjExist = 0) THEN
    BEGIN
      DBMS_OUTPUT.PUT_LINE('Creating table UFS_SPPI.SPPI_PROCESS_STEP_PROCESS');
      execSql :=
      'CREATE TABLE UFS_SPPI.SPPI_PROCESS_STEP_PROCESS (
        PROCESS_ID NUMBER(18) NOT NULL,
	      PROCESS_STEP_ID NUMBER(18) NOT NULL,

        CONSTRAINT SPPI_PROCESS_STEP_PROCESS_PK PRIMARY KEY (PROCESS_ID, PROCESS_STEP_ID) USING INDEX TABLESPACE UFS_TS_TX_IDX,
        CONSTRAINT PROCESS_ID_FK FOREIGN KEY (PROCESS_ID) REFERENCES UFS_SPPI.SPPI_PROCESSES(ID),
	      CONSTRAINT PROCESS_STEP_ID_FK FOREIGN KEY (PROCESS_STEP_ID) REFERENCES UFS_SPPI.SPPI_PROCESS_STEPS(ID)
      )
      TABLESPACE UFS_TS_TX_DATA';
      EXECUTE IMMEDIATE execSql;
      EXECUTE IMMEDIATE 'CREATE UNIQUE INDEX UFS_SPPI.SPPI_PROCESS_STEP_PROCESS_UK1 ON UFS_SPPI.SPPI_PROCESS_STEP_PROCESS (PROCESS_STEP_ID,PROCESS_ID)' ||
                        ' TABLESPACE UFS_TS_TX_IDX';
      EXECUTE IMMEDIATE 'CREATE UNIQUE INDEX UFS_SPPI.SPPI_PROCESS_STEP_PROCESS_UK2 ON UFS_SPPI.SPPI_PROCESS_STEP_PROCESS (PROCESS_STEP_ID)' ||
                        ' TABLESPACE UFS_TS_TX_IDX';
    EXCEPTION
      WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20004,'Error CREATING TABLE UFS_SPPI.SPPI_PROCESS_STEP_PROCESS. CAUSED_BY: '|| SQLERRM);
    END;

  ELSE
    DBMS_OUTPUT.PUT_LINE('Table UFS_SPPI.SPPI_PROCESS_STEP_PROCESS already exists!');
  END if;
END;
/
