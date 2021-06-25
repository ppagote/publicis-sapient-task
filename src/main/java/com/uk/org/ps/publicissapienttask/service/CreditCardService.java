package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.dto.CreditCardDetailsDTO;
import com.uk.org.ps.publicissapienttask.exception.CardAlreadyExistsException;
import com.uk.org.ps.publicissapienttask.exception.InvalidCardException;
import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;
import com.uk.org.ps.publicissapienttask.repository.CreditCardRepository;
import com.uk.org.ps.publicissapienttask.utility.ValidateCreditCardNumber;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CreditCardService implements ICreditCardService {

    private final CreditCardRepository creditCardRepository;

    /**
     * Fetch all the credit card details
     *
     * @return List of CreditCardDetailsModel Object
     */
    @Override
    public List<CreditCardDetailsModel> getAllDetails() {
        List<CreditCardDetailsModel> creditCardDetailsList = new ArrayList<>();
        creditCardRepository.findAll(Sort.by("id").ascending()).forEach(creditCardDetailsList::add);
        return creditCardDetailsList;
    }

    /**
     * Adds a new credit card detail
     *
     * @param creditCardDetails CreditCardDetailsDTO Object
     * @return CreditCardDetailsModel Object
     */
    @Override
    public CreditCardDetailsModel addDetails(CreditCardDetailsDTO creditCardDetails) {
        CreditCardDetailsModel creditCardDetailsModel = new CreditCardDetailsModel();
        creditCardDetailsModel.setCcLimit(creditCardDetails.getCcLimit());
        creditCardDetailsModel.setUserName(creditCardDetails.getUserName());
        creditCardDetailsModel.setCcNumber(creditCardDetails.getCcNumber());

        if (!ValidateCreditCardNumber.validate(String.valueOf(creditCardDetails.getCcNumber()))) {
            throw new InvalidCardException("Credit Card number:: " + creditCardDetails.getCcNumber() + " is invalid");
        }

        findByCreditCardNumber(creditCardDetailsModel.getCcNumber());

        return creditCardRepository.save(creditCardDetailsModel);
    }

    /**
     * Checks if credit card is already added
     *
     * @param creditCardNumber Credit Card Number
     * @return CreditCardDetailsModel Object
     */
    @Override
    public CreditCardDetailsModel findByCreditCardNumber(Long creditCardNumber) {
        CreditCardDetailsModel byccNumber = creditCardRepository
                .findByccNumber(creditCardNumber);
        if (byccNumber != null) {
            throw new CardAlreadyExistsException("Credit Card number:: " + creditCardNumber + " already exists");
        }
        return byccNumber;
    }
}
