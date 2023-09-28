package com.secondhand.api.user.service;

import com.secondhand.api.common.domain.Address;
import com.secondhand.api.user.domain.User;
import com.secondhand.api.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static com.secondhand.api.user.domain.enumtype.UserStatus.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("유저 정보를 올바르게 가져온다.")
    void loadUserByUsernameTest() {
        // given
        Address address = Address.builder()
                .address("서울특별시 강남구 강남로 100")
                .addressDetail("좋은 빌딩 1001호")
                .zipCode("01000")
                .build();
        User user = User.create("hong@sample.com", "test-pass-1234!", "홍길동", "01012345678", address);
        userRepository.save(user);

        // when
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("hong@sample.com");

        // then
        assertThat(userDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    @DisplayName("유저 정보가 었으면 UsernameNotFoundException 을 발생시킨다.")
    void userNotFondTest() {
        // given
        Address address = Address.builder()
                .address("서울특별시 강남구 강남로 100")
                .addressDetail("좋은 빌딩 1001호")
                .zipCode("01000")
                .build();
        User user = User.create("hong@sample.com", "test-pass-1234!", "홍길동", "01012345678", address);
        userRepository.save(user);

        // when // then
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername("notUser@sample.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("사용자 정보가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("탈퇴한 회원은 IllegalStateException 예외를 발생시킨다.")
    void withdrawalTest() {
        // given
        Address address = Address.builder()
                .address("서울특별시 강남구 강남로 100")
                .addressDetail("좋은 빌딩 1001호")
                .zipCode("01000")
                .build();
        User user = User.builder()
                .name("홍길동")
                .email("hong@sample.com")
                .password("test-password2@")
                .address(address)
                .phoneNumber("01012345678")
                .status(WITHDRAWAL)
                .build();
        userRepository.save(user);

        // when // then
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername("hong@sample.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("탈퇴한 유저입니다.");
    }

}