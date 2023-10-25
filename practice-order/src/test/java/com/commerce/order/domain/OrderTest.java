package com.commerce.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.commerce.order.domain.enumtype.OrderStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("주문완료 처리가 올바르게 작동한다.")
    void completeTest() {
        // given
        Order order = Order.builder()
                .orderName("상품명1")
                .status(CREATED)
                .totalPrice(1000)
                .build();

        // when
        order.completeStatus();

        // then
        assertThat(order.getStatus()).isEqualTo(COMPLETED);
    }

    @Test
    @DisplayName("주문생성이 아닌 경우 주문완료 처리시 예외가 발생한다.")
    void completeExceptionTest() {
        // given
        Order order = Order.builder()
                .orderName("상품명1")
                .status(CANCELED)
                .totalPrice(1000)
                .build();

        // when / then
        assertThatThrownBy(order::completeStatus)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 완료처리가 불가능한 상태입니다.");
    }

}
