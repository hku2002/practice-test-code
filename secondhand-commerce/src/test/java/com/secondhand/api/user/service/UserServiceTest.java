package com.secondhand.api.user.service;

import com.secondhand.api.common.domain.Address;
import com.secondhand.api.user.domain.User;
import com.secondhand.api.user.domain.UserRepository;
import com.secondhand.api.user.dto.LoginRequest;
import com.secondhand.api.user.dto.TokenResponse;
import com.secondhand.api.user.dto.UserCreateRequest;
import com.secondhand.api.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                .email("hong@sample.com")
                .password("test-pass-1234$")
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

    @Test
    @DisplayName("신규 유저 생성 시 가입된 email 유저가 있으면 예외를 발생시킨다.")
    void emailDuplicationTest() {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .email("hong@sample.com")
                .password("test-pass-1234$")
                .name("홍길동")
                .phoneNumber("01012345678")
                .address("서울특별시 강남구 강남로 100")
                .zipCode("00001")
                .build();
        Address address = Address.builder()
                .address("서울특별시 강남구 강남로 100")
                .addressDetail("좋은 빌딩 1001호")
                .zipCode("01000")
                .build();
        User user = User.create("hong@sample.com", "test-pass-1234!", "홍길동", "01012345678", address);
        userRepository.save(user);

        // when // then
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 사용자입니다.");
    }

    @Test
    @DisplayName("로그인이 올바르게 동작한다.")
    void loginTest() {
        // given
        LoginRequest request = LoginRequest.builder()
                .email("hong@sample.com")
                .password("test-password-1234!")
                .build();
        Address address = Address.builder()
                .address("서울특별시 강남구 강남로 100")
                .addressDetail("좋은 빌딩 1001호")
                .zipCode("01000")
                .build();
        User user = User.create("hong@sample.com", "test-pass-1234!", "홍길동", "01012345678", address);
        userRepository.save(user);

        // when
        TokenResponse response = userService.login(request);

        // then
        assertThat(response.getToken()).isNotEmpty();
    }

}
