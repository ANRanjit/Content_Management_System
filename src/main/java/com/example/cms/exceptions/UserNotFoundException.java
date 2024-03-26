package com.example.cms.exceptions;


public class UserNotFoundException extends RuntimeException {

	private String message;

	public String getMessage() {
		return message;
	}

	public UserNotFoundException(String message) {
		super();
		this.message = message;
	}
	
}
