package com.sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AmericanoTest {

    @Test
    @DisplayName("Americano 의 이름을 조회하면 '아메리카노' 가 나와야 한다.")
    void getNameTest() {
        Americano americano = new Americano();
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    @DisplayName("Americano 의 가격은 4,000원 이어야 한다.")
    void getPriceTest() {
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4_000);
    }

}