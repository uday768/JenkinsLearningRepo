package com.suncorp.banking.application.advice;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.suncorp.banking.application.dto.ErrorResponse;

/**
 * @author uday
 *
 */
@ControllerAdvice
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Set<String> details = new HashSet<String>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			Object[] arguments = error.getArguments();
			System.out.println(arguments[0].getClass());
			details.add(error.getDefaultMessage());
		}
		return new ResponseEntity(new ErrorResponse("Validation Error", details), HttpStatus.BAD_REQUEST);
	}
}
