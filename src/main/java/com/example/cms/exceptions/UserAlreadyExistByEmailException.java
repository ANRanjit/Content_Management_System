package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAlreadyExistByEmailException extends RuntimeException {

	String message;
}
