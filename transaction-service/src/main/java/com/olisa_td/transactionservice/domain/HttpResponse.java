package com.olisa_td.transactionservice.domain;

import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpResponse {

    Date timeStamp;
    int httpStatusCode;
    HttpStatus httpStatus;
    String reason;
    String message;

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.timeStamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.timeStamp);
        return "HttpResponse [timeStamp=" + timeStamp + ", httpStatusCode=" + httpStatusCode + ", httpStatus="
                + httpStatus + ", reason=" + reason + ", message=" + message + "]";
    }
}
