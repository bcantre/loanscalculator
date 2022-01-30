package com.hoegh.loanscalc.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ben John Cantre
 * Wrapper class for amortization totals and per
 * month amortization
 */
public class AmortizationSummaryDto {
	private BigDecimal maxMonthlyPayment = new BigDecimal(0.0);
	private BigDecimal mortgageTotal = new BigDecimal(0.0);
	private BigDecimal totalPriceHouse = new BigDecimal(0.0);
	private BigDecimal totalInterest  = new BigDecimal(0.0);
	
	private List<AmortizationDto> amortizationData = new ArrayList<>();
	
	public BigDecimal getMaxMonthlyPayment() {
		return maxMonthlyPayment;
	}
	public void setMaxMonthlyPayment(BigDecimal maxMonthlyPayment) {
		this.maxMonthlyPayment = maxMonthlyPayment;
	}
	public BigDecimal getMortgageTotal() {
		return mortgageTotal;
	}
	public void setMortgageTotal(BigDecimal mortgageTotal) {
		this.mortgageTotal = mortgageTotal;
	}
	public BigDecimal getTotalPriceHouse() {
		return totalPriceHouse;
	}
	public void setTotalPriceHouse(BigDecimal totalPriceHouse) {
		this.totalPriceHouse = totalPriceHouse;
	}
	public BigDecimal getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}
	public List<AmortizationDto> getAmortizationData() {
		return amortizationData;
	}
	public void setAmortizationData(List<AmortizationDto> amortizationData) {
		this.amortizationData = amortizationData;
	}
	
	@Override
	public String toString() {
		return "maxMonthlyPayment=" + maxMonthlyPayment
				+ ", mortgageTotal=" + mortgageTotal 
				+ ", totalPriceHouse=" + totalPriceHouse
				+ ", totalInterest=" + totalInterest;
	}
}
