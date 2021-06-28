package com.uk.org.ps.publicissapienttask.controller;

import com.uk.org.ps.publicissapienttask.dto.ErrorDetails;
import com.uk.org.ps.publicissapienttask.dto.UserDTO;
import com.uk.org.ps.publicissapienttask.model.UserModel;
import com.uk.org.ps.publicissapienttask.service.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/v1")
@Api(tags = {"User"})
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add User details",
            notes = "Add a new user details", response = UserDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = UserModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetails.class),
    })
    public UserModel addDetails(
            @ApiParam(required = true, name = "user", value = "Add user")
            @RequestBody @Valid UserDTO user) {
        return userService.addDetails(user);
    }

}
