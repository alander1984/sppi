package com.egartech.sppi.model;

import com.egartech.sppi.configuration.CommonUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Objects;
import java.util.*;

@Entity
@Table(name = "process_step")
public class ProcessStep implements Comparable<ProcessStep> {

    public ProcessStep() { }

    public ProcessStep(String answer, Integer stepNumber, Question question, Process process) {
        this.answer = answer;
        this.stepNumber = stepNumber;
        this.question = question;
        this.process = process;
    }

    public ProcessStep(String answer, Integer stepNumber, Question question, Process process, String answerText) {
        this.answer = answer;
        this.stepNumber = stepNumber;
        this.question = question;
        this.process = process;
        this.answerText = answerText;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_step_seq")
    @SequenceGenerator(
            name = "process_step_seq",
            sequenceName = "process_step_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "answer")
    private String answer;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "step_number")
    private Integer stepNumber;
    
    @Column(name = "full_name_verifier")
    private String fullNameVerifier;
    
    @Column(name = "date_verification")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateOfVerification;
    

    @ManyToOne
    @JoinColumn(name = "process_id")
    @JsonIgnore
    private Process process;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public String getAnswerText() {
        return CommonUtils.getAnswersMap(question.getAnswers()).get(answer);
    }*/

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }
    
    public void setFullNameVerifier(String fullNameVerifier) {
    	this.fullNameVerifier = fullNameVerifier;
    }
    
    public String getFullNameVerifier() {
    	return fullNameVerifier;
    }
    
    public void setDateOfVerification(Date dateOfVerification) {
    	this.dateOfVerification = dateOfVerification;
    }
    
    public Date getDateOfVerification() {
    	return dateOfVerification;
    }
    
    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessStep that = (ProcessStep) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(answer, that.answer) &&
                Objects.equals(question, that.question) &&
                Objects.equals(stepNumber, that.stepNumber) &&
                Objects.equals(process, that.process);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, answer, question, stepNumber, process);
    }

    @Override
    public String toString() {
        return "ProcessStep{" +
                "id=" + id +
                ", stepNumber=" + stepNumber +
                ", answer='" + answer + '\'' +
                ", question=" + question +
                ", process=" + process +
                '}';
    }

    @Override
    public int compareTo(ProcessStep processStep) {
        return this.getStepNumber().compareTo(processStep.getStepNumber());
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}

