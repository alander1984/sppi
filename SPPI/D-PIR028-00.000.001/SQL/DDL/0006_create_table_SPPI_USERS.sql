/*** create table ***/
DECLARE
  execSql varchar2(8096);
  nObjExist INTEGER;
BEGIN
  DBMS_OUTPUT.enable();

  SELECT count(*) INTO nObjExist FROM dba_tables WHERE owner = 'UFS_SPPI' AND table_name = 'SPPI_USERS';
  if (nObjExist = 0) THEN
    BEGIN
      DBMS_OUTPUT.PUT_LINE('Creating table UFS_SPPI.SPPI_USERS');
      execSql :=
      'CREATE TABLE UFS_SPPI.SPPI_USERS (
        ID NUMBER(18) NOT NULL ENABLE,
	      USERNAME VARCHAR2(25) NOT NULL ENABLE,
	      PASSWD VARCHAR2(100) NOT NULL ENABLE,
	      FIRST_PASSWORD VARCHAR(20),
	      ACTIVATED NUMBER(1,0),

	      UNIQUE (USERNAME)
      )
      TABLESPACE UFS_TS_TX_DATA';
      EXECUTE IMMEDIATE execSql;
    EXCEPTION
      WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20004,'Error CREATING TABLE UFS_SPPI.SPPI_USERS. CAUSED_BY: '|| SQLERRM);
    END;

    -- Adding comments
    BEGIN
      execSql := 'COMMENT ON TABLE UFS_SPPI.SPPI_USERS IS ''Пользователи''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_USERS.USERNAME IS ''Логин пользователя''';
      EXECUTE IMMEDIATE  execSql;
      execSql := 'COMMENT ON COLUMN UFS_SPPI.SPPI_USERS.PASSWD IS ''Пароль''';
      EXECUTE IMMEDIATE  execSql;

    EXCEPTION
      WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20005,'Error CREATING COMMENT UFS_SPPI.SPPI_USERS. CAUSED_BY: '|| SQLERRM);
    END;

  ELSE
    DBMS_OUTPUT.PUT_LINE('Table UFS_SPPI.SPPI_USERS already exists!');
  END if;
END;
/
