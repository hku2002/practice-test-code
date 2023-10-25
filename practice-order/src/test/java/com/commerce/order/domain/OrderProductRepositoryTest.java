package com.commerce.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.commerce.order.domain.enumtype.OrderStatus.CREATED;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderProductRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;

    @Test
    @DisplayName("orderProductId 리스트가 전달되면 해당값을 return 한다.")
    void orderProductIdsTest() {
        // given
        Order order = Order.builder()
                .orderName("상품명1 외 1 건")
                .status(CREATED)
                .totalPrice(3000)
                .build();
        orderRepository.save(order);

        OrderProduct orderProduct1 = OrderProduct.builder()
                .productId(1L)
                .productName("상품명1")
                .price(1000)
                .order(order)
                .build();
        OrderProduct orderProduct2 = OrderProduct.builder()
                .productId(2L)
                .productName("상품명2")
                .price(2000)
                .order(order)
                .build();
        List<OrderProduct> orderProducts = List.of(orderProduct1, orderProduct2);
        orderProductRepository.saveAll(orderProducts);

        // when
        List<OrderProduct> results = orderProductRepository.findByIdIn(List.of(1L, 2L));

        // then
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("Order 정보로 OrderProduct 리스트가 올바르게 조회된다.")
    void findOrderProductByOrder() {
        // given
        Order order = Order.builder()
                .orderName("상품명1 외 1 건")
                .status(CREATED)
                .totalPrice(3000)
                .build();
        orderRepository.save(order);

        OrderProduct orderProduct1 = OrderProduct.builder()
                .productId(1L)
                .productName("상품명1")
                .price(1000)
                .order(order)
                .build();
        OrderProduct orderProduct2 = OrderProduct.builder()
                .productId(2L)
                .productName("상품명2")
                .price(2000)
                .order(order)
                .build();
        List<OrderProduct> orderProducts = List.of(orderProduct1, orderProduct2);
        orderProductRepository.saveAll(orderProducts);

        // when
        List<OrderProduct> results = orderProductRepository.findByOrder(order);

        // then
        assertThat(results).hasSize(2);
    }

}
