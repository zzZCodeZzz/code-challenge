package com.homeht.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
public class ErrorResponseMessage {

    private String timeStamp;
    private HttpStatus httpStatus;
    private String errorName;
    private String errorMessage;
    private String path;

    public ErrorResponseMessage(HttpStatus httpStatus, String errorName, String errorMessage, String path) {
        this.timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());
        this.httpStatus = httpStatus;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
        this.path = path;
    }
}
