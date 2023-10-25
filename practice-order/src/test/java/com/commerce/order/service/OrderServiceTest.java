package com.commerce.order.service;

import com.commerce.order.domain.*;
import com.commerce.product.domain.Product;
import com.commerce.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.commerce.order.domain.enumtype.OrderStatus.COMPLETED;
import static com.commerce.order.domain.enumtype.OrderStatus.CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Test
    @DisplayName("주문이 생성된다.")
    void createTest() {
        // given
        Product product1 = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(10)
                .build();
        Product product2 = Product.builder()
                .productName("상품명2")
                .price(2000)
                .quantity(5)
                .build();
        List<Product> products = List.of(product1, product2);
        List<Product> results = productRepository.saveAll(products);
        List<Long> productIds = List.of(results.get(0).getId(), results.get(1).getId());

        // when
        Long orderId = orderService.create(productIds);

        // then
        assertThat(orderId).isNotNull();
    }

    @Test
    @DisplayName("productId 2개를 매계변수로 주면 orderProduct 는 2개가 생성되어야 한다.")
    void orderProductSize2Test() {
        // given
        Product product1 = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(10)
                .build();
        Product product2 = Product.builder()
                .productName("상품명2")
                .price(2000)
                .quantity(5)
                .build();
        List<Product> products = List.of(product1, product2);
        List<Product> results = productRepository.saveAll(products);
        List<Long> productIds = List.of(results.get(0).getId(), results.get(1).getId());

        // when
        orderService.create(productIds);

        // then
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        assertThat(orderProducts).hasSize(2);

    }

    @Test
    @DisplayName("주문 완료가 올바르게 작동한다.")
    void completeTest() {
        // given
        Order order = Order.builder()
                .orderName("상품명1 외 1 건")
                .totalPrice(3000)
                .status(CREATED)
                .build();
        OrderProduct orderProduct1 = OrderProduct.builder()
                .productId(1L)
                .productName("상품명1")
                .order(order)
                .price(1000)
                .build();
        OrderProduct orderProduct2 = OrderProduct.builder()
                .productId(2L)
                .productName("상품명2")
                .order(order)
                .price(2000)
                .build();
        orderRepository.save(order);
        orderProductRepository.saveAll(List.of(orderProduct1, orderProduct2));

        // when
        Long orderId = orderService.complete(order.getId());

        // then
        Order checkOrder = orderProductRepository.findById(orderId).orElseThrow().getOrder();
        assertAll(
                () -> assertThat(checkOrder.getStatus()).isEqualTo(COMPLETED),
                () -> assertThat(orderId).isEqualTo(order.getId())
        );

    }

    @Test
    @DisplayName("주문 완료 시 배송정보가 생성되어야 한다.")
    void deliveryCreatedTest() {
        // given
        Order order = Order.builder()
                .orderName("상품명1 외 1 건")
                .totalPrice(3000)
                .status(CREATED)
                .build();
        OrderProduct orderProduct1 = OrderProduct.builder()
                .productId(1L)
                .productName("상품명1")
                .order(order)
                .price(1000)
                .build();
        OrderProduct orderProduct2 = OrderProduct.builder()
                .productId(2L)
                .productName("상품명2")
                .order(order)
                .price(2000)
                .build();
        orderRepository.save(order);
        orderProductRepository.saveAll(List.of(orderProduct1, orderProduct2));

        // when
        orderService.complete(order.getId());

        // then
        Delivery delivery = deliveryRepository.findByOrder(order);
        assertThat(delivery).isNotNull();
    }

    @Test
    @DisplayName("주문 정보가 존재하지 않으면 예외를 발생시킨다.")
    void orderEmptyExceptionTest() {

        // when / then
        assertThatThrownBy(() -> orderService.complete(10000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 주문번호 입니다.")
        ;
    }

}
