package com.sample.cafekiosk.unit;

import com.sample.cafekiosk.unit.beverage.Americano;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    @DisplayName("음료 한개 추가 테스트")
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertEquals(1, cafeKiosk.getBeverages().size());
    }

}