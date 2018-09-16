package com.egartech.sppi.model;

import com.egartech.sppi.configuration.CommonUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "step_number")
    private Integer stepNumber;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "process_step_process",
            inverseJoinColumns = @JoinColumn(name = "process_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "process_step_id", referencedColumnName = "id"))
    @JsonBackReference
    private Process process;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return CommonUtils.getAnswersMap(question.getAnswers()).get(answer);
    }

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
}
