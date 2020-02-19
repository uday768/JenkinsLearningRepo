package com.suncorp.banking.application.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suncorp.banking.application.dto.Account;
import com.suncorp.banking.application.dto.AccountTransaction;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;
import com.suncorp.banking.application.repo.AccountRepository;
import com.suncorp.banking.application.repo.AccountTransactionRepository;
import com.suncorp.banking.application.service.AccountTransactionService;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

	@Autowired
	AccountTransactionRepository transRepo;

	@Autowired
	AccountRepository accountRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<AccountTransaction> getAccountTrans(String accountNumber) throws AccountNotExistsException {
		logger.info("Checking the whether the account exists or not");
		Account accountDetails = accountRepo.findAccountByAccountnumber(accountNumber);
		if (null != accountDetails) {
			List<AccountTransaction> findAllByAccountNumber = transRepo.findByAccountId(accountDetails.getId());
			return findAllByAccountNumber;
		} else {
			throw new AccountNotExistsException(accountNumber);
		}
	}

}
