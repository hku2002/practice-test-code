package com.commerce.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ProductTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("전달받은 수량만큼 재고가 차감된다.")
    void deductionQuantityTest() {
        // given
        Product product = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(10)
                .build();

        // when
        product.deductionQuantity(1);

        // then
        assertThat(product.getQuantity()).isEqualTo(9);
    }

    @Test
    @DisplayName("차감한 수량이 0보다 작으면 예외가 발생한다.")
    void quantityZeroExceptionTest() {
        // given
        Product product = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(1)
                .build();

        // when / then
        assertThatThrownBy(() ->product.deductionQuantity(2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("차감할 수량이 0보다 작으면 0만큼 차감되어야 한다.")
    void deductionQuantityMinusTest() {
        // given
        Product product = Product.builder()
                .productName("상품명1")
                .price(1000)
                .quantity(10)
                .build();

        // when
        product.deductionQuantity(-1);

        // then
        assertThat(product.getQuantity()).isEqualTo(10);

    }

}
