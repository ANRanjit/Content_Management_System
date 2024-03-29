package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PanelNotFoundByIdException extends RuntimeException {

	private String Message;
}
