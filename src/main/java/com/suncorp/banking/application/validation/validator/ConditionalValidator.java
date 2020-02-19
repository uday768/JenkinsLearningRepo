package com.suncorp.banking.application.validation.validator;

import static org.springframework.util.StringUtils.isEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suncorp.banking.application.dto.Funds;
import com.suncorp.banking.application.validation.annonation.Conditional;

public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean isValid = false;
		// TODO Auto-generated method stub
		Funds funds = (Funds) object;
		logger.info("Invoking the constaring validations for the funds object"+funds.toString());
		if(funds.getTransactionType().equals("WITHDRAW") || funds.getTransactionType().equals("DEPOSIT")){
			logger.info("Invoking the constarint validations for the withdraw/deposit transations");
			if(!isEmpty(funds.getFund()) && funds.getFund()>0){
				isValid = true;
			} else {
				logger.info("Creating a error as the fund value is not provided.");
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Kinldy provide a valid Fund Value").addPropertyNode("fund").addConstraintViolation();	
			}
		} else if (funds.getTransactionType().equals("TPT")){
			logger.info("Invoking the constarint validations for the TPT transations");
			if((!isEmpty(funds.getFund()) && funds.getFund()>0) && (null != funds.getBeneficiaryAccountNumber() && funds.getBeneficiaryAccountNumber().length()>0)){
				isValid = true;
			} else {
				logger.info("Creating a error for the TPT.");
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Kinldy provide a valid Fund Value and the beneficiary account").addPropertyNode("beneficiaryAccountNumber").addConstraintViolation();
			}
		} else {
			logger.info("Creating a error for the invalid tansation type.");
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Kinldy provide a valid transaction type").addPropertyNode("transactionType").addConstraintViolation();
		}
		return isValid;
	}

}
