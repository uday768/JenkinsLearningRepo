package com.suncorp.banking.application.service;

import java.util.List;

import com.suncorp.banking.application.dto.AccountTransaction;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;

/**
 * @author uday
 *
 */
public interface AccountTransactionService {

	public List<AccountTransaction> getAccountTrans(String accountNumber) throws AccountNotExistsException;

}
