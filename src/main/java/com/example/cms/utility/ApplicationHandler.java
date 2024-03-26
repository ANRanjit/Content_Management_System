package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exceptions.UserAlreadyExistByEmailException;
import com.example.cms.exceptions.UserNotFoundException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationHandler {
	
	private ErrorStructure<String> errorStructure;

	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status,String message,String errorData)
	{
		return new ResponseEntity<ErrorStructure<String>>(errorStructure.setErrorCode(status.value()).setErrorMessage(message)
				.setErrorData(errorData),status);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> userAlreadyExistByEmailException(UserAlreadyExistByEmailException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"User Already Exist");
	}
	
	
}
