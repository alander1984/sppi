package com.egartech.sppi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

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
    private Long id;

    @Column(name = "comment")
    private String comment;

    @OrderBy("step_number")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "process_step_process",
        joinColumns = @JoinColumn(name = "process_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "process_step_id", referencedColumnName = "id"))
    @JsonManagedReference("process-steps")
    private List<ProcessStep> processSteps = new ArrayList<>();

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished = false;

    @Column(name = "is_passed")
    private Boolean isPassed;

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

    public List<ProcessStep> getProcessSteps() {
        return processSteps;
    }

    public void setProcessSteps(List<ProcessStep> processSteps) {
        this.processSteps = processSteps;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public ProcessStep getLastProcessStep() {
        return processSteps.stream()
                .reduce((first, second) -> second)
                .orElseGet(ProcessStep::new);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(id, process.id) &&
                Objects.equals(comment, process.comment) &&
                Objects.equals(processSteps, process.processSteps) &&
                Objects.equals(dateStart, process.dateStart) &&
                Objects.equals(productName, process.productName) &&
                Objects.equals(productCode, process.productCode) &&
                Objects.equals(isFinished, process.isFinished) &&
                Objects.equals(isPassed, process.isPassed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, processSteps, dateStart, productName, productCode, isFinished, isPassed);
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", dateStart=" + dateStart +
                ", productName='" + productName + '\'' +
                '}';
    }
}
