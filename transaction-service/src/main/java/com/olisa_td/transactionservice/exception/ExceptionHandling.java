package com.olisa_td.transactionservice.exception;



import com.olisa_td.transactionservice.domain.HttpResponse;
import com.olisa_td.transactionservice.exception.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.*;



@RestControllerAdvice
public class ExceptionHandling {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    private static final String INCORRECT_ENUM_CREDENTIALS = "Invalid transaction type. Accepted values are:, WITHDRAW, DEPOSIT";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";




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




    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<HttpResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        return this.createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NotEnoughPermissionException.class)
    public ResponseEntity<HttpResponse> handleNotEnoughPermissionException() {
        return this.createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }


    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<HttpResponse> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        return this.createHttpResponse(NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return this.createHttpResponse(NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<HttpResponse> handleInsufficientFundsException(InsufficientFundsException ex) {
        return this.createHttpResponse(BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<HttpResponse> handleInvalidTransactionException(InvalidTransactionException ex) {
        return this.createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DailyLimitException.class)
    public ResponseEntity<HttpResponse> handleInvalidTransactionException(DailyLimitException ex) {
        return this.createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<HttpResponse> handleNullPointerException(NullPointerException ex) {
        return this.createHttpResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> handleInvalidEnum(HttpMessageNotReadableException ex) {
        return this.createHttpResponse(BAD_REQUEST, INCORRECT_ENUM_CREDENTIALS);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return this.createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
//        logger.error(exception.getMessage(),exception);
//        return this.createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
//    }

}

