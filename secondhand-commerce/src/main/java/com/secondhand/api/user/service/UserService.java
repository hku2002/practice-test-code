package com.secondhand.api.user.service;

import com.secondhand.api.common.domain.Address;
import com.secondhand.api.user.domain.User;
import com.secondhand.api.user.domain.UserRepository;
import com.secondhand.api.user.dto.UserCreateRequest;
import com.secondhand.api.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        Address address = request.toAddress(
                request.getAddress(),
                request.getAddressDetail(),
                request.getZipCode()
        );

        User user = User.create(request.getEmail(), request.getPassword(), request.getName(), request.getPhoneNumber(), address);
        return UserResponse.of(userRepository.save(user));
    }

}
