package com.suncorp.banking.application.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.suncorp.banking.application.controller.BankAccountController;
import com.suncorp.banking.application.controller.BankAccountTransactionController;
import com.suncorp.banking.application.dto.Account;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;

/**
 * @author uday
 *
 */
public class AccountResponseResource extends ResourceSupport {

	private Account account;

	/**
	 * @param account
	 */
	public AccountResponseResource(Account account) {
		super();
		this.account = account;
		String accountNumber = account.getAccountnumber();
		try {
			add(linkTo(methodOn(BankAccountController.class).fetchAccountDetails(accountNumber)).withRel("self"));
			add(linkTo(methodOn(BankAccountTransactionController.class).fetchAccountTransactionDetails(accountNumber))
					.withRel("transactions"));
		} catch (AccountNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
