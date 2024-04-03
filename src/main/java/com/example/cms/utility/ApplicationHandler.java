package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exceptions.BlogAlreadyExistedByTitleException;
import com.example.cms.exceptions.BlogNotFoundByIdException;
import com.example.cms.exceptions.BlogPostAlreadyInDraftException;
import com.example.cms.exceptions.BlogPostNotFoundByIdException;
import com.example.cms.exceptions.ContributersAlreadyPresentException;
import com.example.cms.exceptions.ContributersNotPresentException;
import com.example.cms.exceptions.IllegalAccessRequestException;
import com.example.cms.exceptions.IllegalAccessRequestForUpdateException;
import com.example.cms.exceptions.PanelNotFoundByIdException;
import com.example.cms.exceptions.TopicsNotSpecifiedException;
import com.example.cms.exceptions.UserAlreadyExistByEmailException;
import com.example.cms.exceptions.UserNotFoundException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationHandler {
	
	private ErrorStructure<String> errorStructure;

	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status,String message,String errorData)
	{
		return new ResponseEntity<ErrorStructure<String>>(errorStructure.setErrorCode(status.value())
				.setErrorMessage(message)
				.setErrorData(errorData),status);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> userAlreadyExistByEmailException(UserAlreadyExistByEmailException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Illegal access");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> blogNotFoundByIdException(BlogNotFoundByIdException bnfe)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,bnfe.getMessage(),"Blog Not Found ");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> blogAlreadyExistException(BlogAlreadyExistedByTitleException baet)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,baet.getMessage(),"Blog Title Already Exist");

	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> topicsNotSpecifiedException(TopicsNotSpecifiedException tnse)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,tnse.getMessage(),"User Already Exist");

	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> illegalAccessRequestException(IllegalAccessRequestException iare)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,iare.getMessage(),"Illegal Access");

	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> panelNotFoundByIdException(PanelNotFoundByIdException pnfe)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,pnfe.getMessage(),"Panel Not Found");

	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> blogContributersAlreadyPresentationPresent(ContributersAlreadyPresentException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Contributer Already present ");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> blogContributersNotPresentationPresent(ContributersNotPresentException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Contributer Not present ");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> blogPostNotFoundById(BlogPostNotFoundByIdException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"BlogPost Not Exist In This Id ");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> illegalAccessForUpdateRequestException(IllegalAccessRequestForUpdateException iare)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,iare.getMessage(),"User doesn't have a permission");

	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> illegalAccessForUpdateRequestException(BlogPostAlreadyInDraftException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"BlogPost Already in Draft");

	}
}
