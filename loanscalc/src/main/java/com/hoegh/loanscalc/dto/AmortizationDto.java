package com.hoegh.loanscalc.dto;

import java.math.BigDecimal;

/**
 * @author Ben John Cantre
 * Per period amortization
 */
public class AmortizationDto {
	private int period;
	private BigDecimal amortizationInterest = new BigDecimal(0.0);
	private BigDecimal amortizationCapital = new BigDecimal(0.0);
	private BigDecimal remainingCapital = new BigDecimal(0.0);
	
	public AmortizationDto() {}
	
	public AmortizationDto(int period,
			BigDecimal amortizationInterest,
			BigDecimal amortizationCapital,
			BigDecimal remainingCapital) {
		this.period = period;
		this.amortizationInterest = amortizationInterest;
		this.amortizationCapital = amortizationCapital;
		this.remainingCapital = remainingCapital;
	}
	
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public BigDecimal getAmortizationInterest() {
		return amortizationInterest;
	}
	public void setAmortizationInterest(BigDecimal amortizationInterest) {
		this.amortizationInterest = amortizationInterest;
	}
	public BigDecimal getAmortizationCapital() {
		return amortizationCapital;
	}
	public void setAmortizationCapital(BigDecimal amortizationCapital) {
		this.amortizationCapital = amortizationCapital;
	}
	public BigDecimal getRemainingCapital() {
		return remainingCapital;
	}
	public void setRemainingCapital(BigDecimal remainingCapital) {
		this.remainingCapital = remainingCapital;
	}
	
	@Override
	public String toString() {
		return "period=" + period 
				+ ", amortizationInterest=" + amortizationInterest.doubleValue()
				+ ", amortizationCapital=" + amortizationCapital.doubleValue()
				+ ", remainingCapital=" + remainingCapital.doubleValue();
	}
}
