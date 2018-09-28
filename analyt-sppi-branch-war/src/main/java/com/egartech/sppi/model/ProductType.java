package com.egartech.sppi.model;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_type_seq")
    @SequenceGenerator(
            name = "product_type_seq",
            sequenceName = "product_type_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "product_type_name")
    private String productTypeName;

    @Column(name = "product_type_code")
    private String productTypeCode;

    @Column(name = "attributes")
    private String attributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
    
    
}