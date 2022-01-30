package com.hoegh.loanscalc.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import com.hoegh.loanscalc.constants.LoanType;

/**
 * @author Ben John Cantre
 * Represents loan options data entry from UI
 * Uses bean validation framework
 */
public class LoanOptionsDto {
	@DecimalMin(
			value = "0.0", 
			inclusive = true, 
			message="Initial deposit minimun is 0")
    @DecimalMax(
    		value = "99999999.00", 
			inclusive = true, 
    		message="Initial deposit maximum is 99,999,999.00")
	private BigDecimal initialDeposit = new BigDecimal(0);
	
	@DecimalMin(
			value = "1000.00", 
			inclusive = true, 
			message="Monthly income minimum is 1000.00")
	@DecimalMax(
    		value = "999999.00", 
			inclusive = true, 
    		message="Monthly income maximum is 999,999.00")
	private BigDecimal monthlyIncome = new BigDecimal(0);
	
	@DecimalMin(
			value = "1.0", 
			inclusive = true,
			message="Interest rate minimum is 1.0")
	@DecimalMax(
    		value = "200.0", 
			inclusive = true, 
    		message="Interest rate maximum is 200.0")
	private BigDecimal interestRate = new BigDecimal(0);
	
	@Min(value=10, message="Term minimum is 10 years")
	private int term = 10;
	
	@DecimalMin(
			value = "1.0", 
			inclusive = true, 
			message="Monthly expenses minimum is 1.0")
	@DecimalMax(
    		value = "999999.0", 
			inclusive = true, 
    		message="Monthy expenses maximum is 999,999.0")
	private BigDecimal monthlyExpenses = new BigDecimal(0);
	
	// Default, can be other type
	private LoanType type = LoanType.HOUSE;
	
	public LoanOptionsDto() {}
	
	public LoanOptionsDto(LoanOptionsDto dto) {
		this.setInitialDeposit(
				new BigDecimal(dto.getInitialDeposit().doubleValue()));
		this.setMonthlyIncome(
				new BigDecimal(dto.getMonthlyIncome().doubleValue()));
		this.setInterestRate(
				new BigDecimal(dto.getInterestRate().doubleValue()));
		this.setTerm(dto.getTerm());
		this.setMonthlyExpenses(
				new BigDecimal(dto.getMonthlyExpenses().doubleValue()));
	}
	
	public BigDecimal getInitialDeposit() {
		return initialDeposit;
	}
	public void setInitialDeposit(BigDecimal initialDeposit) {
		this.initialDeposit = initialDeposit;
	}
	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public BigDecimal getMonthlyExpenses() {
		return monthlyExpenses;
	}
	public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
		this.monthlyExpenses = monthlyExpenses;
	}
	
	public LoanType getType() {
		return type;
	}

	public void setType(LoanType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "initialDeposit=" + initialDeposit.doubleValue()
				+ "monthlyIncome=" + monthlyIncome.doubleValue()
				+ "interestRate=" + interestRate.doubleValue()
				+ "term=" + term
				+ "monthlyExpenses=" + monthlyExpenses.doubleValue();
	}
}
