package com.hoegh.loanscalc.exception;

import com.hoegh.loanscalc.dto.LoanOptionsDto;

public class UnsupportedLoanTypeException extends Exception {
	public UnsupportedLoanTypeException(LoanOptionsDto options) {
		super(options.getType() + "is not yet supported.");
	}
}
