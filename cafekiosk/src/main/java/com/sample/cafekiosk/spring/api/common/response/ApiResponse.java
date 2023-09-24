package com.sample.cafekiosk.spring.api.common.response;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private int code;
    private HttpStatus status;
    private String message;
    private T data;
}
