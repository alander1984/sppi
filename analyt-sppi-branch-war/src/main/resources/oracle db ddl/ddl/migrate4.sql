CREATE TABLE SPPI_TEST.SPPI_TERMINAL_STATUS (ID NUMBER(38, 0) NOT NULL, CODE VARCHAR2(255), NAME VARCHAR2(255), UTI_FIRST_DIGIT INTEGER) ;

COMMENT ON TABLE SPPI_TEST.SPPI_TERMINAL_STATUS IS 'Терминальное состояние SPPI-теста' ;

COMMENT ON COLUMN SPPI_TEST.SPPI_TERMINAL_STATUS.CODE IS 'Код терминального состояния SPPI-теста' ;

COMMENT ON COLUMN SPPI_TEST.SPPI_TERMINAL_STATUS.NAME IS 'Наименование терминального состояния SPPI-теста' ;

COMMENT ON COLUMN SPPI_TEST.SPPI_TERMINAL_STATUS.UTI_FIRST_DIGIT IS 'Первый символ УИТа' ;

ALTER TABLE SPPI_TEST.SPPI_TERMINAL_STATUS ADD CONSTRAINT terminal_status_pk PRIMARY KEY (id) ;

ALTER TABLE SPPI_TEST.SPPI_TERMINAL_STATUS ADD CONSTRAINT unq_terminal_status_code UNIQUE (code) ;

CREATE SEQUENCE SPPI_TEST.terminal_status_sequence START WITH 1 INCREMENT BY 1 ;

create or replace trigger SPPI_TEST.sppi_terminal_status_on_insert
            before insert ON SPPI_TEST.SPPI_TERMINAL_STATUS for each row
            begin
            select SPPI_TEST.terminal_status_sequence.nextval into :new.id from dual;
            end; ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('passed', 'Пройден', 1) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('not_passed', 'Не пройден', 2) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('inapplicable', 'Не применим', 3) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('insignificant_msfo9', 'Несущественная модификация МСФО 9', 4) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('significant_msfo9', 'Существенная модификация МСФО; SPPI-тест не пройден', 5) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('no_msfo9_with_change', 'Не модификация МСФО 9, ставка изменилась в рамках договора', 6) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME,UTI_FIRST_DIGIT) VALUES ('no_msfo9', 'Не модификация МСФО 9', 7) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME) VALUES ('significant_msfo9_needed_test', 'Существенная модификация МСФО 9 (необходимо проведение SPPI, теста на рыночность)"') ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('modification_test_passed', 'SPPI пройден (с учетом существенных изменений/модификации)', 8) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('modification_test_not_passed', 'SPPI не пройден (с учетом существенных изменений/модификации)', 9) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME, UTI_FIRST_DIGIT) VALUES ('modification_test_inapplicable', 'SPPI не применим(с учетом существенных изменений/модификации)', 0) ;

INSERT INTO SPPI_TEST.SPPI_TERMINAL_STATUS (CODE, NAME) VALUES ('paused', 'Приостановлен') ;


ALTER TABLE SPPI_TEST.SPPI_PROCESS ADD TERMINAL_STATUS_CODE VARCHAR2(255) ;

COMMENT ON COLUMN SPPI_TEST.SPPI_PROCESS.TERMINAL_STATUS_CODE IS 'Код терминального статуса теста' ;

ALTER TABLE SPPI_TEST.SPPI_PROCESS DROP COLUMN IS_PASSED ;

INSERT INTO SPPI_TEST.sppi_product_type (id, name, tree_code, description, product_code, attributes) VALUES (12, 'ФИ Модификация договора КЮЛ', 'update', '', 'UPD', '[{name:''ID_Договора/сделки'',type:String},{name:''ID_Заемщика'',type:String},{name:''Потраншевое прохождение'',type:String},{name:''ID_Транша'',type:String},{name:''Дополнительная информация: (КЮЛ/КИП/КЛ/ПФ)'',type:String}]');
