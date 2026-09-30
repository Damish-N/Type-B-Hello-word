package org.dna.helloworld.exception;


import org.dna.helloworld.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * HTTP responses so controllers stay free of error-handling code.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    static final String INVALID_INPUT = "Invalid Input";

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidInput(InvalidInputException ex) {
        log.debug("Rejected request: {}", ex.getMessage());
        return new ErrorResponse(INVALID_INPUT);
    }
}
