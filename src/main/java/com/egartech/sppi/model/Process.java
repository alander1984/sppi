package com.egartech.sppi.model;

import javax.persistence.*;

@Entity
@Table(name="process")
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_seq")
    @SequenceGenerator(
            name = "process_seq",
            sequenceName = "process_sequence",
            allocationSize = 1
    )
    Long id;
    
    @Column(name = "comment")
    private String comment;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
