/*** create table ***/
DECLARE
  execSql varchar2(8096);
  nObjExist INTEGER;
BEGIN
  DBMS_OUTPUT.enable();

  SELECT count(*) INTO nObjExist FROM dba_tables WHERE owner = 'UFS_SPPI' AND table_name = 'SPPI_PROCESS';
  if (nObjExist = 0) THEN
    BEGIN
      DBMS_OUTPUT.PUT_LINE('Creating table UFS_SPPI.SPPI_PROCESSES');
      execSql :=
      'CREATE TABLE UFS_SPPI.SPPI_PROCESSES (
        ID NUMBER(18) NOT NULL,
	      DESCRIPTION VARCHAR2(255),
	      PRODUCT_NAME VARCHAR2(60),
	      PRODUCT_CODE VARCHAR2(25),
	      IS_PASSED NUMBER(1,0),
	      DATE_START TIMESTAMP(6),
	      DATE_END TIMESTAMP(6),

        CONSTRAINT SPPI_PROCESSES_PK PRIMARY KEY (ID) USING INDEX TABLESPACE UFS_TS_TX_IDX
      )
      TABLESPACE UFS_TS_TX_DATA';
      EXECUTE IMMEDIATE execSql;

    EXCEPTION
      WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20004,'Error CREATING TABLE UFS_SPPI.SPPI_PROCESSES. CAUSED_BY: '|| SQLERRM);
    END;

    -- Adding comments
    BEGIN
      execSql := 'COMMENT ON TABLE UFS_SPPI.SPPI_PROCESSES IS ''Запущенные процессы''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_PROCESSES.DESCRIPTION IS ''Комментарий''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_PROCESSES.DATE_START IS ''Дата начала прохождения теста''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_PROCESSES.PRODUCT_NAME IS ''Имя продукта, к которому относится тест''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_PROCESSES.PRODUCT_CODE IS ''Код продукта, к которому относится тест''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_PROCESSES.IS_PASSED IS ''Признак того, что тест пройден успешно''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_PROCESSES.DATE_END IS ''Дата окончания прохождения теста''';
      EXECUTE IMMEDIATE  execSql;

    EXCEPTION
      WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20005,'Error CREATING COMMENT UFS_SPPI.SPPI_PROCESSES. CAUSED_BY: '|| SQLERRM);
    END;
  ELSE
    DBMS_OUTPUT.PUT_LINE('Table UFS_SPPI.SPPI_PROCESSES already exists!');
  END if;
END;
/

/*** create sequence ***/
DECLARE
  execSql varchar2(8096);
  nObjExist INTEGER;
BEGIN
  DBMS_OUTPUT.enable();
  SELECT count(*) INTO nObjExist FROM dba_sequences WHERE sequence_owner = 'UFS_SPPI' AND sequence_name = 'PROCESS_SEQUENCE';
  if (nObjExist = 0) THEN
    BEGIN
      execSql :=
      'CREATE SEQUENCE UFS_SPPI.PROCESS_SEQUENCE MINVALUE 1 MAXVALUE 9999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 100 NOORDER NOCYCLE';
      EXECUTE IMMEDIATE  execSql;
      EXCEPTION
      WHEN OTHERS THEN   RAISE_APPLICATION_ERROR(-20004,'Error CREATING SEQUENCE UFS_SPPI.PROCESS_SEQUENCE. CAUSED_BY: '|| SQLERRM);
    END ;
  END if;
END;
/
