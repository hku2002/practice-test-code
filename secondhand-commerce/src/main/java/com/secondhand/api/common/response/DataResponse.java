package com.secondhand.api.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResponse<T> {

    private final int code;
    private final String message;
    private final T data;

    private DataResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> DataResponse<T> ok(T data) {
        return new DataResponse<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T> DataResponse<T> created(T data) {
        return new DataResponse<>(HttpStatus.CREATED.value(), "created", data);
    }

    public static <T> DataResponse<T> fail(HttpStatus status, String message, T data) {
        return new DataResponse<>(status.value(), message, data);
    }
}
