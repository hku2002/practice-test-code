package com.secondhand.api.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondhand.api.user.dto.UserCreateRequest;
import com.secondhand.api.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("유저 생성 요청 시 올바르게 응답한다.")
    void userCreateCorrectReturnTest() throws Exception {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .name("홍길동")
                .phoneNumber("01012345678")
                .address("서울특별시 강남구 강남로 100")
                .zipCode("00001")
                .build();

        // when // then
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"));
    }

    @Test
    @DisplayName("이름이 비어있으면 400 응답을 준다.")
    void nameEmpty400Return() throws Exception {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .phoneNumber("01012345678")
                .address("서울특별시 강남구 강남로 100")
                .addressDetail("부자 빌딩 1601호")
                .zipCode("00001")
                .build();

        // when // then
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("이름은 필수 입력값입니다."));

    }

}
