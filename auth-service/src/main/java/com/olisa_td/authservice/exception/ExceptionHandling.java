package com.olisa_td.authservice.exception;


import com.olisa_td.authservice.domain.HttpResponse;
import com.olisa_td.authservice.exception.domain.EmailExistException;
import com.olisa_td.authservice.exception.domain.InValidTokenException;
import com.olisa_td.authservice.exception.domain.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.*;



@RestControllerAdvice
public class ExceptionHandling {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final String INCORRECT_CREDENTIALS = "Email or Password is Incorrect.";
    private static final String TOKEN_EXPIRED_ERROR = "Token has Expired";




    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,message), httpStatus);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> handleValidationException(MethodArgumentNotValidException ex){
        StringBuilder errors = new StringBuilder();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            if(!errors.isEmpty()){
                errors.append(",");
            }
            errors.append(error.getDefaultMessage());
        }

        return this.createHttpResponse(BAD_REQUEST,errors.toString());
    }


    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> handleEmailExistException(EmailExistException ex) {
        return this.createHttpResponse(CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return this.createHttpResponse(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> handleBadCredentialsException() {
        return this.createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<HttpResponse> handleExpiredJwtException() {
        return this.createHttpResponse(UNAUTHORIZED, TOKEN_EXPIRED_ERROR);
    }

    @ExceptionHandler(InValidTokenException.class)
    public ResponseEntity<HttpResponse> handleInValidTokenException(InValidTokenException ex) {
        return this.createHttpResponse(UNAUTHORIZED, ex.getMessage());
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<HttpResponse> handleNullPointerException(NullPointerException ex) {
        return this.createHttpResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}

