package com.sample.cafekiosk.spring.api.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class CommonResponse {

    private CommonResponse() {
    }

    public static <T> ResponseEntity<BodyResponse<T>> ok(T data) {
        return ResponseEntity.ok()
                .body(new BodyResponse<>(HttpStatus.OK, "", data));
    }

    public static <T> ResponseEntity<BodyResponse<T>> created(T data) {
        return ResponseEntity.created(URI.create(""))
                .body(new BodyResponse<>(HttpStatus.CREATED, "", data));
    }

    public static <T> ResponseEntity<BodyResponse<T>> fail(HttpStatus httpStatus, String message, T data) {
        return ResponseEntity.status(httpStatus)
                .body(new BodyResponse<>(httpStatus, message, data));
    }
}
