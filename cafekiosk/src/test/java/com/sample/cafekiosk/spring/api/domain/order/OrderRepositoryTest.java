package com.sample.cafekiosk.spring.api.domain.order;

import com.sample.cafekiosk.spring.api.domain.product.Product;
import com.sample.cafekiosk.spring.api.domain.product.ProductRepository;
import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus;
import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductType;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.sample.cafekiosk.spring.api.domain.order.enumtype.OrderStatus.INIT;
import static com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus.SELLING;
import static com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("시작일자, 종료일자, 주문상태값을 기준으로 주문 정보를 조회한다.")
    void findOrdersByTest() {
        // given
        LocalDateTime currentDateTime = LocalDateTime.now();
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, SELLING, "카페모카", 4500);
        productRepository.saveAll(List.of(product1, product2));

        Order order1 = Order.create(List.of(product1, product2), currentDateTime.minusHours(2L));
        Order order2 = Order.create(List.of(product1, product2), currentDateTime.minusHours(1L));
        Order order3 = Order.create(List.of(product1, product2), currentDateTime);
        orderRepository.saveAll(List.of(order1, order2, order3));

        // when
        List<Order> orders = orderRepository.findOrdersBy(currentDateTime.minusHours(2L), currentDateTime, INIT);

        // then
        assertThat(orders).hasSize(2)
                .extracting("orderStatus", "totalPrice")
                .contains(Tuple.tuple(INIT, 8500));
    }

    private Product createProduct(
            String productNumber
            , ProductType type
            , ProductSellingStatus sellingStatus
            , String name
            , int price
    ) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

}