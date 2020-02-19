package com.suncorp.banking.application.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.suncorp.banking.application.controller.BankAccountController;
import com.suncorp.banking.application.controller.BankAccountTransactionController;
import com.suncorp.banking.application.dto.AccountTransaction;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;

/**
 * @author uday
 *
 */
public class AccountTransaactionResource extends ResourceSupport {

	private List<AccountTransaction> accountTransactions;

	public AccountTransaactionResource(List<AccountTransaction> accountTransactions, String accountNumber) {
		super();
		this.accountTransactions = accountTransactions;
		try {
			add(linkTo(methodOn(BankAccountController.class).fetchAccountDetails(accountNumber)).withRel("self"));
			add(linkTo(methodOn(BankAccountTransactionController.class).fetchAccountTransactionDetails(accountNumber))
					.withRel("transactions"));
		} catch (AccountNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<AccountTransaction> getAccountTransactions() {
		return accountTransactions;
	}

	public void setAccountTransactions(List<AccountTransaction> accountTransactions) {
		this.accountTransactions = accountTransactions;
	}
	
	
}
