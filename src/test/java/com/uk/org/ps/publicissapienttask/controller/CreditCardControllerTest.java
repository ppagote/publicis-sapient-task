package com.uk.org.ps.publicissapienttask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.uk.org.ps.publicissapienttask.dto.CreditCardDetailsDTO;
import com.uk.org.ps.publicissapienttask.dto.ErrorDetails;
import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;
import com.uk.org.ps.publicissapienttask.service.CustomUserDetailsService;
import com.uk.org.ps.publicissapienttask.service.ICreditCardService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(CreditCardController.class)
class CreditCardControllerTest {

    @MockBean
    private ICreditCardService mockCreditCardService;

    @MockBean
    private CustomUserDetailsService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddDetails_withSuccess() throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO("userName", "1", 0);

        // Run the test
       mockMvc.perform(post("/api/cc/v1/add")
               .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .with(csrf())
               .with(user("duke"))
       )
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAddDetails_whenNullValues_thenReturns400() throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO();

        // Run the test
        mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "-1", "12q12", "123456789012345678901234"})
    void testAddDetails_whenFaultedCreditCardNumber_thenReturns400(String creditCardNumber) throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO("test", creditCardNumber, 0);

        // Run the test
        mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1})
    void testAddDetails_whenFaultedCreditCardLimit_thenReturns400(int creditCardLimit) throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO("test", "12345678903555", creditCardLimit);

        // Run the test
        mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testAddDetails_withVerifyBusinessLogic() throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO("userName", "12345678903555", 0);

        // Run the test
        mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify the results
        ArgumentCaptor<CreditCardDetailsModel> cardCaptor = ArgumentCaptor.forClass(CreditCardDetailsModel.class);
        verify(mockCreditCardService, times(1)).addDetails(cardCaptor.capture());
        assertThat(cardCaptor.getValue().getCcLimit()).isEqualTo(creditCardDetailsDto.getCcLimit());
        assertThat(cardCaptor.getValue().getCcNumber()).isEqualTo(creditCardDetailsDto.getCcNumber());
        assertThat(cardCaptor.getValue().getUserName()).isEqualTo(creditCardDetailsDto.getUserName());
    }

    @Test
    void testAddDetails_withVerifyJsonOutput() throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO("userName", "12345678903555", 0);

        // Configure CreditCardService.addDetails(...).
        final CreditCardDetailsModel creditCardDetailsModel =
                new CreditCardDetailsModel(0L, Timestamp.valueOf(LocalDateTime.now()),
                        creditCardDetailsDto.getUserName(), creditCardDetailsDto.getCcNumber(),
                        creditCardDetailsDto.getCcLimit(), 0);
        when(mockCreditCardService.addDetails(any(CreditCardDetailsModel.class)))
                .thenReturn(creditCardDetailsModel);

        // Run the test
        mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ccLimit").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ccNumber").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNotEmpty());
    }

    @Test
    void testAddDetails_whenNullUserName_thenReturns400AndErrorResult() throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO(null, "12345678903555", 0);

        // Run the test
        MvcResult mvcResult = mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        ErrorDetails expectedErrorResponse = new ErrorDetails(LocalDateTime.now(),
                "User Name is mandatory.", HttpStatus.BAD_REQUEST.name());

        // Verify the results
        String actualResponseBody =
                mvcResult.getResponse().getContentAsString();
        ErrorDetails actualErrorDetail = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .readValue(actualResponseBody, ErrorDetails.class);
        assertThat(actualErrorDetail.getMessage())
                .isEqualToIgnoringWhitespace(expectedErrorResponse.getMessage());
        assertThat(actualErrorDetail.getStatus())
                .isEqualToIgnoringWhitespace(expectedErrorResponse.getStatus());
    }

    @Test
    void testAddDetails_whenNullCreditCardNumber_thenReturns400AndErrorResult() throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO("Pranav", null, 0);

        // Run the test
        MvcResult mvcResult = mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        ErrorDetails expectedErrorResponse = new ErrorDetails(LocalDateTime.now(),
                "Credit Card number is mandatory.", HttpStatus.BAD_REQUEST.name());

        // Verify the results
        String actualResponseBody =
                mvcResult.getResponse().getContentAsString();
        ErrorDetails actualErrorDetail = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .readValue(actualResponseBody, ErrorDetails.class);
        assertThat(actualErrorDetail.getMessage())
                .isEqualToIgnoringWhitespace(expectedErrorResponse.getMessage());
        assertThat(actualErrorDetail.getStatus())
                .isEqualToIgnoringWhitespace(expectedErrorResponse.getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "-1", " gdisg3423 ", "274637845235648266325632584234"})
    void testAddDetails_whenFaultyCreditCardNumber_thenReturns400AndErrorResult(String creditCardNumber) throws Exception {
        // Setup
        final CreditCardDetailsDTO creditCardDetailsDto =
                new CreditCardDetailsDTO("Pranav", creditCardNumber, 0);

        // Run the test
        MvcResult mvcResult = mockMvc.perform(post("/api/cc/v1/add")
                .content(new ObjectMapper().writeValueAsString(creditCardDetailsDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        ErrorDetails expectedErrorResponse = new ErrorDetails(LocalDateTime.now(),
                "Credit Card number must contain only digits, must be positive number and max length can be 19", HttpStatus.BAD_REQUEST.name());

        // Verify the results
        String actualResponseBody =
                mvcResult.getResponse().getContentAsString();
        ErrorDetails actualErrorDetail = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .readValue(actualResponseBody, ErrorDetails.class);
        assertThat(actualErrorDetail.getMessage())
                .isEqualToIgnoringWhitespace(expectedErrorResponse.getMessage());
        assertThat(actualErrorDetail.getStatus())
                .isEqualToIgnoringWhitespace(expectedErrorResponse.getStatus());
    }

    @Test
    void testFetchAllDetails_WithSuccess() throws Exception {
        // Run the test
        mockMvc.perform(get("/api/cc/v1/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify the results
    }

    @Test
    void testFetchAllDetails_withVerifyJsonOutput() throws Exception {

        // Configure CreditCardService.getAllDetails(...).
        final List<CreditCardDetailsModel> creditCardDetailsModels =
                Collections.singletonList(new CreditCardDetailsModel(0L,
                        Timestamp.valueOf(LocalDateTime.now()), "userName", "12345678903555",
                        0, 0));
        when(mockCreditCardService.getAllDetails()).thenReturn(creditCardDetailsModels);

        // Run the test
        mockMvc.perform(get("/api/cc/v1/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].ccLimit").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].ccNumber").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].userName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].createdTime").isNotEmpty());
    }

    @Test
    void testFetchAllDetails_CreditCardServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockCreditCardService.getAllDetails()).thenReturn(Collections.emptyList());

        // Run the test
        mockMvc.perform(get("/api/cc/v1/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user("duke"))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}
