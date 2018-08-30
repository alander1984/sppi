package com.egartech.sppi.model;

import javax.persistence.*;

@Entity
@Table(name="question")
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(
            name = "question_seq",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    Long id;
    
    @Column(name = "code")
    private String code;
    
    @Column(name="content")
    private String content;
    
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
    
}
