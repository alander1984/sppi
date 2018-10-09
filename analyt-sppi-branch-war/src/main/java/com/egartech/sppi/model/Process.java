package com.egartech.sppi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.FluentIterable;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="sppi_processes")
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_seq")
    @SequenceGenerator(
            name = "process_seq",
            sequenceName = "process_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "description")
    private String description;

    @OrderBy("step_number")
    @OneToMany(mappedBy="process")
    //@JsonManagedReference("process-steps")
    private List<ProcessStep> processSteps = new ArrayList<>();

    @Column(name = "date_start")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateStart;
    
    @Column(name = "date_end")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateEnd;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "attributes")
    private String attributes;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @Column(name = "is_passed")
    private Boolean isPassed;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "uti")
    private String uti;

    @ManyToOne()
    @JoinColumn(name = "paused_question_id")
    private Question pausedQuestion;

    public String getQuestionAnswer(String code) {
    	Iterator<ProcessStep> steps = this.processSteps.iterator();
    	boolean found = false;
    	while (steps.hasNext()&&!found) {
    		ProcessStep step = steps.next();
    		if (step.getQuestion().getCode().equalsIgnoreCase(code)) {
    			return step.getAnswer();
    		}
    	}
    	return "NOT_FOUND";
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ProcessStep getLastProcessStep() {
        return FluentIterable.from(processSteps).last().or(new ProcessStep());
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getUti() {
		return uti;
	}

	public void setUti(String uti) {
		this.uti = uti;
	}

    public Question getPausedQuestion() {
        return pausedQuestion;
    }

    public void setPausedQuestion(Question pausedQuestion) {
        this.pausedQuestion = pausedQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(id, process.id) &&
        		Objects.equals(uti, process.uti) &&
                Objects.equals(description, process.description) &&
                Objects.equals(processSteps, process.processSteps) &&
                Objects.equals(dateStart, process.dateStart) &&
                Objects.equals(productName, process.productName) &&
                Objects.equals(productCode, process.productCode) &&
                Objects.equals(dateEnd, process.dateEnd) &&
                Objects.equals(isPassed, process.isPassed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uti, description, processSteps, dateStart, productName, productCode, dateEnd, isPassed);
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", dateStart=" + dateStart +
                ", productName='" + productName + '\'' +
                '}';
    }



	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}


}
