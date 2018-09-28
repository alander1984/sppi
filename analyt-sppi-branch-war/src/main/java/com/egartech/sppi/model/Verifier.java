package com.egartech.sppi.model;

import javax.persistence.*;

@Entity
@Table(name="verifier")
public class Verifier {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verifier_seq")
    @SequenceGenerator(
            name = "verifier_seq",
            sequenceName = "verifier_sequence",
            allocationSize = 1
    )
	private Long id;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="post")
	private String post;
	
	public Verifier() {}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setPost(String post) {
		this.post = post;
	}
	
	public String getPost() {
		return post;
	}

}
