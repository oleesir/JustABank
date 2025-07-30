//package com.olisa_td.transactionservice.exception;
//
//import com.olisa_td.transactionservice.exception.domain.InvalidTransactionException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.time.ZonedDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(InvalidTransactionException.class)
//    public ResponseEntity<Object> handleInvalidTransactionException(InvalidTransactionException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("httpStatusCode", HttpStatus.BAD_REQUEST.value());
//        body.put("httpStatus", HttpStatus.BAD_REQUEST.getReasonPhrase());
//        body.put("reason", "INVALID TRANSACTION");
//        body.put("message", ex.getMessage());
//        body.put("timeStamp", ZonedDateTime.now());
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
//}
