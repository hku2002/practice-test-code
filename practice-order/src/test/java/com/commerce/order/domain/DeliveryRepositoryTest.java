package com.commerce.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.commerce.order.domain.enumtype.DeliveryStatus.WAIT;
import static com.commerce.order.domain.enumtype.OrderStatus.CREATED;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("주문번호로 배송 조회가 올바르게 작동한다.")
    void findByOrder() {
        // given
        Order order = Order.builder()
                .orderName("상품명1 외 1 건")
                .totalPrice(3000)
                .status(CREATED)
                .build();
        Delivery delivery = Delivery.builder()
                .address("서울특별시 강남구 강남대로 123 1501호")
                .zipCode("00001")
                .order(order)
                .status(WAIT)
                .build();
        orderRepository.save(order);
        deliveryRepository.save(delivery);

        // when
        Delivery result = deliveryRepository.findByOrder(delivery.getOrder());

        // then
        assertThat(result).isNotNull();

    }

}
