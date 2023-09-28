package com.secondhand.api.user.domain;

import com.secondhand.api.common.domain.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.secondhand.api.user.domain.enumtype.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("유저 생성 시 status 값은 ACTIVE 이다.")
    void createStatusActiveTest() {
        // given
        Address address = Address.builder()
                .address("서울시 강남구 강남로 100")
                .addressDetail("테스트 빌딩 2005호")
                .build();

        // when
        User user = User.create("hong@sample.com", "1234pass3#", "홍길동", "01012341234", address);

        // then
        assertThat(user.getStatus()).isEqualTo(ACTIVE);

    }

}