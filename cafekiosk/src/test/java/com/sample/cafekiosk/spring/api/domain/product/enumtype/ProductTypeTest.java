package com.sample.cafekiosk.spring.api.domain.product.enumtype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고로 관리되는 타입이면 true 를 return 한다.")
    void stockTypeTrueTest() {
        // given
        ProductType productType1 = ProductType.BAKERY;
        ProductType productType2 = ProductType.BOTTLE;

        // when
        boolean bakeryType = ProductType.containsStockManagedType(productType1);
        boolean bottleType = ProductType.containsStockManagedType(productType2);

        //then
        assertThat(bakeryType).isTrue();
        assertThat(bottleType).isTrue();

    }

    @Test
    @DisplayName("상품 타입이 재고로 관리되는 타입이아니면 false 를 return 한다.")
    void stockTypeFalseTest() {
        // given
        ProductType productType = ProductType.HANDMADE;

        // when
        boolean handMadeType = ProductType.containsStockManagedType(productType);

        //then
        assertThat(handMadeType).isFalse();
    }

}