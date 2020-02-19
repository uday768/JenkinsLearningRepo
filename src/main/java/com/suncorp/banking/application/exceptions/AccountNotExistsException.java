package com.suncorp.banking.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author uday
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param accountNumber
	 */
	public AccountNotExistsException(String accountNumber) {
		super("Account doesn't exists " + accountNumber);
	}
}
