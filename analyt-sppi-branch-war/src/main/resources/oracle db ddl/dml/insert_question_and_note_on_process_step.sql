create or replace trigger SPPI_TEST.insert_question_and_note
before insert ON SPPI_TEST.process_step for each row
begin
  select content into :new.question_text from SPPI_TEST.SPPI_QUESTION where id = :new.question_id;
  select note into :new.note_text from SPPI_TEST.SPPI_QUESTION where id = :new.question_id;
end;
