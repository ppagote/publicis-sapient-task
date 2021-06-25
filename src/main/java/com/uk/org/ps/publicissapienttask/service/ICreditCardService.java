package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.dto.CreditCardDetailsDTO;
import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;

import java.util.List;

public interface ICreditCardService {

    List<CreditCardDetailsModel> getAllDetails();

    CreditCardDetailsModel addDetails(CreditCardDetailsDTO creditCardDetails);

    CreditCardDetailsModel findByCreditCardNumber(Long creditCardNumber);
}
