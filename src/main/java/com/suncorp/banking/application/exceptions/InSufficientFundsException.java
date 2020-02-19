package com.suncorp.banking.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author uday
 *
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InSufficientFundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param accountNumber
	 */
	public InSufficientFundsException(String accountNumber) {
		super("Sufficinet funds are not available in the account " + accountNumber);
	}
}
