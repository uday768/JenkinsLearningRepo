package com.suncorp.banking.application.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suncorp.banking.application.dto.AccountTransaction;

/**
 * @author uday
 *
 */
@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

	/**
	 * @param accountId
	 * @return
	 */
	List<AccountTransaction> findByAccountId(Long accountId);

}
