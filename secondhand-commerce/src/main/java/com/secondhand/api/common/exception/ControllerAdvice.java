package com.secondhand.api.common.exception;

import com.secondhand.api.common.response.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public DataResponse<Object> bindException(BindException e) {
        return DataResponse.fail(HttpStatus.BAD_REQUEST
                , e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
                , ""
        );
    }
}
