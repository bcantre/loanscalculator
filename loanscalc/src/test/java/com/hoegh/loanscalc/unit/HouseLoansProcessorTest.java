package com.hoegh.loanscalc.unit;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hoegh.loanscalc.dto.AmortizationSummaryDto;
import com.hoegh.loanscalc.dto.LoanOptionsDto;
import com.hoegh.loanscalc.exception.UnsupportedLoanTypeException;
import com.hoegh.loanscalc.processor.LoansProcessor;
import com.hoegh.loanscalc.processor.SimpleLoansProcessorFactory;

public class HouseLoansProcessorTest {
	
	@Test
	void testIncomeLessThanExpenses() throws UnsupportedLoanTypeException {
		LoanOptionsDto options = new LoanOptionsDto();
		options.setInitialDeposit(new BigDecimal(20000.55));
		options.setInterestRate(new BigDecimal(5.25));
		options.setMonthlyExpenses(new BigDecimal(5500.77));
		options.setTerm(10);
		options.setMonthlyIncome(new BigDecimal(3600));

		LoansProcessor processor = SimpleLoansProcessorFactory.getInstance(options);
		Assertions.assertThrows(IllegalArgumentException.class, () -> processor.processLoan());
	}
	
	@Test
	void testLoansCalculation() {
		LoanOptionsDto options = new LoanOptionsDto();
		options.setInitialDeposit(new BigDecimal(20000.55));
		options.setInterestRate(new BigDecimal(5.25));
		options.setMonthlyExpenses(new BigDecimal(1200.77));
		options.setTerm(10);
		options.setMonthlyIncome(new BigDecimal(3600.00));

		try {
			LoansProcessor processor = SimpleLoansProcessorFactory.getInstance(options);
			processor.processLoan();
			
			AmortizationSummaryDto result = processor.getResult();
			Assertions.assertNotNull(result);
			Assertions.assertTrue(result.getMaxMonthlyPayment().compareTo(new BigDecimal(0)) > 0);
			Assertions.assertTrue(result.getMortgageTotal().compareTo(new BigDecimal(0)) > 0);
			Assertions.assertTrue(result.getTotalInterest().compareTo(new BigDecimal(0)) > 0);
			Assertions.assertTrue(result.getTotalPriceHouse().compareTo(new BigDecimal(0)) > 0);
			Assertions.assertFalse(result.getAmortizationData().isEmpty());
			
		} catch (UnsupportedLoanTypeException | IllegalArgumentException e) {
			Assertions.fail("Should not throw any exception");
		}
	}
}
