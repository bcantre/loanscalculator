package com.hoegh.loanscalc;

import static com.hoegh.loanscalc.constants.WebAttributeConstants.AMORTIZATION_DETAILS;
import static com.hoegh.loanscalc.constants.WebAttributeConstants.AMORTIZATION_SUMMARY;
import static com.hoegh.loanscalc.constants.WebAttributeConstants.LOAN_OPTIONS;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoegh.loanscalc.dto.LoanOptionsDto;
import com.hoegh.loanscalc.service.LoansService;
import com.hoegh.loanscalc.web.controller.LoansWebController;

@WebMvcTest(LoansWebController.class)
public class IntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private LoansService service;
	
	@Test
	void testController() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		// GET request test
		// Verify HTTP status is success
		// Verify request attributes are present
		// Verify content generated contains expected UI labels
		this.mockMvc.perform(get("/loanscalc")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().attributeExists(LOAN_OPTIONS))
			.andExpect(model().attributeExists(AMORTIZATION_SUMMARY))
			.andExpect(model().attributeExists(AMORTIZATION_DETAILS))
			.andExpect(content().string(containsString("Initial Deposit ($)")));

		// POST request test
		// Verify HTTP status is success
		// Verify request attributes are present
		// Verify content generated contains expected Error Message due to invalid interest rate
		this.mockMvc.perform(post("/loanscalc")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new LoanOptionsDto()))
					.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(LOAN_OPTIONS))
				.andExpect(model().attributeExists(AMORTIZATION_SUMMARY))
				.andExpect(model().attributeExists(AMORTIZATION_DETAILS))
				.andExpect(content().string(containsString("Interest rate minimum is 1.0")));
	}
}
