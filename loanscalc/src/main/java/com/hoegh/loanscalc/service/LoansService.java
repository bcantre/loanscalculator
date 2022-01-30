package com.hoegh.loanscalc.service;

import org.springframework.stereotype.Service;

import com.hoegh.loanscalc.dto.AmortizationSummaryDto;
import com.hoegh.loanscalc.dto.LoanOptionsDto;
import com.hoegh.loanscalc.exception.UnsupportedLoanTypeException;
import com.hoegh.loanscalc.processor.LoansProcessor;
import com.hoegh.loanscalc.processor.SimpleLoansProcessorFactory;

/**
 * @author Ben John Cantre
 * Service singleton that delegates request from web or REST controller
 * to back-end business handlers/processors
 */
@Service
public class LoansService {
	
	public AmortizationSummaryDto processLoan(LoanOptionsDto options)
		throws UnsupportedLoanTypeException {
		LoansProcessor processor = SimpleLoansProcessorFactory.getInstance(options);
		processor.processLoan();
		
		return processor.getResult();
	}
}