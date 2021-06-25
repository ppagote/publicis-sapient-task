package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;

import java.util.List;

public interface ICreditCardService {

    List<CreditCardDetailsModel> getAllDetails();

    CreditCardDetailsModel addDetails(CreditCardDetailsModel creditCardDetails);

    void findByCreditCardNumber(String creditCardNumber);
}
