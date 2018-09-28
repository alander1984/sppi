package com.egartech.sppi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="sppi_questions")
public class Question {

    public Question() { }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(
            name = "question_seq",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    private Long id;
    
    @Column(name = "code")
    private String code;
    
    @Column(name="content")
    private String content;

    @Column(name = "answers")
    private String answers;

    @Column(name = "note")
    private String note;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(code, question.code) &&
                Objects.equals(content, question.content) &&
                Objects.equals(answers, question.answers) &&
                Objects.equals(note, question.note);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, content, answers, note);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", answers='" + answers + '\'' +
                '}';
    }
}
