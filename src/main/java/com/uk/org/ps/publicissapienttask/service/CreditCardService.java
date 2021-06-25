package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.dto.CreditCardDetailsDTO;
import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;
import com.uk.org.ps.publicissapienttask.repository.CreditCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    /**
     * Fetch all the credit card details
     * @return List of CreditCardDetailsModel Object
     */
    public List<CreditCardDetailsModel> getAllDetails() {
        List<CreditCardDetailsModel> creditCardDetailsList = new ArrayList<>();
        creditCardRepository.findAll(Sort.by("id").ascending()).forEach(creditCardDetailsList:: add);
        return creditCardDetailsList;
    }

    /**
     * Adds a new credit card detail
     *
     * @param creditCardDetails CreditCardDetailsDTO Object
     * @return CreditCardDetailsModel Object
     */
    public CreditCardDetailsModel addDetails(CreditCardDetailsDTO creditCardDetails) {
        CreditCardDetailsModel creditCardDetailsModel = new CreditCardDetailsModel();
        creditCardDetailsModel.setCcLimit(creditCardDetails.getCcLimit());
        creditCardDetailsModel.setUserName(creditCardDetails.getUserName());
        creditCardDetailsModel.setCcNumber(creditCardDetails.getCcNumber());
        return creditCardRepository.save(creditCardDetailsModel);
    }
}
