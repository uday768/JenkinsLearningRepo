package com.suncorp.banking.application.service.impl;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suncorp.banking.application.dto.Account;
import com.suncorp.banking.application.dto.AccountTransaction;
import com.suncorp.banking.application.dto.Funds;
import com.suncorp.banking.application.dto.TransactionResponse;
import com.suncorp.banking.application.exceptions.AccountNotExistsException;
import com.suncorp.banking.application.exceptions.InSufficientFundsException;
import com.suncorp.banking.application.repo.AccountRepository;
import com.suncorp.banking.application.repo.AccountTransactionRepository;
import com.suncorp.banking.application.service.AccountService;

/**
 * @author uday
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AccountRepository accountRepo; 
	
	@Autowired
	AccountTransactionRepository transRepo;
	
	/* (non-Javadoc)
	 * @see com.suncorp.banking.application.service.AccountService#createAccount(com.suncorp.banking.application.dto.AccountDTO)
	 */
	@Override
	public Account createAccount(Account accountDetails){
		logger.info("Creating the account for the user:"+accountDetails.getFirstName());
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String accountNumberFeed = accountDetails.getFirstName() + accountDetails.getLastName() + timestamp.toString();
		SecureRandom random = new SecureRandom(accountNumberFeed.getBytes());
		accountDetails.setAccountnumber(Integer.toString(1000000000 + random.nextInt(90000000)));
		accountRepo.save(accountDetails);
		return accountDetails;
	}
	
	/* (non-Javadoc)
	 * @see com.suncorp.banking.application.service.AccountService#updateAccountDetails(java.lang.String, com.suncorp.banking.application.dto.AccountDTO)
	 */
	@Override
	@Transactional
	public Account updateAccountDetails(String accountNumber, Account newAcctDetails) throws AccountNotExistsException {
		logger.info("Checking the whether the account exists or not");
		Account accountDetails = accountRepo.findAccountByAccountnumber(accountNumber);
		if(null != accountDetails){
			logger.info("Account details are-->" + accountDetails.toString());
			String refId = generateRefId();
			AccountTransaction transVo = new AccountTransaction("EDIT_ACCOUNT", 0, refId, "Change of the account type from "+accountDetails.getAccountType()+" to "+newAcctDetails.getAccountType(), accountDetails);
			accountDetails.setAccountType(newAcctDetails.getAccountType());
			accountRepo.save(accountDetails);
			transRepo.save(transVo);
			return accountDetails;
		} else {
			throw new AccountNotExistsException(newAcctDetails.getAccountnumber());
		}
	}
	
	public List<AccountTransaction> getAccountTrans(String accountNumber) throws AccountNotExistsException{
		logger.info("Checking the whether the account exists or not");
		Account accountDetails = accountRepo.findAccountByAccountnumber(accountNumber);
		if(null != accountDetails){
			List<AccountTransaction> findAllByAccountNumber = transRepo.findByAccountId(accountDetails.getId());
			return findAllByAccountNumber;
		} else {
			throw new AccountNotExistsException(accountNumber);
		}
	}
	
	@Override
	public Account getAccountDetails(String accountNumber) throws AccountNotExistsException{
		logger.info("Checking the whether the account exists or not");
		Account accountDetails = accountRepo.findAccountByAccountnumber(accountNumber);
		if(null != accountDetails){
			return accountDetails;
		} else {
			throw new AccountNotExistsException(accountNumber);
		}
	}
	
	private String generateRefId(){
		return new Timestamp(new Date().getTime()).toString().replace(":", "")
				.replace(".", "").replace(" ", "").replace("-", "");
	}
	
	@Override
	public TransactionResponse fundTransactions(String accountNumber, Funds fundsDto) throws AccountNotExistsException, InSufficientFundsException {
		logger.info("Processing the fund transaction for the account number"+accountNumber+" with trasanction"+fundsDto.getTransactionType());
		TransactionResponse response = new TransactionResponse(generateRefId(), "Trsanaction can not be processed.");
		if(fundsDto.getTransactionType().equals("DEPOSIT")) {
			response = depositFunds(accountNumber, fundsDto);
		} else if (fundsDto.getTransactionType().equals("WITHDRAW")) {
			response = withDrawFunds(accountNumber, fundsDto);
		} else if (fundsDto.getTransactionType().equals("TPT")) {
			response = trasnferFunds(accountNumber, fundsDto);
		}
		return response;
	}
	
	@Transactional
	public TransactionResponse depositFunds(String accountNumber, Funds fundsDto) throws AccountNotExistsException {
		logger.info("Checking the whether the account exists or not");
		Account accountFundDetails = accountRepo.findAccountByAccountnumber(accountNumber);
		if(null != accountFundDetails){
			logger.info("Account details are-->" + accountFundDetails.toString());
			double updatedBalance = accountFundDetails.getBalance() + fundsDto.getFund();
			accountFundDetails.setBalance(updatedBalance);
			String refId = generateRefId();
			AccountTransaction transVo = new AccountTransaction("DEPOSIT", fundsDto.getFund(), refId, "Amount deposited into the account", accountFundDetails);
			accountRepo.save(accountFundDetails);
			transRepo.save(transVo);
			return new TransactionResponse(refId, "Deposit request has been processed. Amount is creted to the account:"+accountFundDetails.getAccountnumber()+" Available banalce is:"+accountFundDetails.getBalance());
		} else {
			throw new AccountNotExistsException(accountNumber);
		}
		
	}

	@Transactional
	public TransactionResponse withDrawFunds(String accountNumber, Funds fundsDto) throws AccountNotExistsException, InSufficientFundsException {
		logger.info("Checking the whether the account exists or not");
		Account accountFundDetails = accountRepo.findAccountByAccountnumber(accountNumber);
		if (null != accountFundDetails) {
			if (accountFundDetails.getBalance() >= fundsDto.getFund()) {
				logger.info("Account details are-->" + accountFundDetails.toString());
				double updatedBalance = accountFundDetails.getBalance() - fundsDto.getFund();
				accountFundDetails.setBalance(updatedBalance);
				accountRepo.save(accountFundDetails);
				String refId = generateRefId();
				AccountTransaction transVo = new AccountTransaction("WITHDRAWL", fundsDto.getFund(), refId, "Amount withdrawl from the account", accountFundDetails);
				accountRepo.save(accountFundDetails);
				transRepo.save(transVo);
				return new TransactionResponse(refId, "Withdrawl request has been processed from the account:"+accountFundDetails.getAccountnumber()+" available balance is:"+accountFundDetails.getBalance());
			} else {
				throw new InSufficientFundsException(accountNumber);
			}
		} else {
			throw new AccountNotExistsException(accountNumber);
		}
	}

	@Transactional
	public TransactionResponse trasnferFunds(String accountNumber, Funds fundsDto)
			throws AccountNotExistsException, InSufficientFundsException {
		logger.info("Checking the whether the account exists or not");
		Account accountFundDetails = accountRepo.findAccountByAccountnumber(accountNumber);
		if (null != accountFundDetails) {
			if (accountFundDetails.getBalance() >= fundsDto.getFund()) {
				logger.info("Account details are-->" + accountFundDetails.toString());
				double updatedBalance = accountFundDetails.getBalance() - fundsDto.getFund();
				accountFundDetails.setBalance(updatedBalance);
				String refId = generateRefId();
				AccountTransaction transVo = new AccountTransaction("FUND_TRANSFER", fundsDto.getFund(), refId,
						"Amount transfered from " + accountNumber + " to " + fundsDto.getBeneficiaryAccountNumber(),
						accountFundDetails);
				accountRepo.save(accountFundDetails);
				transRepo.save(transVo);
				return new TransactionResponse(refId,
						"Fund Trasnfer request has been processed. Amount has been debited from the account:"
								+ accountNumber + " available balance is:" + accountFundDetails.getBalance());
			} else {
				throw new InSufficientFundsException(accountNumber);
			}
		} else {
			throw new AccountNotExistsException(accountNumber);
		}
	}
	
}
