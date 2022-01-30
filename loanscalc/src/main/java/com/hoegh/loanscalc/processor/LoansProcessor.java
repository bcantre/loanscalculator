package com.hoegh.loanscalc.processor;

import java.math.BigDecimal;

import com.hoegh.loanscalc.dto.AmortizationSummaryDto;
import com.hoegh.loanscalc.dto.LoanOptionsDto;

public abstract class LoansProcessor {

	protected static final int DEFAULT_AGE = 18;
	protected static final int DEFAULT_MAX_AGE = 65;
	protected static final BigDecimal DEFAULT_RISK_RATE = new BigDecimal(0.36);
	protected static final BigDecimal BD_100 = new BigDecimal(100);
	protected static final BigDecimal BD_12 = new BigDecimal(12);
	protected static final BigDecimal BD_1 = new BigDecimal(1);
	
	private LoanOptionsDto options;
	private AmortizationSummaryDto amortizationResult;
	
	public LoansProcessor(LoanOptionsDto options) {
		this.options = options;
	}
	
	/*
	 * Template method pattern
	 */
	public final void processLoan() throws IllegalArgumentException {
		if (options.getMonthlyExpenses().compareTo(options.getMonthlyIncome()) >= 0) {
			throw new IllegalArgumentException("Monthly income should be greater than monthly expenses");
		}
		
		amortizationResult = calculate();
		save();
	}
	
	protected abstract AmortizationSummaryDto calculate();
	
	protected void save() {
		// Save amortizationResult to DB
	}
	
	public LoanOptionsDto getOptions() {
		return options;
	}
	
	public AmortizationSummaryDto getResult() {
		return amortizationResult;
	}
	
	protected BigDecimal findMaxPay(BigDecimal monthlyIncome, 
			BigDecimal monthlyExpenses) {
		return (monthlyIncome.subtract(monthlyExpenses)).multiply(DEFAULT_RISK_RATE);
	}
	
	protected int getMaxTerm(int age, 
			int maxAge, 
			int periodsRequested) {
		int maxPeriods = (maxAge - age) * 12;
		
		if (maxPeriods > periodsRequested) {
            return periodsRequested;
        } else {
            return maxPeriods;
        }
	}
}
