package com.hoegh.loanscalc.unit;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import com.hoegh.loanscalc.dto.LoanOptionsDto;
import com.hoegh.loanscalc.web.controller.LoansWebController;

public class LoanOptionsDtoTest {
	private final Logger logger = LoggerFactory.getLogger(LoanOptionsDtoTest.class);
	private static Validator validator;
	
	@BeforeAll
	static void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@AfterAll
	static void done() {
		validator = null;
	}
	
	@Test
	void testInvalidArgument() {
		LoanOptionsDto options = new LoanOptionsDto();
		Set<ConstraintViolation<LoanOptionsDto>> violations = validator.validate(options);
		logger.debug(violations.toString());
		Assertions.assertFalse(violations.isEmpty());
		
		options.setInitialDeposit(new BigDecimal(20000.55));
		options.setInterestRate(new BigDecimal(5.25));
		options.setMonthlyExpenses(new BigDecimal(2389.15));
		options.setTerm(5);  // Minimum is 10
		options.setMonthlyIncome(new BigDecimal(3599.77));
		violations = validator.validate(options);
		logger.debug(violations.toString());
		Assertions.assertFalse(violations.isEmpty());
		
		options.setMonthlyIncome(new BigDecimal(9999999.00));
		options.setTerm(10);
		violations = validator.validate(options);
		logger.debug(violations.toString());		
		Assertions.assertFalse(violations.isEmpty());
	}
	
	@Test
	void testValidArgument() {
		LoanOptionsDto options = new LoanOptionsDto();
		options.setInitialDeposit(new BigDecimal(20000.55));
		options.setInterestRate(new BigDecimal(5.25));
		options.setMonthlyExpenses(new BigDecimal(2389.15));
		options.setTerm(10);
		options.setMonthlyIncome(new BigDecimal(3599.77));
		
		Set<ConstraintViolation<LoanOptionsDto>> violations = validator.validate(options);
		logger.debug(violations.toString());
		Assertions.assertTrue(violations.isEmpty());
	}
	
}
