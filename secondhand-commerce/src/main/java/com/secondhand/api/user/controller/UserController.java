package com.secondhand.api.user.controller;

import com.secondhand.api.common.response.CommonResponse;
import com.secondhand.api.common.response.DataResponse;
import com.secondhand.api.user.dto.UserCreateRequest;
import com.secondhand.api.user.dto.UserResponse;
import com.secondhand.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/users")
    public ResponseEntity<DataResponse<UserResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {
        return CommonResponse.created(userService.createUser(request));
    }

}
