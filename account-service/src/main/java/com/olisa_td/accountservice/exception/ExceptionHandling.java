package com.olisa_td.accountservice.exception;


import com.olisa_td.accountservice.domain.HttpResponse;
import com.olisa_td.accountservice.exception.domain.AccountNotFoundException;
import com.olisa_td.accountservice.exception.domain.InvalidAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;



@RestControllerAdvice
public class ExceptionHandling {


    private static final String INCORRECT_CREDENTIALS = "Invalid account type";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";




    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, message), httpStatus);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return this.createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return this.createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<HttpResponse> accountNotFoundException(AccountNotFoundException ex) {
        return this.createHttpResponse(NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return this.createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<HttpResponse> handleInvalidTransactionException(InvalidAccountException ex) {
        return this.createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

}
