/*** create table ***/
DECLARE
  execSql varchar2(8096);
  nObjExist INTEGER;
BEGIN
  DBMS_OUTPUT.enable();

  SELECT count(*) INTO nObjExist FROM dba_tables WHERE owner = 'UFS_SPPI' AND table_name = 'SPPI_PROCESS_STEPS';
  if (nObjExist = 0) THEN
    BEGIN
      DBMS_OUTPUT.PUT_LINE('Creating table UFS_SPPI.SPPI_PROCESS_STEPS');
      execSql :=
      'CREATE TABLE UFS_SPPI.SPPI_PROCESS_STEPS (
        ID NUMBER(18) NOT NULL,
        ANSWER VARCHAR2(60) NOT NULL,
        QUESTION_ID NUMBER(38,0) NOT NULL,
        STEP_NUMBER NUMBER(10,0) NOT NULL,
        ANSWER_TEXT VARCHAR2(512),

        CONSTRAINT SPPI_PROCESS_STEP_PK PRIMARY KEY (ID) USING INDEX TABLESPACE UFS_TS_TX_IDX,
        CONSTRAINT QUESTION_ID_FK FOREIGN KEY (QUESTION_ID) REFERENCES UFS_SPPI.SPPI_QUESTIONS (ID)
      )
      TABLESPACE UFS_TS_TX_DATA';
      EXECUTE IMMEDIATE execSql;

    EXCEPTION
      WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20004,'Error CREATING TABLE UFS_SPPI.SPPI_PROCESS_STEPS. CAUSED_BY: '|| SQLERRM);
    END;

    -- Adding comments
    BEGIN
      execSql := 'COMMENT ON TABLE UFS_SPPI.SPPI_QUESTIONS IS ''Справочник вопросов''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_QUESTIONS.CODE IS ''Код вопроса''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_QUESTIONS.CONTENT IS ''Текст вопроса''';
      EXECUTE IMMEDIATE  execSql;

    EXCEPTION
      WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20005,'Error CREATING COMMENT UFS_SPPI.SPPI_PROCESS_STEPS. CAUSED_BY: '|| SQLERRM);
    END;
  ELSE
    DBMS_OUTPUT.PUT_LINE('Table UFS_SPPI.SPPI_PROCESS_STEPS already exists!');
  END if;
END;
/

/*** create sequence ***/
DECLARE
  execSql varchar2(8096);
  nObjExist INTEGER;
BEGIN
  DBMS_OUTPUT.enable();
  SELECT count(*) INTO nObjExist FROM dba_sequences WHERE sequence_owner = 'UFS_SPPI' AND sequence_name = 'PROCESS_STEP_SEQUENCE';
  if (nObjExist = 0) THEN
    BEGIN
      execSql :=
      'CREATE SEQUENCE UFS_SPPI.PROCESS_STEP_SEQUENCE MINVALUE 1 MAXVALUE 9999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 100 NOORDER NOCYCLE';
      EXECUTE IMMEDIATE  execSql;
      EXCEPTION
      WHEN OTHERS THEN   RAISE_APPLICATION_ERROR(-20004,'Error CREATING SEQUENCE UFS_SPPI.PROCESS_STEP_SEQUENCE. CAUSED_BY: '|| SQLERRM);
    END ;
  END if;
END;
/
