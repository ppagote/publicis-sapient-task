package com.uk.org.ps.publicissapienttask.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.org.ps.publicissapienttask.dto.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    public @Override
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        logger.error(e.getLocalizedMessage(), e);

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                "Incorrect authentication details", FORBIDDEN.name());
        String message = new ObjectMapper().writer().writeValueAsString(errorDetails);
       /* String message = RestResponse.builder()
                .status(UNAUTHORIZED)
                .error("Unauthenticated")
                .message("Insufficient authentication details")
                .path(request.getRequestURI())
                .json();*/

        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }
}
