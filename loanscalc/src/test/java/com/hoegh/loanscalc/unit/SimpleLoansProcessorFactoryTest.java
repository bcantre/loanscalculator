package com.hoegh.loanscalc.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hoegh.loanscalc.constants.LoanType;
import com.hoegh.loanscalc.dto.LoanOptionsDto;
import com.hoegh.loanscalc.exception.UnsupportedLoanTypeException;
import com.hoegh.loanscalc.processor.HouseLoansProcessor;
import com.hoegh.loanscalc.processor.LoansProcessor;
import com.hoegh.loanscalc.processor.SimpleLoansProcessorFactory;

public class SimpleLoansProcessorFactoryTest {
	@Test
	void testUnsupportedLoanType() {
		LoanOptionsDto options = new LoanOptionsDto();
		options.setType(LoanType.PERSONAL);
		
		Assertions.assertThrows(UnsupportedLoanTypeException.class, 
				() -> SimpleLoansProcessorFactory.getInstance(options));
	}
	
	@Test
	void testSupportedLoanType() throws UnsupportedLoanTypeException {
		LoanOptionsDto options = new LoanOptionsDto();
		
		LoansProcessor processor = SimpleLoansProcessorFactory.getInstance(options);
		Assertions.assertNotNull(processor);
		Assertions.assertInstanceOf(HouseLoansProcessor.class, processor);
	}
}
