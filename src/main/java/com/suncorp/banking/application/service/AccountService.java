package com.suncorp.banking.application.service;

import com.suncorp.banking.application.dto.Account;
import com.suncorp.banking.application.dto.Funds;
import com.suncorp.banking.application.dto.TransactionResponse;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;
import com.suncorp.banking.application.exceptions.InSufficientFundsException;

/**
 * @author uday
 *
 */
public interface AccountService {

	public Account createAccount(Account accountDetails);

	public Account updateAccountDetails(String accountNumber, Account newAcctDetails)
			throws AccountNotExistsException;


	public Account getAccountDetails(String accountNumber) throws AccountNotExistsException;

	public TransactionResponse fundTransactions(String accountNumber, Funds fundsDto)
			throws AccountNotExistsException, InSufficientFundsException;

}
