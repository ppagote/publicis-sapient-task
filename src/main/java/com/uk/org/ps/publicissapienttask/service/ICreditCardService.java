package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.dto.CreditCardDetailsDTO;
import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;

import java.math.BigInteger;
import java.util.List;

public interface ICreditCardService {

    List<CreditCardDetailsModel> getAllDetails();

    CreditCardDetailsModel addDetails(CreditCardDetailsModel creditCardDetails);

    CreditCardDetailsModel findByCreditCardNumber(String creditCardNumber);
}
