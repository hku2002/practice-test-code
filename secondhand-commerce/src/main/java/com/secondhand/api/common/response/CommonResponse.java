package com.secondhand.api.common.response;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@Getter
public class CommonResponse {

    private CommonResponse() {
    }

    public static <T> ResponseEntity<DataResponse<T>> ok(T data) {
        return ResponseEntity.ok().body(DataResponse.ok(data));
    }

    public static <T> ResponseEntity<DataResponse<T>> created(T data) {
        return ResponseEntity.created(URI.create("")).body(DataResponse.created(data));
    }

    public static <T> ResponseEntity<DataResponse<T>> fail(HttpStatus httpStatus, T data) {
        return ResponseEntity.status(httpStatus).body(DataResponse.fail(httpStatus, data));
    }

}
