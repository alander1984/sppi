package com.egartech.sppi.model;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="sppi_product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_type_seq")
    @SequenceGenerator(
            name = "product_type_seq",
            sequenceName = "product_type_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
	private String description;

    @Column(name = "attributes")
    private String attributes;

	@Column(name = "quiz_tree_code")
    private String quizTreeCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getQuizTreeCode() {
		return quizTreeCode;
	}

	public void setQuizTreeCode(String quizTreeCode) {
		this.quizTreeCode = quizTreeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}