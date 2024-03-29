package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class TopicsNotSpecifiedException extends RuntimeException{

	private String message;
	
	public String getMessage()
	{
		return message;
	}
}
