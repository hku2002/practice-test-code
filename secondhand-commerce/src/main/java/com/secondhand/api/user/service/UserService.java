package com.secondhand.api.user.service;

import com.secondhand.api.common.domain.Address;
import com.secondhand.api.common.jwt.JwtFilter;
import com.secondhand.api.common.jwt.TokenProvider;
import com.secondhand.api.user.domain.User;
import com.secondhand.api.user.domain.UserRepository;
import com.secondhand.api.user.dto.LoginRequest;
import com.secondhand.api.user.dto.TokenResponse;
import com.secondhand.api.user.dto.UserCreateRequest;
import com.secondhand.api.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        Address address = request.toAddress(
                request.getAddress(),
                request.getAddressDetail(),
                request.getZipCode()
        );

        Optional<User> checkUser = userRepository.findByEmail(request.getEmail());
        if (checkUser.isPresent()) throw new IllegalStateException("이미 존재하는 사용자입니다.");

        User user = User.create(request.getEmail(), request.getPassword(), request.getName(), request.getPhoneNumber(), address);
        return UserResponse.of(userRepository.save(user));
    }

    public TokenResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        return new TokenResponse(token, httpHeaders);
    }

}
