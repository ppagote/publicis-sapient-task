package com.uk.org.ps.publicissapienttask.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;
import java.math.BigInteger;

@ApiModel(description = "Details about the credit cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardDetailsDTO {

    @NotEmpty(message = "User Name is mandatory.")
    private String userName;

    @NotNull(message = "Credit Card number is mandatory.")
    @Pattern(regexp="^[0-9]{1,19}",message="Credit Card number must contain only digits, must be positive number and max length can be 19")
    private String ccNumber;

    @NotNull(message = "Credit Card limit is mandatory.")
    @PositiveOrZero(message = "Credit Card limit should be positive or zero.")
    private int ccLimit;
}
