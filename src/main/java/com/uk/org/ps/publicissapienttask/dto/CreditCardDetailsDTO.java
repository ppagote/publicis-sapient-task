package com.uk.org.ps.publicissapienttask.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@ApiModel(description = "Details about the credit cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardDetailsDTO {

    @NotBlank(message = "userName is mandatory")
    @NotNull(message = "userName cannot be null")
    private String userName;

    @NotBlank(message = "credit card number is mandatory")
    @NotNull(message = "credit card number  cannot be null")
    private String ccNumber;

    @NotNull(message = "credit card limit cannot be null")
    private int ccLimit;
}
