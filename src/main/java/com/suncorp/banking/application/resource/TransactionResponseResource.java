package com.suncorp.banking.application.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.suncorp.banking.application.controller.BankAccountController;
import com.suncorp.banking.application.controller.BankAccountTransactionController;
import com.suncorp.banking.application.dto.TransactionResponse;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;

/**
 * @author uday
 *
 */
public class TransactionResponseResource extends ResourceSupport {

	private TransactionResponse transaction;

	/**
	 * @param transactionDto
	 * @param accountNumber
	 */
	public TransactionResponseResource(TransactionResponse transactionDto, String accountNumber) {
		super();
		this.transaction = transactionDto;
		try {
			add(linkTo(methodOn(BankAccountController.class).fetchAccountDetails(accountNumber)).withRel("self"));
			add(linkTo(methodOn(BankAccountTransactionController.class).fetchAccountTransactionDetails(accountNumber))
					.withRel("transactions"));
		} catch (AccountNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public TransactionResponse getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionResponse transaction) {
		this.transaction = transaction;
	}

}
