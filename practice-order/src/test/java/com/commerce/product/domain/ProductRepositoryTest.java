package com.commerce.product.domain;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("productId 리스트를 기준으로 상품 목록을 조회한다.")
    void findProductsByIds() {
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
        productRepository.saveAll(products);

        // when
        List<Product> results = productRepository.findByIdIn(List.of(1L, 2L));

        // then
        assertThat(results).hasSize(2)
                .extracting("productName", "price", "quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("상품명1", 1000, 10),
                        Tuple.tuple("상품명2", 2000, 5)
                );
    }

}
