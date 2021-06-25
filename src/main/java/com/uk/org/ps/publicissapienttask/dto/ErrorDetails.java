package com.uk.org.ps.publicissapienttask.dto;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ErrorDetails {

    private final Date timestamp;
    private final String message;
    private final String status;
}