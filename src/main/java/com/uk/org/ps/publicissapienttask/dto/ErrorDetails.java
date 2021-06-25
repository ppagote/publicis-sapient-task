package com.uk.org.ps.publicissapienttask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class ErrorDetails {

    private final LocalDateTime timestamp;
    private final String message;
    private final String status;
}