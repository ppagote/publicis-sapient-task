package com.uk.org.ps.publicissapienttask.utility;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateCreditCardNumberTest {

    @ParameterizedTest
    @ValueSource(strings = {"274637845"})
    void testValidate_Failure(String ccNumber) {
        assertFalse(ValidateCreditCardNumber.validate(ccNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678903555", "012850003580200", "1358954993914435"})
    void testValidate_SUccess(String ccNumber) {
        assertTrue(ValidateCreditCardNumber.validate(ccNumber));
    }
}
