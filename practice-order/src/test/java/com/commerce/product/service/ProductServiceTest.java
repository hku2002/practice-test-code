package com.commerce.product.service;

import com.commerce.product.domain.Product;
import com.commerce.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

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
        List<Product> results = productService.findProducts(List.of(1L, 2L));

        // then
        assertThat(results).hasSize(2);
    }

    @Test
    @DirtiesContext
    @DisplayName("상품이 존재하지 않으면 예외가 발생한다.")
    void emptyProductExceptionTest() {
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

        // when / then
        assertThatThrownBy(() -> productService.findProducts(List.of(3L, 4L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품이 존재하지 않습니다.");
    }

}
