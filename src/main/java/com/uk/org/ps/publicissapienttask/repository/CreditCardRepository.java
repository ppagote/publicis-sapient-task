package com.uk.org.ps.publicissapienttask.repository;

import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardDetailsModel, Long> {

    @Query/*("SELECT cc FROM cc_details cc WHERE cc.cc_number = :number limit 1")*/
    CreditCardDetailsModel findByccNumber( /*@Param("number") */String ccNumber);
}
