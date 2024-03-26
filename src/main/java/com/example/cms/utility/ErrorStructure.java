package com.example.cms.utility;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure<T> {

	private int errorCode;
	private String errorMessage;
	private T errorData;
	public int getErrorCode() {
		return errorCode;
	}
	public ErrorStructure<T> setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public ErrorStructure<T> setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	public T getErrorData() {
		return errorData;
	}
	public ErrorStructure<T> setErrorData(T i) {
		this.errorData = i;
		return this;
	}
}
