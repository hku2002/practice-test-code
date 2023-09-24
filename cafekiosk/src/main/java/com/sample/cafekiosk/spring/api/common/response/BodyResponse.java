package com.sample.cafekiosk.spring.api.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BodyResponse<T> {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final T data;

    public BodyResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> BodyResponse<T> of(HttpStatus httpStatus, T data) {
        return new BodyResponse<>(httpStatus, httpStatus.name(), data);
    }

    public static <T> BodyResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new BodyResponse<>(httpStatus, message, data);
    }

    public static <T> BodyResponse<T> ok(T data) {
        return new BodyResponse<>(HttpStatus.OK, "success", data);
    }

    public static <T> BodyResponse<T> created(T data) {
        return new BodyResponse<>(HttpStatus.CREATED, HttpStatus.CREATED.name(), data);
    }
}
