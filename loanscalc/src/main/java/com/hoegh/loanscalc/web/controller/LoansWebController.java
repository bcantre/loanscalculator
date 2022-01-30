package com.hoegh.loanscalc.web.controller;

import static com.hoegh.loanscalc.constants.WebAttributeConstants.*;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hoegh.loanscalc.constants.WebResourceConstants;
import com.hoegh.loanscalc.dto.AmortizationSummaryDto;
import com.hoegh.loanscalc.dto.LoanOptionsDto;
import com.hoegh.loanscalc.exception.UnsupportedLoanTypeException;
import com.hoegh.loanscalc.service.LoansService;

@Controller
public class LoansWebController {
	@Autowired
	private LoansService service;
	private final Logger logger = LoggerFactory.getLogger(LoansWebController.class);
	
	@GetMapping("/loanscalc")
	public String loansForm(Model model) {
		logger.debug("Received GET request from web");
		
		LoanOptionsDto loanOptions = new LoanOptionsDto();
		AmortizationSummaryDto result = new AmortizationSummaryDto();
		
		model.addAttribute(USER_NAME, "Ben");
		model.addAttribute(LOAN_OPTIONS, loanOptions);
		model.addAttribute(AMORTIZATION_SUMMARY, result);
		model.addAttribute(AMORTIZATION_DETAILS, result.getAmortizationData());
		
		return WebResourceConstants.LOANS_MAIN;
	}
	
	/*
	 * Note: To get the binding validation errors, BindingResult must be
	 * placed just after @Valid parameter where bean validation occurs (LoanOptionsDto)
	 */
	@PostMapping("/loanscalc")
	public String loansSubmit(@Valid @ModelAttribute LoanOptionsDto loanOptionsDto, 
			BindingResult bindResult, 
			Model model) {
		logger.debug("POST request, loanOptionsDto from web: " + loanOptionsDto);

		if (bindResult.hasErrors()) {
			return handleErrorCondition(loanOptionsDto, bindResult, model);
		}
		
		AmortizationSummaryDto result = new AmortizationSummaryDto();

		try {
			result = service.processLoan(loanOptionsDto);
		} catch (UnsupportedLoanTypeException e) {
			logger.error(e.toString());
			bindResult.addError(
					new ObjectError("type", "Loan type is not supported at this time"));
		} catch (IllegalArgumentException e) {
			bindResult.addError(
					new ObjectError("monthlyIncome", e.getMessage()));
		} finally {
			model.addAttribute(USER_NAME, "Ben");
			model.addAttribute(LOAN_OPTIONS, loanOptionsDto);
			if (result == null) {
				result = new AmortizationSummaryDto();
			}
			model.addAttribute(AMORTIZATION_SUMMARY, result);
			model.addAttribute(AMORTIZATION_DETAILS, result.getAmortizationData());
		}

		return WebResourceConstants.LOANS_MAIN;
	}

	private String handleErrorCondition(LoanOptionsDto loanOptionsDto, 
			BindingResult bindResult, 
			Model model) {
		logger.error("Encountered validation errors");
		
		bindResult.getAllErrors().forEach((error) -> {
		        String fieldName = ((FieldError) error).getField();
		        String errorMessage = error.getDefaultMessage();

		        logger.error("Validation error field=" + fieldName 
		        		+ ", errorMessage=" + errorMessage);
		    });
		
		AmortizationSummaryDto result = new AmortizationSummaryDto();
		
		model.addAttribute(USER_NAME, "Ben");
		// Do not override loanOptionsDto with a new instance.
		// Doing so will remove all validation errors.
		model.addAttribute(LOAN_OPTIONS, loanOptionsDto);
		model.addAttribute(AMORTIZATION_SUMMARY, result);
		model.addAttribute(AMORTIZATION_DETAILS, result.getAmortizationData());
		
		return WebResourceConstants.LOANS_MAIN;
	}
}
