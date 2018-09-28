package com.egartech.sppi.model;

public class GetNextQuestionDataDTO {
	
	private String answer;
	private String fullNameVerifier;
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setFullNameVerifier(String fullNameVerifier) {
		this.fullNameVerifier = fullNameVerifier;
	}
	
	public String getFullNameVerifier() {
		return fullNameVerifier;
	}
	
}
