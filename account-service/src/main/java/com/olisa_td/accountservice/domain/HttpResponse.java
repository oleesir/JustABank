package com.olisa_td.accountservice.domain;

import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HttpResponse {

    Date timeStamp;
    int httpStatusCode;
    HttpStatus httpStatus;
    String message;

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String message) {
        this.timeStamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.timeStamp);
        return "HttpResponse [timeStamp=" + timeStamp + ", httpStatusCode=" + httpStatusCode + ", httpStatus="
                + httpStatus + " message=" + message + "]";
    }
}
