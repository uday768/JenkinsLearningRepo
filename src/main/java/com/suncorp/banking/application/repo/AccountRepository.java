package com.suncorp.banking.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suncorp.banking.application.dto.Account;

/**
 * @author uday
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	/**
	 * @param accountNumber
	 * @return
	 */
	Account findAccountByAccountnumber(String accountNumber);
}
