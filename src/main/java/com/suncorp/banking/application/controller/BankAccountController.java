package com.suncorp.banking.application.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.suncorp.banking.application.dto.Account;
import com.suncorp.banking.application.dto.Funds;
import com.suncorp.banking.application.dto.TransactionResponse;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;
import com.suncorp.banking.application.exceptions.InSufficientFundsException;
import com.suncorp.banking.application.resource.AccountResponseResource;
import com.suncorp.banking.application.resource.TransactionResponseResource;
import com.suncorp.banking.application.service.AccountService;
import com.suncorp.banking.application.validation.groups.NullNotAllowed;

/**
 * @author uday
 *
 */
@RestController
@RequestMapping("/accounts")
public class BankAccountController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;

	/**
	 * @param accountNumber
	 * @return
	 * @throws AccountNotExistsException
	 */
	@ResponseBody
	@GetMapping("{accountNumber}")
	public ResponseEntity<AccountResponseResource> fetchAccountDetails(@PathVariable String accountNumber)
			throws AccountNotExistsException {
		logger.info("Fetching the transaction details for the account number:" + accountNumber);
		Account accountTrans = accountService.getAccountDetails(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(new AccountResponseResource(accountTrans));
	}

	/**
	 * @param accountRequest
	 * @return
	 */
	@ResponseBody
	@PostMapping
	public ResponseEntity<AccountResponseResource> createAccount(@Valid @RequestBody Account accountRequest) {
		logger.info("Inside the create acccount controller method");
		Account createAccountResponse = accountService.createAccount(accountRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponseResource(createAccountResponse));
	}

	/**
	 * @param accountNumber
	 * @param accountRequest
	 * @return
	 * @throws AccountNotExistsException
	 */
	@ResponseBody
	@PatchMapping("{accountNumber}")
	public ResponseEntity<AccountResponseResource> editAccount(@PathVariable String accountNumber,
			@Validated(NullNotAllowed.class) @RequestBody Account accountRequest) throws AccountNotExistsException {
		logger.info("Inside the update account type service for the account number:" + accountNumber);
		Account updateAccountDetails = accountService.updateAccountDetails(accountNumber, accountRequest);
		return ResponseEntity.status(HttpStatus.OK).body(new AccountResponseResource(updateAccountDetails));
	}

	/**
	 * @param accountNumber
	 * @param fundsRequest
	 * @return
	 * @throws AccountNotExistsException
	 * @throws InSufficientFundsException
	 */
	@ResponseBody
	@PostMapping("{accountNumber}")
	public ResponseEntity<TransactionResponseResource> performFundTransaction(@PathVariable String accountNumber,
			@Valid @RequestBody Funds fundsRequest) throws AccountNotExistsException, InSufficientFundsException {
		logger.info("Performing the account related trasactions for the account number:" + accountNumber);
		TransactionResponse fundTransactionResponse = accountService.fundTransactions(accountNumber, fundsRequest);
		return ResponseEntity.status(HttpStatus.OK).body(new TransactionResponseResource(fundTransactionResponse, accountNumber));
	}
}
