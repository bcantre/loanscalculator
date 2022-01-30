package com.hoegh.loanscalc.processor;

import com.hoegh.loanscalc.constants.LoanType;
import com.hoegh.loanscalc.dto.LoanOptionsDto;
import com.hoegh.loanscalc.exception.UnsupportedLoanTypeException;

public class SimpleLoansProcessorFactory {

	private SimpleLoansProcessorFactory() {}
	
	public static LoansProcessor getInstance(LoanOptionsDto options)
		throws UnsupportedLoanTypeException {
		if (LoanType.HOUSE.equals(options.getType())) {
			return new HouseLoansProcessor(options);
		}
		
		// Other loan types are not supported yet
		throw new UnsupportedLoanTypeException(options);
	}
}
