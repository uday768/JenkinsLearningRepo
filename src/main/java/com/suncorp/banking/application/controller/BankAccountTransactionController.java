package com.suncorp.banking.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.suncorp.banking.application.dto.AccountTransaction;
import com.suncorp.banking.application.dto.TransactionResponse;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;
import com.suncorp.banking.application.resource.AccountTransaactionResource;
import com.suncorp.banking.application.service.AccountTransactionService;

/**
 * @author uday
 *
 */
@RestController
@RequestMapping("/accounts/{accountNumber}/transactions")
public class BankAccountTransactionController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountTransactionService accountTransactionService;

	/**
	 * @param accountNumber
	 * @return
	 * @throws AccountNotExistsException
	 */
	@ResponseBody
	@GetMapping
	public ResponseEntity<?> fetchAccountTransactionDetails(@PathVariable String accountNumber)
			throws AccountNotExistsException {
		logger.info("Fetching the transaction details for the account number:" + accountNumber);
		List<AccountTransaction> accountTrans = accountTransactionService.getAccountTrans(accountNumber);
		if (null != accountTrans && accountTrans.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(new AccountTransaactionResource(accountTrans, accountNumber));
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body(new TransactionResponse("NO_DATA_FOUND", "No Transactions were performed on this account."));
		}
	}
	
}
