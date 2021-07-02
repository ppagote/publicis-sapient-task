package com.uk.org.ps.publicissapienttask.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.uk.org.ps.publicissapienttask.dto.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    public @Override
    void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        logger.error(e.getLocalizedMessage(), e);

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                "Invalid Authorization token", FORBIDDEN.name());
        String message = new ObjectMapper().writer().writeValueAsString(errorDetails);
        /*String message = RestResponse.builder()
                .status(FORBIDDEN)
                .message("Invalid Authorization token")
                .path(request.getRequestURI())
                .json();*/

        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }
}
