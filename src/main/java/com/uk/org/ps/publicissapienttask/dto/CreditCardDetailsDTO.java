package com.uk.org.ps.publicissapienttask.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;

@ApiModel(description = "Details about the credit cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardDetailsDTO {

    @NotEmpty(message = "User Name is mandatory.")
    private String userName;

    //TODO:: length restrictions
    @NotNull(message = "Credit Card number is mandatory.")
    @Digits(integer = 19, fraction = 0, message = "Credit card number cannot be greater than 19 digits.")
    @Positive(message = "Credit Card number should be positive.")
    private long ccNumber;

    @NotNull(message = "Credit Card limit is mandatory.")
    @PositiveOrZero(message = "Credit Card limit should be positive or zero.")
    private int ccLimit;
}
