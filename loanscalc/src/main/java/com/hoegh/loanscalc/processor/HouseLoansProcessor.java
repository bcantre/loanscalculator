package com.hoegh.loanscalc.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoegh.loanscalc.dto.AmortizationDto;
import com.hoegh.loanscalc.dto.AmortizationSummaryDto;
import com.hoegh.loanscalc.dto.LoanOptionsDto;

public class HouseLoansProcessor extends LoansProcessor {
	private final Logger logger = LoggerFactory.getLogger(HouseLoansProcessor.class);
	
	public HouseLoansProcessor(LoanOptionsDto options) {
		super(options);
	}

	@Override
	public AmortizationSummaryDto calculate() {
		LoanOptionsDto optionsClone = new LoanOptionsDto(getOptions());
		
		if (optionsClone.getMonthlyExpenses().compareTo(optionsClone.getMonthlyIncome()) > 0) {
			optionsClone.setMonthlyExpenses(optionsClone.getMonthlyIncome());
		}
		
		int periodsRequested = optionsClone.getTerm() * 12;
		int totalPeriods = getMaxTerm(DEFAULT_AGE, DEFAULT_MAX_AGE, periodsRequested);
		
		BigDecimal maxMonthlyPayment = findMaxPay(optionsClone.getMonthlyIncome(), 
				optionsClone.getMonthlyExpenses());
		
		BigDecimal mortgageTotal = findMortgage(
				maxMonthlyPayment, 
				optionsClone.getInterestRate().divide(BD_12, 6, RoundingMode.HALF_UP),
				new BigDecimal(totalPeriods));
		
		BigDecimal totalPriceHouse = mortgageTotal.add(optionsClone.getInitialDeposit());
		BigDecimal totalInterest = new BigDecimal(0);
		
		List<AmortizationDto> amortizationList = new ArrayList<>();
		if (mortgageTotal.compareTo(new BigDecimal(0)) > 0) {
			amortizationList = totalAmortization(
					mortgageTotal, 
					maxMonthlyPayment, 
					periodsRequested, 
					optionsClone.getInterestRate().divide(BD_12, 6, RoundingMode.HALF_UP));
			
			totalInterest = amortizationList.stream().map(
					am -> am.getAmortizationInterest()).reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		
		AmortizationSummaryDto summary = new AmortizationSummaryDto();
		summary.setMaxMonthlyPayment(maxMonthlyPayment);
		summary.setMortgageTotal(mortgageTotal);
		summary.setTotalInterest(totalInterest);
		summary.setTotalPriceHouse(totalPriceHouse);
		summary.setAmortizationData(amortizationList);

		return summary;
	}
	
	private BigDecimal findMortgage(BigDecimal pay, 
			BigDecimal monthlyInterest, 
			BigDecimal totalPeriods) {

		logger.debug("monthlyInterest=" + monthlyInterest.doubleValue());
		logger.debug("denominator=" 
				+ monthlyInterest.divide(BD_100, 6, RoundingMode.HALF_UP).doubleValue());
		
		//TODO: Refactor
		return pay.multiply(new BigDecimal(BD_1.subtract(new BigDecimal(Math.pow(
						BD_1.add(monthlyInterest.divide(BD_100, 6, RoundingMode.HALF_UP)).doubleValue(), 
						totalPeriods.multiply(new BigDecimal(-1)).doubleValue()
					)
				)).doubleValue()))
				.divide(monthlyInterest.divide(BD_100, 6, RoundingMode.HALF_UP), 6, RoundingMode.HALF_UP);
	}
	
	private List<AmortizationDto> totalAmortization(BigDecimal mortgageTotal, 
			BigDecimal maxMonthlyPayment, 
			int periodsRequested, 
			BigDecimal interest) {
		
		List<AmortizationDto> amortizationList = 
				getAmortization(mortgageTotal, 
								maxMonthlyPayment, 
								periodsRequested, 
								interest.divide(BD_100, 6, RoundingMode.HALF_UP));
		
		return amortizationList;
	}
	
	private List<AmortizationDto> getAmortization(BigDecimal capital, 
			BigDecimal pay, 
			int periods, 
			BigDecimal interest) {

		List<AmortizationDto> amortizationTable = new ArrayList<>();
		updateAmortizationList(capital, pay, periods, interest, amortizationTable);
		
		return amortizationTable;
	}
	
	// Warning: recursive!
	private void updateAmortizationList (BigDecimal capital, 
			BigDecimal pay, 
			int periods, 
			BigDecimal interest, 
			List<AmortizationDto> amortizationTable) {

		BigDecimal amortizationInterest = capital.multiply(interest.divide(BD_12, 6, RoundingMode.HALF_UP));
		BigDecimal amortizationCapital = pay.subtract(amortizationInterest);
		
		if (capital.compareTo(pay) < 0) {
			pay = capital.add(amortizationInterest);
			amortizationCapital = pay.subtract(amortizationInterest);
			amortizationTable.add(
					new AmortizationDto(
						amortizationTable.size()+1, 
						amortizationInterest,
						amortizationCapital,
						capital.subtract(amortizationCapital)));
			
			return;
		} else {
			amortizationTable.add(
					new AmortizationDto(
						amortizationTable.size()+1, 
						amortizationInterest,
						amortizationCapital,
						capital.subtract(amortizationCapital)));
			
			// Recursion
			updateAmortizationList(capital.subtract(amortizationCapital), 
					pay, periods - 1, interest, amortizationTable);
		}
	}

}
