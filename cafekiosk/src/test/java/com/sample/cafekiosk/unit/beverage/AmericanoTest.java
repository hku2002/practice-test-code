package com.sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AmericanoTest {

    @Test
    @DisplayName("이름이 아메리카노인지 확인")
    void getNameTest() {
        Americano americano = new Americano();
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    @DisplayName("가격이 올바른지 확인")
    void getPriceTest() {
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4_000);
    }

}