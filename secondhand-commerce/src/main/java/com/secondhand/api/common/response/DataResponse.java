package com.secondhand.api.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResponse<T> {

    private final HttpStatus code;
    private final String message;
    private final T data;

    private DataResponse(HttpStatus code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> DataResponse<T> ok(T data) {
        return new DataResponse<>(HttpStatus.OK, "success", data);
    }

    public static <T> DataResponse<T> created(T data) {
        return new DataResponse<>(HttpStatus.CREATED, "created", data);
    }

    public static <T> DataResponse<T> fail(HttpStatus status, T data) {
        return new DataResponse<>(status, "fail", data);
    }
}
