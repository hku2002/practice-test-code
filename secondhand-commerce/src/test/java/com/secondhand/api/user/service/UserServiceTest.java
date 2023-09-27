package com.secondhand.api.user.service;

import com.secondhand.api.user.domain.UserRepository;
import com.secondhand.api.user.dto.UserCreateRequest;
import com.secondhand.api.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("신규 유저 생성 시 User 데이터를 올바르게 return 한다.")
    void createUserCorrectReturn() {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .name("홍길동")
                .phoneNumber("01012345678")
                .address("서울특별시 강남구 강남로 100")
                .zipCode("00001")
                .build();

        // when
        UserResponse userResponse = userService.createUser(request);

        // then
        assertAll(
                () -> assertThat(userResponse.getName()).isEqualTo("홍길동"),
                () -> assertThat(userResponse.getPhoneNumber()).isEqualTo("01012345678"),
                () -> assertThat(userResponse.getAddress()).isEqualTo("서울특별시 강남구 강남로 100")
        );
    }

}
