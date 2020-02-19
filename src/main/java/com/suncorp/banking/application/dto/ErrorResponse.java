package com.suncorp.banking.application.dto;

import java.util.Set;

/**
 * @author uday
 *
 */
public class ErrorResponse {
	private String message;

	private Set<String> details;

	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorResponse(String message, Set<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<String> getDetails() {
		return details;
	}

	public void setDetails(Set<String> details) {
		this.details = details;
	}

}
