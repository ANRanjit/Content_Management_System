package com.example.cms.utility;

import org.springframework.stereotype.Component;

import com.example.cms.dto.UserResponse;

@Component
public class ResponseStructure<T> {

	private int statusCode;
	private String message;
	private T data;
	public int getStatusCode() {
		return statusCode;
	}
	public ResponseStructure<T> setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ResponseStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public T getData() {
		return data;
		
	}
	public ResponseStructure<T> setData(T userResponse) {
		this.data = userResponse;
		return this;
	}
	
}
