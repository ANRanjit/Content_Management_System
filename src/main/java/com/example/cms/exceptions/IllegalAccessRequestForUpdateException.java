package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IllegalAccessRequestForUpdateException extends Exception {

	private String message;
}
