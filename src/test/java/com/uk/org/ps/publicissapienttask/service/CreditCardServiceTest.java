package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.exception.CardAlreadyExistsException;
import com.uk.org.ps.publicissapienttask.exception.InvalidCardException;
import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;
import com.uk.org.ps.publicissapienttask.repository.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

    @Mock
    private CreditCardRepository mockCreditCardRepository;

    private CreditCardService creditCardServiceUnderTest;

    @BeforeEach
    void setUp() {
        creditCardServiceUnderTest = new CreditCardService(mockCreditCardRepository);
    }

    @Test
    void testGetAllDetails() {
        // Setup

        // Configure CreditCardRepository.findAll(...).
        final List<CreditCardDetailsModel> creditCardDetailsModels =
                Collections.singletonList(new CreditCardDetailsModel(0L,
                        Timestamp.valueOf(LocalDateTime.now()), "userName", "ccNumber",
                        0, 0));
        when(mockCreditCardRepository.findAll(Sort.by("id"))).thenReturn(creditCardDetailsModels);

        // Run the test
        final List<CreditCardDetailsModel> result = creditCardServiceUnderTest.getAllDetails();

        // Verify the results
        assertEquals(result, creditCardDetailsModels);
    }

    @Test
    void testGetAllDetails_CreditCardRepositoryReturnsNoItems() {
        // Setup
        when(mockCreditCardRepository.findAll(Sort.by("id"))).thenReturn(Collections.emptyList());

        // Run the test
        final List<CreditCardDetailsModel> result = creditCardServiceUnderTest.getAllDetails();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testAddDetails() {
        // Setup
        final CreditCardDetailsModel creditCardDetails =
                new CreditCardDetailsModel(0L,
                        Timestamp.valueOf(LocalDateTime.now()), "userName",
                        "12345678903555", 0, 0);

        // Configure CreditCardRepository.findByccNumber(...).
        when(mockCreditCardRepository.findByccNumber("12345678903555")).thenReturn(null);

        // Configure CreditCardRepository.save(...).
        when(mockCreditCardRepository.save(any(CreditCardDetailsModel.class))).thenReturn(creditCardDetails);

        // Run the test
        final CreditCardDetailsModel result = creditCardServiceUnderTest.addDetails(creditCardDetails);

        // Verify the results
        assertEquals(creditCardDetails, result);
    }

    @Test
    void testAddDetails_ThrowCardAlreadyExistsException() {
        // Setup
        final CreditCardDetailsModel creditCardDetails =
                new CreditCardDetailsModel(0L,
                        Timestamp.valueOf(LocalDateTime.now()), "userName",
                        "12345678903555", 0, 0);

        // Configure CreditCardRepository.findByccNumber(...).
        when(mockCreditCardRepository.findByccNumber(any())).thenReturn(mock(CreditCardDetailsModel.class));

        CardAlreadyExistsException cardAlreadyExistsException = assertThrows(
                CardAlreadyExistsException.class, () ->
                        creditCardServiceUnderTest.findByCreditCardNumber("12345678903555")
        );

        // Verify the results
        String expectedMessage = "Credit Card number:: " + "12345678903555" + " already exists";
        String actualMessage = cardAlreadyExistsException.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testAddDetails_ThrowInvalidCardException() {
        // Setup
        final CreditCardDetailsModel creditCardDetails =
                new CreditCardDetailsModel(0L,
                        Timestamp.valueOf(LocalDateTime.now()), "userName",
                        "1234567890355511", 0, 0);

        InvalidCardException invalidCardException = assertThrows(
                InvalidCardException.class, () ->
                        creditCardServiceUnderTest.addDetails(creditCardDetails)
        );

        // Verify the results
        String expectedMessage = "Credit Card number:: " + "1234567890355511" + " is invalid";
        String actualMessage = invalidCardException.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

/*        @Test
    void testFindByCreditCardNumber_ThrowCardAlreadyExistsException() {
        // Setup
        String creditCardNumber = "12344566778487234";

        when(mockCreditCardRepository.findByccNumber(creditCardNumber))
                .thenReturn(mock(CreditCardDetailsModel.class));
        // Run the test
        CardAlreadyExistsException cardAlreadyExistsException = assertThrows(
                CardAlreadyExistsException.class, () ->
            creditCardServiceUnderTest.findByCreditCardNumber(creditCardNumber)
        );

        // Verify the results
        String expectedMessage = "Credit Card number:: " + creditCardNumber + " already exists";
        String actualMessage = cardAlreadyExistsException.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }*/
}
