package com.commerce.order.service;

import com.commerce.order.domain.OrderProduct;
import com.commerce.order.domain.OrderProductRepository;
import com.commerce.product.domain.Product;
import com.commerce.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

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
        assertThat(orderId).isEqualTo(1L);
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

}
