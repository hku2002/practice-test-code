package com.secondhand.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private HttpHeaders httpHeaders;
}
