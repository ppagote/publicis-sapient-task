package com.uk.org.ps.publicissapienttask.repository;

import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardDetailsModel, Long> {
}
