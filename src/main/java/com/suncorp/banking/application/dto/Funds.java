package com.suncorp.banking.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.suncorp.banking.application.validation.annonation.Conditional;

/**
 * @author uday
 *
 */
@Conditional
public class Funds {

	@NotBlank(message = "Transaction type cannot be blank.")
	@NotNull(message = "Transaction type cannot be null.")
	private String transactionType;

	private String beneficiaryAccountNumber;

	private double fund;

	public Funds() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public double getFund() {
		return fund;
	}

	public void setFund(double fund) {
		this.fund = fund;
	}

	@Override
	public String toString() {
		return "FundsDTO [transactionType=" + transactionType + ", beneficiaryAccountNumber=" + beneficiaryAccountNumber
				+ ", fund=" + fund + "]";
	}

}
