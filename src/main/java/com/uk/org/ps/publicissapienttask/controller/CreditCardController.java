package com.uk.org.ps.publicissapienttask.controller;

import com.uk.org.ps.publicissapienttask.dto.CreditCardDetailsDTO;
import com.uk.org.ps.publicissapienttask.dto.ErrorDetails;
import com.uk.org.ps.publicissapienttask.model.CreditCardDetailsModel;
import com.uk.org.ps.publicissapienttask.service.CreditCardService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cc/v1")
@Api(tags = {"CreditCard"})
@AllArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add credit card details",
            notes = "Add a new credit card details", response = CreditCardDetailsModel.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CreditCardDetailsModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not Acceptable", response = ErrorDetails.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetails.class)
    })
    public CreditCardDetailsModel addDetails(
            @ApiParam(required = true, name = "creditCard", value = "Add credit Card")
            @RequestBody @Valid CreditCardDetailsDTO creditCardDetailsDto) {

        return creditCardService.addDetails(creditCardDetailsDto);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch all credit card",
            notes = "Retrieving the collection of credit card", response = CreditCardDetailsModel[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CreditCardDetailsModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetails.class)
    })
    public List<CreditCardDetailsModel> fetchAllDetails() {

        return creditCardService.getAllDetails();
    }
}
