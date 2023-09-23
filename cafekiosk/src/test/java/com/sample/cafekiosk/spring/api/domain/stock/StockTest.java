package com.sample.cafekiosk.spring.api.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @Test
    @DisplayName("입력받은 재고수량이 현재 수량보다 많으면 true 를 return 한다.")
    void isQuantityLessThanTrue() {
        // given
        Stock stock = Stock.create("001", 3);

        // when
        boolean result = stock.isQuantityLessThan(4);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("입력받은 재고수량이 현재 수량보다 적으면 false 를 return 한다.")
    void isQuantityLessThanFalse() {
        // given
        Stock stock = Stock.create("001", 3);

        // when
        boolean result = stock.isQuantityLessThan(3);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("입력받은 재고수 만큼 재고를 차감한다.")
    void deductQuantity() {
        // given
        Stock stock = Stock.create("001", 1);

        // when
        stock.deductQuantity(1);

        //then
        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    @DisplayName("현재 재고 수량보다 많은 수량이 차감되면 예외를 발생시킨다.")
    void deductQuantityOutOfStock() {
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when / then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");
    }

}